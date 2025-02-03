package tech.nmhillusion.slight_transportation.domains.delivery.deliveryType;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryTypeEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-03
 */
public interface DeliveryTypeRepository extends JpaRepository<DeliveryTypeEntity, Integer> {
}
