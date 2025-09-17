package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturnStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnStatusEntity;

import java.util.Optional;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public interface DeliveryReturnStatusRepository extends JpaRepository<DeliveryReturnStatusEntity, String> {
    @Query("SELECT d FROM DeliveryReturnStatusEntity d WHERE trim(lower(d.statusName)) = trim(lower(:statusName))")
    Optional<DeliveryReturnStatusEntity> findByStatusName(String statusName);
}
