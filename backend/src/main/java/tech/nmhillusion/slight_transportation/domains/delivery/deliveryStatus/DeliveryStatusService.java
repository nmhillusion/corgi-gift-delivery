package tech.nmhillusion.slight_transportation.domains.delivery.deliveryStatus;

import tech.nmhillusion.slight_transportation.entity.business.DeliveryStatusEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-16
 */
public interface DeliveryStatusService {
    List<DeliveryStatusEntity> list();
}
