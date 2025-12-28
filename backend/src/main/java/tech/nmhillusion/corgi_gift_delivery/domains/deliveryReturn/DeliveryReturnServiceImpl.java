package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturn;

import org.apache.commons.lang3.stream.Streams;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.domains.delivery.DeliveryService;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturnStatus.DeliveryReturnStatusService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnEntity;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnStatusEntity;
import tech.nmhillusion.corgi_gift_delivery.helper.BeanHelper;
import tech.nmhillusion.corgi_gift_delivery.service.business.AbstractBaseDeliveryService;
import tech.nmhillusion.corgi_gift_delivery.service.core.SequenceService;
import tech.nmhillusion.n2mix.exception.ApiResponseException;
import tech.nmhillusion.n2mix.exception.NotFoundException;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.helper.office.excel.writer.ExcelWriteHelper;
import tech.nmhillusion.n2mix.helper.office.excel.writer.model.BasicExcelDataModel;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */

@TransactionalService
public class DeliveryReturnServiceImpl extends AbstractBaseDeliveryService<DeliveryReturnEntity, DeliveryReturnSearchDto, DeliveryReturnRepository> implements DeliveryReturnService {
    private final DeliveryReturnExcelSheetParser deliverReturnExcelSheetParser;
    private final BeanHelper beanHelper;

    protected DeliveryReturnServiceImpl(DeliveryReturnRepository repository, SequenceService sequenceService, DeliveryReturnExcelSheetParser deliverReturnExcelSheetParser, BeanHelper beanHelper) {
        super(repository, sequenceService);
        this.deliverReturnExcelSheetParser = deliverReturnExcelSheetParser;
        this.beanHelper = beanHelper;
    }

    @Override
    public Long getMaxReturnIdOfDeliveryId(Long deliveryId) {
        return repository.getMaxReturnIdOfDeliveryId(deliveryId);
    }

    @Override
    public List<DeliveryReturnEntity> insertBatchByExcelFile(MultipartFile excelFile) throws ApiResponseException {
        try {
            final List<DeliveryReturnEntity> combinedList = parseExcelFileToEntityList(excelFile, deliverReturnExcelSheetParser::parse);

            return saveBatch(combinedList);
        } catch (Throwable ex) {
            throw new ApiResponseException(ex);
        }
    }

    @Override
    public List<DeliveryReturnEntity> updateBatchByExcelFile(MultipartFile excelFile) throws ApiResponseException {
        try {
            final List<DeliveryReturnEntity> entityList = parseExcelFileToEntityList(excelFile, deliverReturnExcelSheetParser::parse);

            for (DeliveryReturnEntity entity_ : entityList) {
                final Long maxReturnId = repository.getMaxReturnIdOfDeliveryId(entity_.getDeliveryId());
                if (null == maxReturnId) {
                    throw new NotFoundException(
                            MessageFormat.format(
                                    "Latest deliver return not found to update, delivery id: {0}"
                                    , entity_.getDeliveryId()
                            )
                    );
                }

                entity_.setReturnId(maxReturnId);
            }

            return repository.saveAllAndFlush(entityList);
        } catch (Throwable ex) {
            throw new ApiResponseException(ex);
        }
    }

    @Override
    public Page<DeliveryReturnEntity> search(DeliveryReturnSearchDto dto, int pageIndex, int pageSize) {
        return repository.search(dto, PageRequest.of(pageIndex, pageSize));
    }

    @Override
    public DeliveryReturnEntity getById(String id) {
        return repository.findById(Long.valueOf(id))
                .orElseThrow();
    }

    @Override
    public Optional<DeliveryReturnEntity> getLatestReturnByDeliveryId(String deliveryId) {
        final Long latestReturnId = repository.getMaxReturnIdOfDeliveryId(Long.parseLong(deliveryId));

        if (null == latestReturnId) {
            LogHelper.getLogger(this).warn("Not found latest deliver return, delivery id: " + deliveryId);
            return Optional.empty();
        }

        return repository.findById(latestReturnId);
    }

    @Override
    public Resource export(DeliveryReturnSearchDto deliveryReturnSearchDto) throws ApiResponseException {
        try {
            final long totalElements = getTotalElementsForSearch(deliveryReturnSearchDto);

            final Page<DeliveryReturnEntity> resultPage = search(deliveryReturnSearchDto, 0, (int) totalElements);

            final DeliveryService deliveryService = beanHelper.injectForService(DeliveryService.class);
            final DeliveryReturnStatusService deliveryReturnStatusService = beanHelper.injectForService(DeliveryReturnStatusService.class);

            final byte[] byteData = new ExcelWriteHelper()
                    .addSheetData(
                            new BasicExcelDataModel()
                                    .setSheetName("DeliveryReturns")
                                    .setHeaders(
                                            List.of(
                                                    Streams.of(DeliveryReturnParserEnum.values())
                                                            .map(DeliveryReturnParserEnum::getColumnName)
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
                                                                Optional.ofNullable(deliveryReturnStatusService.getDeliveryReturnStatusByStatusId(
                                                                                String.valueOf(it.getReturnStatusId())
                                                                        ))
                                                                        .map(DeliveryReturnStatusEntity::getStatusName)
                                                                        .orElse("")
                                                        );
                                                        dRow.add(it.getNote());

                                                        return dRow;
                                                    })
                                                    .toList()
                                    )
                    )
                    .build();

            return new ByteArrayResource(byteData);
        } catch (Throwable ex) {
            throw new ApiResponseException(ex);
        }
    }

    @Override
    public DeliveryReturnEntity updateById(String id, DeliveryReturnEntity mEntity) {
        mEntity.setId(Long.parseLong(id));
        return repository.saveAndFlush(mEntity);
    }
}
