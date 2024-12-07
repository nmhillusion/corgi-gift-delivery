package tech.nmhillusion.slight_transportation.domains.shipper.shipper;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nmhillusion.slight_transportation.entity.business.ShipperEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-30
 */
public interface ShipperRepository extends JpaRepository<ShipperEntity, String> {
}
