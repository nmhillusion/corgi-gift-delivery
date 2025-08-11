package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnEntity;
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
public class DeliveryReturnServiceImpl extends BaseBusinessServiceImpl<DeliveryReturnEntity, DeliveryReturnRepository> implements DeliveryReturnService {
    private final DeliveryReturnExcelSheetParser deliverReturnExcelSheetParser;

    protected DeliveryReturnServiceImpl(DeliveryReturnRepository repository, SequenceService sequenceService, DeliveryReturnExcelSheetParser deliverReturnExcelSheetParser) {
        super(repository, sequenceService);
        this.deliverReturnExcelSheetParser = deliverReturnExcelSheetParser;
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
    public DeliveryReturnEntity getLatestAttemptByDeliveryId(String deliveryId) {
        try {
            final Long latestReturnId = repository.getMaxReturnIdOfDeliveryId(Long.parseLong(deliveryId));

            if (null == latestReturnId) {
                throw new NotFoundException("Not found latest deliver return, delivery id: " + deliveryId);
            }

            return repository.findById(latestReturnId)
                    .orElseThrow();
        } catch (Throwable ex) {
            LogHelper.getLogger(this).error(ex);
            throw new ApiResponseException(ex);
        }
    }
}
