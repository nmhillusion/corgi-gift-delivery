package tech.nmhillusion.corgi_gift_delivery.service_impl.business;

import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;
import tech.nmhillusion.corgi_gift_delivery.parser.delivery.DeliveryExcelSheetParser;
import tech.nmhillusion.corgi_gift_delivery.repository.business.DeliveryRepository;
import tech.nmhillusion.corgi_gift_delivery.service.business.DeliveryService;
import tech.nmhillusion.corgi_gift_delivery.service.core.SequenceService;
import tech.nmhillusion.n2mix.exception.ApiResponseException;
import tech.nmhillusion.n2mix.exception.NotFoundException;
import tech.nmhillusion.n2mix.helper.office.excel.reader.ExcelReader;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
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
            final List<DeliveryEntity> combinedList = convertExcelFileToDeliveries(excelFile);

            return saveBatch(combinedList);
        } catch (Exception ex) {
            throw new ApiResponseException(ex);
        }
    }

    private List<DeliveryEntity> convertExcelFileToDeliveries(MultipartFile excelFile) throws IOException, NotFoundException {
        final List<SheetData> sheetList = ExcelReader.read(excelFile.getInputStream());
        final List<DeliveryEntity> combinedList = new ArrayList<>();

        for (SheetData sheetData : sheetList) {
            final List<DeliveryEntity> deliveryEntities = deliveryExcelSheetParser.parse(sheetData);
            combinedList.addAll(deliveryEntities);
        }
        return combinedList;
    }

    @Override
    public List<DeliveryEntity> updateBatchByExcelFile(MultipartFile excelFile) {
        try {
            final List<DeliveryEntity> deliveryEntities = convertExcelFileToDeliveries(excelFile);

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
        } catch (Exception ex) {
            throw new ApiResponseException(ex);
        }
    }
}
