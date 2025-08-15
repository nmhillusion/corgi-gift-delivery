package tech.nmhillusion.corgi_gift_delivery.domains.delivery;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;
import tech.nmhillusion.corgi_gift_delivery.service.core.SequenceService;
import tech.nmhillusion.corgi_gift_delivery.service_impl.business.BaseBusinessServiceImpl;
import tech.nmhillusion.n2mix.exception.ApiResponseException;
import tech.nmhillusion.n2mix.exception.NotFoundException;
import tech.nmhillusion.n2mix.helper.office.excel.writer.ExcelWriteHelper;
import tech.nmhillusion.n2mix.helper.office.excel.writer.model.ExcelDataConverterModel;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

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
        final Optional<DeliveryEntity> entity_ = deliveryRepository.findByEventIdAndCustomerId(eventId, customerId);
        getLogger(this).info("getDeliveryIdByEventAndCustomer(String eventId = {}, String customerId = {}) = {}", eventId, customerId, entity_);

        return entity_.map(DeliveryEntity::getDeliveryId).orElseThrow();
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
    public Page<DeliveryEntity> search(DeliverySearchDto deliveryDto, int pageIndex, int pageSize) {
        return deliveryRepository.search(deliveryDto, PageRequest.of(pageIndex, pageSize));
    }

    @Override
    public String getCustomerNameOfDelivery(String deliveryId, String customerId) {
        return deliveryRepository.getCustomerNameOfDelivery(deliveryId, customerId);
    }

    @Override
    public DeliveryEntity getById(String id) {
        return deliveryRepository.findById(Long.valueOf(id))
                .orElseThrow();
    }

    @Override
    public Resource exportDeliveries(DeliverySearchDto deliveryDto) {
        try {
            final Page<DeliveryEntity> samplePage = search(deliveryDto, 0, 1);
            final long totalElements = samplePage.getTotalElements();

            final Page<DeliveryEntity> allItemsPage = search(deliveryDto, 0, (int) totalElements);
            final List<DeliveryEntity> allItems = allItemsPage.getContent();

//            "event_id", "delivery_period_year", "delivery_period_month", "territory", "region", "organ_id", "received_organ", "amd_name",
//            "customer_level", "customer_id", "customer_name", "id_card_number_raw", "id_card_number", "phone_number_raw", "phone_number",
//            "address", "gift_name", "note"

            final byte[] byteData = new ExcelWriteHelper()
                    .addSheetData(
                            new ExcelDataConverterModel<DeliveryEntity>()
                                    .setSheetName("Deliveries")
                                    .setRawData(allItems)
                                    //-- Mark: DELIVERY
                                    .addColumnConverters("event_id", DeliveryEntity::getEventId)
                                    .addColumnConverters("delivery_period_year", it -> String.valueOf(it.getDeliveryPeriodYear()))
                                    .addColumnConverters("delivery_period_month", it -> String.valueOf(it.getDeliveryPeriodMonth()))
                                    .addColumnConverters("territory", DeliveryEntity::getTerritory)
                                    .addColumnConverters("region", DeliveryEntity::getRegion)
                                    .addColumnConverters("organ_id", DeliveryEntity::getOrganId)
                                    .addColumnConverters("received_organ", DeliveryEntity::getReceivedOrgan)
                                    .addColumnConverters("amd_name", DeliveryEntity::getAmdName)
                                    .addColumnConverters("customer_level", DeliveryEntity::getCustomerLevel)
                                    .addColumnConverters("customer_id", DeliveryEntity::getCustomerId)
                                    .addColumnConverters("customer_name", DeliveryEntity::getCustomerName)
                                    .addColumnConverters("id_card_number", DeliveryEntity::getIdCardNumber)
                                    .addColumnConverters("phone_number", DeliveryEntity::getPhoneNumber)
                                    .addColumnConverters("address", DeliveryEntity::getAddress)
                                    .addColumnConverters("gift_name", DeliveryEntity::getGiftName)
                                    .addColumnConverters("note", DeliveryEntity::getNote)
                            //-- Mark: DELIVERY ATTEMPT
                            //-- Mark: DELIVERY RETURN
                    )
                    .build();

            return new ByteArrayResource(byteData);
        } catch (Throwable ex) {
            throw new ApiResponseException(ex);
        }
    }
}
