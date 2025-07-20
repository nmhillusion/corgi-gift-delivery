package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturn;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnEntity;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public interface DeliveryReturnRepository extends JpaRepository<DeliveryReturnEntity, Long> {
}
