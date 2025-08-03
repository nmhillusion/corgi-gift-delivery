package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturnStatus;

import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnStatusEntity;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public interface DeliveryReturnStatusService {
    DeliveryReturnStatusEntity getDeliveryReturnStatusByStatusName(String statusName);

    DeliveryReturnStatusEntity getDeliveryReturnStatusByStatusId(String statusId);
}
