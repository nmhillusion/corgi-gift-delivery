package tech.nmhillusion.corgi_gift_delivery.domains.delivery;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt.DeliveryAttemptService;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturn.DeliveryReturnService;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturnStatus.DeliveryReturnStatusService;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryStatus.DeliveryStatusService;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryType.DeliveryTypeService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.*;
import tech.nmhillusion.corgi_gift_delivery.entity.business.export.LatestDeliveryReportEntity;
import tech.nmhillusion.corgi_gift_delivery.helper.BeanHelper;
import tech.nmhillusion.corgi_gift_delivery.service.business.AbstractBaseDeliveryService;
import tech.nmhillusion.corgi_gift_delivery.service.core.SequenceService;
import tech.nmhillusion.n2mix.exception.ApiResponseException;
import tech.nmhillusion.n2mix.exception.NotFoundException;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.helper.office.excel.writer.ExcelWriteHelper;
import tech.nmhillusion.n2mix.helper.office.excel.writer.model.BasicExcelDataModel;
import tech.nmhillusion.n2mix.helper.office.excel.writer.model.ExcelDataConverterModel;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */

@TransactionalService
public class DeliveryServiceImpl extends AbstractBaseDeliveryService<DeliveryEntity, DeliverySearchDto, DeliveryRepository> implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryExcelSheetParser deliveryExcelSheetParser;
    private final BeanHelper beanHelper;


    public DeliveryServiceImpl(DeliveryRepository deliveryRepository
            , SequenceService sequenceService
            , DeliveryExcelSheetParser deliveryExcelSheetParser
            , BeanHelper beanHelper
    ) {
        super(deliveryRepository, sequenceService);
        this.beanHelper = beanHelper;
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
    public Resource exportSummaryDeliveries(DeliverySearchDto deliveryDto) {
        try {
            final Page<DeliveryEntity> samplePage = search(deliveryDto, 0, 1);
            final long totalElements = samplePage.getTotalElements();

            final Page<DeliveryEntity> allItemsPage = search(deliveryDto, 0, (int) totalElements);
            final List<LatestDeliveryReportEntity> allItems = allItemsPage.getContent()
                    .stream()
                    .map(this::convertToLatestDeliveryReport)
                    .toList();

//            "event_id", "delivery_period_year", "delivery_period_month", "territory", "region", "organ_id", "received_organ", "amd_name",
//            "customer_level", "customer_id", "customer_name", "id_card_number_raw", "id_card_number", "phone_number_raw", "phone_number",
//            "address", "gift_name", "note"

            final DeliveryStatusService deliveryStatusService = beanHelper.injectForService(DeliveryStatusService.class);
            final DeliveryTypeService deliveryTypeService = beanHelper.injectForService(DeliveryTypeService.class);
            final DeliveryReturnStatusService deliveryReturnStatusService = beanHelper.injectForService(DeliveryReturnStatusService.class);

            final ExcelDataConverterModel<LatestDeliveryReportEntity> exportSheetData = new ExcelDataConverterModel<LatestDeliveryReportEntity>()
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
//                    .addColumnConverters("latest_attempt__attempt_id",
//                            it -> getValueIfPass(it::getLatestDeliveryAttempt, attempt -> String.valueOf(attempt.getAttemptId()), "")
//                    )
                    .addColumnConverters("latest_attempt__attempt_id",
                            it -> Optional.ofNullable(it.getLatestDeliveryAttempt())
                                    .map(DeliveryAttemptEntity::getAttemptId)
                                    .map(String::valueOf)
                                    .orElse("")
                    )
                    .addColumnConverters("latest_attempt__delivery_status",
                            it -> {
                                return Optional.ofNullable(it.getLatestDeliveryAttempt())
                                        .map(DeliveryAttemptEntity::getDeliveryStatusId)
                                        .map(String::valueOf)
                                        .map(deliveryStatusService::getDeliveryStatusByStatusId)
                                        .map(DeliveryStatusEntity::getStatusName)
                                        .orElse("");
                            }
                    )
                    .addColumnConverters("latest_attempt__delivery_type",
                            it -> Optional.ofNullable(it.getLatestDeliveryAttempt())
                                    .map(DeliveryAttemptEntity::getDeliveryTypeId)
                                    .map(String::valueOf)
                                    .map(deliveryTypeService::getDeliveryTypeByTypeId)
                                    .map(DeliveryTypeEntity::getTypeName)
                                    .orElse("")
                    )
                    .addColumnConverters("latest_attempt__note",
                            it -> Optional.ofNullable(it.getLatestDeliveryAttempt())
                                    .map(DeliveryAttemptEntity::getNote)
                                    .orElse("")
                    )
                    //-- Mark: DELIVERY RETURN
                    .addColumnConverters("latest_return__return_id",
                            it -> Optional.ofNullable(it.getLatestDeliveryReturn())
                                    .map(DeliveryReturnEntity::getReturnId)
                                    .map(String::valueOf)
                                    .orElse("")
                    )
                    .addColumnConverters("latest_return__return_status",
                            it -> Optional.ofNullable(it.getLatestDeliveryReturn())
                                    .map(DeliveryReturnEntity::getReturnStatusId)
                                    .map(String::valueOf)
                                    .map(deliveryReturnStatusService::getDeliveryReturnStatusByStatusId)
                                    .map(DeliveryReturnStatusEntity::getStatusName)
                                    .orElse("")
                    )
                    .addColumnConverters("latest_return__note",
                            it -> Optional.ofNullable(it.getLatestDeliveryReturn())
                                    .map(DeliveryReturnEntity::getNote)
                                    .orElse("")
                    );

            final byte[] byteData = new ExcelWriteHelper()
                    .addSheetData(exportSheetData)
                    .build();

            return new ByteArrayResource(byteData);
        } catch (Throwable ex) {
            throw new ApiResponseException(ex);
        }
    }

    @Override
    public LatestDeliveryReportEntity convertToLatestDeliveryReport(DeliveryEntity deliveryEntity) {
        final LatestDeliveryReportEntity latestDeliveryReport = new LatestDeliveryReportEntity();
        final String deliveryId = String.valueOf(deliveryEntity.getDeliveryId());
        latestDeliveryReport
                .setAddress(deliveryEntity.getAddress())
                .setAmdName(deliveryEntity.getAmdName())
                .setCustomerId(deliveryEntity.getCustomerId())
                .setCustomerLevel(deliveryEntity.getCustomerLevel())
                .setCustomerName(deliveryEntity.getCustomerName())
                .setDeliveryId(deliveryEntity.getDeliveryId())
                .setDeliveryPeriodMonth(deliveryEntity.getDeliveryPeriodMonth())
                .setDeliveryPeriodYear(deliveryEntity.getDeliveryPeriodYear())
                .setEventId(deliveryEntity.getEventId())
                .setGiftName(deliveryEntity.getGiftName())
                .setIdCardNumber(deliveryEntity.getIdCardNumber())
                .setInsertDate(deliveryEntity.getInsertDate())
                .setNote(deliveryEntity.getNote())
                .setOrganId(deliveryEntity.getOrganId())
                .setPhoneNumber(deliveryEntity.getPhoneNumber())
                .setReceivedOrgan(deliveryEntity.getReceivedOrgan())
                .setRegion(deliveryEntity.getRegion())
                .setTerritory(deliveryEntity.getTerritory())
                .setUpdateDate(deliveryEntity.getUpdateDate());

        final DeliveryAttemptService deliveryAttemptService = beanHelper.injectForService(DeliveryAttemptService.class);
        final DeliveryReturnService deliveryReturnService = beanHelper.injectForService(DeliveryReturnService.class);

        latestDeliveryReport.setLatestDeliveryAttempt(deliveryAttemptService.getLatestAttemptByDeliveryId(deliveryId).orElse(null));
        latestDeliveryReport.setLatestDeliveryReturn(deliveryReturnService.getLatestReturnByDeliveryId(deliveryId).orElse(null));

        return latestDeliveryReport;
    }

    @Override
    public Resource export(DeliverySearchDto deliverySearchDto) throws ApiResponseException {
        try {
            final long totalElements = getTotalElementsForSearch(deliverySearchDto);

            final Page<DeliveryEntity> resultPage = search(deliverySearchDto, 0, (int) totalElements);


            final byte[] data = new ExcelWriteHelper()
                    .addSheetData(
                            new BasicExcelDataModel()
                                    .setSheetName("Deliveries")
                                    .setHeaders(
                                            List.of(
                                                    Stream.of(
                                                                    DeliveryParserEnum.values()
                                                            )
                                                            .map(DeliveryParserEnum::getColumnName)
                                                            .toList()
                                            )
                                    )
                                    .setBodyData(
                                            resultPage.getContent()
                                                    .stream()
                                                    .map(it -> {
                                                                final List<String> dRow = new ArrayList<>();

                                                                dRow.add(it.getEventId());
                                                                dRow.add(String.valueOf(it.getDeliveryPeriodYear()));
                                                                dRow.add(String.valueOf(it.getDeliveryPeriodMonth()));
                                                                dRow.add(it.getTerritory());
                                                                dRow.add(it.getRegion());
                                                                dRow.add(it.getOrganId());
                                                                dRow.add(it.getReceivedOrgan());
                                                                dRow.add(it.getAmdName());
                                                                dRow.add(it.getCustomerLevel());
                                                                dRow.add(it.getCustomerId());
                                                                dRow.add(it.getCustomerName());
                                                                dRow.add(it.getIdCardNumber());
                                                                dRow.add(it.getPhoneNumber());
                                                                dRow.add(it.getAddress());
                                                                dRow.add(it.getGiftName());
                                                                dRow.add(it.getNote());

                                                                return dRow;
                                                            }
                                                    )
                                                    .toList()
                                    )
                    )
                    .build();
            return new ByteArrayResource(data)
                    ;
        } catch (Exception ex) {
            LogHelper.getLogger(this).error(ex);
            throw new ApiResponseException(ex);
        }
    }
}
