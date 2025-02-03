package tech.nmhillusion.slight_transportation.domains.shipper.shipperType;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nmhillusion.slight_transportation.entity.business.ShipperTypeEntity;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-01-26
 */
public interface ShipperTypeRepository extends JpaRepository<ShipperTypeEntity, Integer> {
}
