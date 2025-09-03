package tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt;

import org.apache.commons.lang3.stream.Streams;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.domains.delivery.DeliveryService;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryStatus.DeliveryStatusService;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryType.DeliveryTypeService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryAttemptEntity;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryStatusEntity;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryTypeEntity;
import tech.nmhillusion.corgi_gift_delivery.helper.BeanHelper;
import tech.nmhillusion.corgi_gift_delivery.service.business.AbstractBaseDeliveryService;
import tech.nmhillusion.corgi_gift_delivery.service.core.SequenceService;
import tech.nmhillusion.n2mix.exception.ApiResponseException;
import tech.nmhillusion.n2mix.exception.MissingDataException;
import tech.nmhillusion.n2mix.exception.NotFoundException;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.helper.office.excel.writer.ExcelWriteHelper;
import tech.nmhillusion.n2mix.helper.office.excel.writer.model.BasicExcelDataModel;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
@TransactionalService
public class DeliveryAttemptServiceImpl extends AbstractBaseDeliveryService<DeliveryAttemptEntity, DeliveryAttemptSearchDto, DeliveryAttemptRepository> implements DeliveryAttemptService {
    private final DeliveryAttemptExcelSheetParser deliveryAttemptExcelSheetParser;
    private final BeanHelper beanHelper;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    protected DeliveryAttemptServiceImpl(DeliveryAttemptRepository deliveryAttemptRepository, SequenceService sequenceService, DeliveryAttemptExcelSheetParser deliveryAttemptExcelSheetParser, BeanHelper beanHelper
    ) {
        super(deliveryAttemptRepository, sequenceService);
        this.deliveryAttemptExcelSheetParser = deliveryAttemptExcelSheetParser;
        this.beanHelper = beanHelper;
    }

    @Override
    public Long getMaxAttemptIdOfDeliveryId(Long deliveryId) {
        return repository.getMaxAttemptIdOfDeliveryId(deliveryId);
    }

    @Override
    public List<DeliveryAttemptEntity> insertBatchByExcelFile(MultipartFile excelFile) throws ApiResponseException {
        try {
            final List<DeliveryAttemptEntity> combinedList = parseExcelFileToEntityList(excelFile, deliveryAttemptExcelSheetParser::parse);

            return saveBatch(combinedList);
        } catch (Throwable ex) {
            throw new ApiResponseException(ex);
        }
    }

    @Override
    public List<DeliveryAttemptEntity> updateBatchByExcelFile(MultipartFile excelFile) {
        try {
            final List<DeliveryAttemptEntity> entityList = parseExcelFileToEntityList(excelFile, deliveryAttemptExcelSheetParser::parse);

            for (DeliveryAttemptEntity entity_ : entityList) {
                final Long maxAttemptId = repository.getMaxAttemptIdOfDeliveryId(entity_.getDeliveryId());
                if (null == maxAttemptId) {
                    throw new NotFoundException(
                            MessageFormat.format(
                                    "Latest deliver attempt not found to update, delivery id: {0}"
                                    , entity_.getDeliveryId()
                            )
                    );
                }

                entity_.setAttemptId(maxAttemptId);
            }

            return repository.saveAllAndFlush(entityList);
        } catch (Throwable ex) {
            throw new ApiResponseException(ex);
        }
    }

    @Override
    public Page<DeliveryAttemptEntity> search(DeliveryAttemptSearchDto dto, int pageIndex, int pageSize) {
        return repository.search(dto, PageRequest.of(pageIndex, pageSize));
    }

    @Override
    public DeliveryAttemptEntity getById(String id) {
        return repository.findById(Long.valueOf(id))
                .orElseThrow();
    }

    @Override
    public Optional<DeliveryAttemptEntity> getLatestAttemptByDeliveryId(String deliveryId) {
        final Long latestAttemptId = repository.getMaxAttemptIdOfDeliveryId(Long.parseLong(deliveryId));

        if (null == latestAttemptId) {
            LogHelper.getLogger(this).warn("Not found latest deliver attempt, delivery id: " + deliveryId);
            return Optional.empty();
        }

        return repository.findById(latestAttemptId);
    }

    @Override
    public Resource export(DeliveryAttemptSearchDto deliveryAttemptSearchDto) throws ApiResponseException {
        final long totalElements = getTotalElementsForSearch(deliveryAttemptSearchDto);

        final Page<DeliveryAttemptEntity> resultPage = search(deliveryAttemptSearchDto, 0, (int) totalElements);

        final DeliveryService deliveryService = beanHelper.injectForService(DeliveryService.class);
        final DeliveryTypeService deliveryTypeService = beanHelper.injectForService(DeliveryTypeService.class);
        final DeliveryStatusService deliveryAttemptStatusService = beanHelper.injectForService(DeliveryStatusService.class);

        try {
            final byte[] byteData = new ExcelWriteHelper()
                    .addSheetData(
                            new BasicExcelDataModel()
                                    .setSheetName("DeliveryAttempts")
                                    .setHeaders(
                                            List.of(
                                                    Streams.of(
                                                                    DeliveryAttemptParserEnum.values()
                                                            ).map(DeliveryAttemptParserEnum::getColumnName)
                                                            .toList()
                                            )
                                    )
                                    .setBodyData(
                                            resultPage.getContent()
                                                    .stream()
                                                    .map(it -> {
                                                        final List<String> dRow = new ArrayList<>();

                                                        final DeliveryEntity delivery = deliveryService.getById(
                                                                String.valueOf(it.getDeliveryId())
                                                        );

                                                        dRow.add(delivery.getEventId());
                                                        dRow.add(delivery.getCustomerId());
                                                        dRow.add(
                                                                Optional.ofNullable(deliveryTypeService.getDeliveryTypeByTypeId(
                                                                                String.valueOf(it.getDeliveryTypeId())
                                                                        ))
                                                                        .map(DeliveryTypeEntity::getTypeName)
                                                                        .orElse("")
                                                        );
                                                        dRow.add(
                                                                Optional.ofNullable(deliveryAttemptStatusService.getDeliveryStatusByStatusId(
                                                                                String.valueOf(it.getDeliveryStatusId())
                                                                        ))
                                                                        .map(DeliveryStatusEntity::getStatusName)
                                                                        .orElse("")
                                                        );
                                                        dRow.add(
                                                                Optional.ofNullable(it.getDeliveryDate())
                                                                        .map(dateTimeFormatter::format)
                                                                        .orElse("")
                                                        );
                                                        dRow.add(it.getNote());

                                                        return dRow;
                                                    })
                                                    .toList()
                                    )
                    )
                    .build();
            return new ByteArrayResource(byteData)
                    ;
        } catch (IOException | MissingDataException e) {
            throw new ApiResponseException(e);
        }
    }
}