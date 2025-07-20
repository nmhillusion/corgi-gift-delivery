package tech.nmhillusion.corgi_gift_delivery.domains.delivery;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.domains.base.BaseDeliveryService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;
import tech.nmhillusion.corgi_gift_delivery.service.business.BaseBusinessService;
import tech.nmhillusion.n2mix.exception.ApiResponseException;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public interface DeliveryService extends BaseBusinessService<DeliveryEntity>, BaseDeliveryService<DeliveryEntity, DeliveryDto> {
    Long getDeliveryIdByEventAndCustomer(String eventId, String customerId);

    List<DeliveryEntity> insertBatchByExcelFile(MultipartFile excelFile) throws ApiResponseException;

    List<DeliveryEntity> updateBatchByExcelFile(MultipartFile excelFile);

    Page<DeliveryEntity> search(DeliveryDto deliveryDto, int pageIndex, int pageSize);
}
