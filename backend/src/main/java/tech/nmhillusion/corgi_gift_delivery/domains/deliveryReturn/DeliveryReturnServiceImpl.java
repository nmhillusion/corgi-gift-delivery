package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturn;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnEntity;
import tech.nmhillusion.corgi_gift_delivery.service.business.AbstractBaseDeliveryService;
import tech.nmhillusion.corgi_gift_delivery.service.core.SequenceService;
import tech.nmhillusion.n2mix.exception.ApiResponseException;
import tech.nmhillusion.n2mix.exception.MissingDataException;
import tech.nmhillusion.n2mix.exception.NotFoundException;
import tech.nmhillusion.n2mix.helper.log.LogHelper;

import java.io.IOException;
import java.text.MessageFormat;
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
        throw new UnsupportedOperationException();
    }
}
