package tech.nmhillusion.slight_transportation.domains.shipper.shipperType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.nmhillusion.slight_transportation.entity.business.ShipperTypeEntity;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-30
 */
@Repository
public interface ShipperTypeRepository extends JpaRepository<ShipperTypeEntity, String> {
}
