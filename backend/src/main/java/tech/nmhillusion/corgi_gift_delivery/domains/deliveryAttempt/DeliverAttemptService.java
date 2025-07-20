package tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliverAttemptEntity;
import tech.nmhillusion.corgi_gift_delivery.service.business.BaseBusinessService;
import tech.nmhillusion.corgi_gift_delivery.service.business.BaseDeliveryService;
import tech.nmhillusion.n2mix.exception.ApiResponseException;

import java.util.List;

public interface DeliverAttemptService extends BaseBusinessService<DeliverAttemptEntity>, BaseDeliveryService<DeliverAttemptEntity, DeliverAttemptDto> {
    Long getMaxAttemptIdOfDeliveryId(Long deliveryId);

    List<DeliverAttemptEntity> insertBatchByExcelFile(MultipartFile excelFile) throws ApiResponseException;

    List<DeliverAttemptEntity> updateBatchByExcelFile(MultipartFile excelFile);

    Page<DeliverAttemptEntity> search(DeliverAttemptDto deliveryDto, int pageIndex, int pageSize);
}
