package tech.nmhillusion.corgi_gift_delivery.service.business;

import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;

import java.io.IOException;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public interface DeliveryService extends BaseBusinessService<DeliveryEntity> {
    Long getDeliveryIdByEventAndCustomer(String eventId, String customerId);

    Iterable<DeliveryEntity> saveBatchByExcelFile(MultipartFile excelFile) throws IOException;
}
