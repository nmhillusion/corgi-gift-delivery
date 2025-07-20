package tech.nmhillusion.corgi_gift_delivery.domains.delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;
import tech.nmhillusion.corgi_gift_delivery.service.core.SequenceService;
import tech.nmhillusion.corgi_gift_delivery.service_impl.business.BaseBusinessServiceImpl;
import tech.nmhillusion.n2mix.exception.ApiResponseException;
import tech.nmhillusion.n2mix.exception.NotFoundException;

import java.text.MessageFormat;
import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */

@TransactionalService
public class DeliveryServiceImpl extends BaseBusinessServiceImpl<DeliveryEntity, DeliveryRepository> implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryExcelSheetParser deliveryExcelSheetParser;


    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, SequenceService sequenceService, DeliveryExcelSheetParser deliveryExcelSheetParser) {
        super(deliveryRepository, sequenceService);
        this.deliveryRepository = deliveryRepository;
        this.deliveryExcelSheetParser = deliveryExcelSheetParser;
    }

    @Override
    public Long getDeliveryIdByEventAndCustomer(String eventId, String customerId) {
        return deliveryRepository.findByEventIdAndCustomerId(eventId, customerId)
                .getDeliveryId();
    }

    @Override
    public List<DeliveryEntity> insertBatchByExcelFile(MultipartFile excelFile) throws ApiResponseException {
        try {
            final List<DeliveryEntity> combinedList = parseExcelFileToEntityList(excelFile, deliveryExcelSheetParser::parse);

            return saveBatch(combinedList);
        } catch (Throwable ex) {
            throw new ApiResponseException(ex);
        }
    }

    @Override
    public List<DeliveryEntity> updateBatchByExcelFile(MultipartFile excelFile) {
        try {
            final List<DeliveryEntity> deliveryEntities = parseExcelFileToEntityList(excelFile, deliveryExcelSheetParser::parse);

            for (DeliveryEntity deliveryEntity : deliveryEntities) {
                final Long existedDeliveryId = getDeliveryIdByEventAndCustomer(deliveryEntity.getEventId(), deliveryEntity.getCustomerId());
                if (existedDeliveryId == null) {
                    throw new NotFoundException(
                            MessageFormat.format(
                                    "Delivery not found to update, event id: {0}, customer id: {1}"
                                    , deliveryEntity.getEventId()
                                    , deliveryEntity.getCustomerId()
                            )
                    );
                }

                deliveryEntity.setDeliveryId(existedDeliveryId);
            }

            return deliveryRepository.saveAllAndFlush(deliveryEntities);
        } catch (Throwable ex) {
            throw new ApiResponseException(ex);
        }
    }

    @Override
    public Page<DeliveryEntity> search(DeliveryDto deliveryDto, int pageIndex, int pageSize) {
        return deliveryRepository.search(deliveryDto, PageRequest.of(pageIndex, pageSize));
    }
}
