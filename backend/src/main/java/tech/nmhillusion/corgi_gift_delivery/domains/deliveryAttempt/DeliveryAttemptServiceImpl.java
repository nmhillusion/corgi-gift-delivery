package tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryAttemptEntity;
import tech.nmhillusion.corgi_gift_delivery.service.core.SequenceService;
import tech.nmhillusion.corgi_gift_delivery.service_impl.business.BaseBusinessServiceImpl;
import tech.nmhillusion.n2mix.exception.ApiResponseException;
import tech.nmhillusion.n2mix.exception.NotFoundException;
import tech.nmhillusion.n2mix.helper.log.LogHelper;

import java.text.MessageFormat;
import java.util.List;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
@TransactionalService
public class DeliveryAttemptServiceImpl extends BaseBusinessServiceImpl<DeliveryAttemptEntity, DeliveryAttemptRepository> implements DeliveryAttemptService {
    private final DeliveryAttemptExcelSheetParser deliveryAttemptExcelSheetParser;

    protected DeliveryAttemptServiceImpl(DeliveryAttemptRepository deliveryAttemptRepository, SequenceService sequenceService, DeliveryAttemptExcelSheetParser deliveryAttemptExcelSheetParser) {
        super(deliveryAttemptRepository, sequenceService);
        this.deliveryAttemptExcelSheetParser = deliveryAttemptExcelSheetParser;
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
    public DeliveryAttemptEntity getLatestAttemptByDeliveryId(String deliveryId) {
        try {
            final Long latestAttemptId = repository.getMaxAttemptIdOfDeliveryId(Long.parseLong(deliveryId));

            if (null == latestAttemptId) {
                throw new NotFoundException("Not found latest delivery attempt, delivery id: " + deliveryId);
            }

            return repository.findById(latestAttemptId)
                    .orElseThrow();
        } catch (Throwable ex) {
            LogHelper.getLogger(this).error(ex);
            throw new ApiResponseException(ex);
        }
    }
}
