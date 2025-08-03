package tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliverAttemptEntity;
import tech.nmhillusion.corgi_gift_delivery.service.core.SequenceService;
import tech.nmhillusion.corgi_gift_delivery.service_impl.business.BaseBusinessServiceImpl;
import tech.nmhillusion.n2mix.exception.ApiResponseException;
import tech.nmhillusion.n2mix.exception.NotFoundException;

import java.text.MessageFormat;
import java.util.List;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
@TransactionalService
public class DeliverAttemptServiceImpl extends BaseBusinessServiceImpl<DeliverAttemptEntity, DeliverAttemptRepository> implements DeliverAttemptService {
    private final DeliverAttemptExcelSheetParser deliverAttemptExcelSheetParser;

    protected DeliverAttemptServiceImpl(DeliverAttemptRepository deliverAttemptRepository, SequenceService sequenceService, DeliverAttemptExcelSheetParser deliverAttemptExcelSheetParser) {
        super(deliverAttemptRepository, sequenceService);
        this.deliverAttemptExcelSheetParser = deliverAttemptExcelSheetParser;
    }

    @Override
    public Long getMaxAttemptIdOfDeliveryId(Long deliveryId) {
        return repository.getMaxAttemptIdOfDeliveryId(deliveryId);
    }

    @Override
    public List<DeliverAttemptEntity> insertBatchByExcelFile(MultipartFile excelFile) throws ApiResponseException {
        try {
            final List<DeliverAttemptEntity> combinedList = parseExcelFileToEntityList(excelFile, deliverAttemptExcelSheetParser::parse);

            return saveBatch(combinedList);
        } catch (Throwable ex) {
            throw new ApiResponseException(ex);
        }
    }

    @Override
    public List<DeliverAttemptEntity> updateBatchByExcelFile(MultipartFile excelFile) {
        try {
            final List<DeliverAttemptEntity> entityList = parseExcelFileToEntityList(excelFile, deliverAttemptExcelSheetParser::parse);

            for (DeliverAttemptEntity entity_ : entityList) {
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
    public Page<DeliverAttemptEntity> search(DeliverAttemptDto dto, int pageIndex, int pageSize) {
        return repository.search(dto, PageRequest.of(pageIndex, pageSize));
    }

    @Override
    public DeliverAttemptEntity getById(String id) {
        return repository.findById(Long.valueOf(id))
                .orElseThrow();
    }
}
