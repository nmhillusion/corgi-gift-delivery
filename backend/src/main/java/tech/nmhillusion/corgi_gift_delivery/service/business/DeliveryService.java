package tech.nmhillusion.corgi_gift_delivery.service.business;

import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;
import tech.nmhillusion.n2mix.exception.ApiResponseException;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public interface DeliveryService extends BaseBusinessService<DeliveryEntity> {
    Long getDeliveryIdByEventAndCustomer(String eventId, String customerId);

    List<DeliveryEntity> insertBatchByExcelFile(MultipartFile excelFile) throws ApiResponseException;

    List<DeliveryEntity> updateBatchByExcelFile(MultipartFile excelFile);

}
