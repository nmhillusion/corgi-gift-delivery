package tech.nmhillusion.corgi_gift_delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
}
