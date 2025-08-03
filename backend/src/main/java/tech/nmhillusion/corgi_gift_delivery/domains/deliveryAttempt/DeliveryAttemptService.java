package tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryAttemptEntity;
import tech.nmhillusion.corgi_gift_delivery.service.business.BaseBusinessService;
import tech.nmhillusion.corgi_gift_delivery.service.business.BaseDeliveryService;
import tech.nmhillusion.n2mix.exception.ApiResponseException;

import java.util.List;

public interface DeliveryAttemptService extends BaseBusinessService<DeliveryAttemptEntity>, BaseDeliveryService<DeliveryAttemptEntity, DeliveryAttemptDto> {
    Long getMaxAttemptIdOfDeliveryId(Long deliveryId);

    List<DeliveryAttemptEntity> insertBatchByExcelFile(MultipartFile excelFile) throws ApiResponseException;

    List<DeliveryAttemptEntity> updateBatchByExcelFile(MultipartFile excelFile);

    Page<DeliveryAttemptEntity> search(DeliveryAttemptDto deliveryDto, int pageIndex, int pageSize);
}
