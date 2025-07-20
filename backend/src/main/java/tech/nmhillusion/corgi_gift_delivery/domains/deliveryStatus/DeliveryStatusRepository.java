package tech.nmhillusion.corgi_gift_delivery.domains.deliveryStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryStatusEntity;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatusEntity, String> {
    @Query("SELECT d FROM DeliveryStatusEntity d WHERE trim(lower(d.statusName)) = trim(lower(:statusName))")
    DeliveryStatusEntity findByStatusName(String statusName);
}
