package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturn;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnEntity;
import tech.nmhillusion.corgi_gift_delivery.service.business.BaseBusinessService;
import tech.nmhillusion.corgi_gift_delivery.service.business.BaseDeliveryService;
import tech.nmhillusion.n2mix.exception.ApiResponseException;
import tech.nmhillusion.n2mix.exception.NotFoundException;

import java.util.List;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public interface DeliveryReturnService extends BaseBusinessService<DeliveryReturnEntity>, BaseDeliveryService<DeliveryReturnEntity, DeliveryReturnDto> {
    Long getMaxReturnIdOfDeliveryId(Long deliveryId);

    List<DeliveryReturnEntity> insertBatchByExcelFile(MultipartFile excelFile) throws ApiResponseException;

    List<DeliveryReturnEntity> updateBatchByExcelFile(MultipartFile excelFile) throws ApiResponseException;

    Page<DeliveryReturnEntity> search(DeliveryReturnDto deliveryDto, int pageIndex, int pageSize);

    DeliveryReturnEntity getLatestAttemptByDeliveryId(String deliveryId);
}
