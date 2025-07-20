package tech.nmhillusion.corgi_gift_delivery.domains.deliveryStatus;

import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryStatusEntity;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public interface DeliveryStatusService {
    DeliveryStatusEntity getDeliveryStatusByStatusName(String statusName);
}
