package tech.nmhillusion.slight_transportation.domains.delivery.deliveryStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryStatusEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-16
 */
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatusEntity, String> {
}
