package tech.nmhillusion.corgi_gift_delivery.domains.deliveryStatus;

import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryStatusEntity;

import java.util.List;
import java.util.Optional;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public interface DeliveryStatusService {
    Optional<DeliveryStatusEntity> getDeliveryStatusByStatusName(String statusName);

    DeliveryStatusEntity getDeliveryStatusByStatusId(String statusId);

    List<DeliveryStatusEntity> getAll();

}
