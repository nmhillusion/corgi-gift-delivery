package tech.nmhillusion.slight_transportation.domains.warehouse;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nmhillusion.slight_transportation.entity.business.WarehouseEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-08
 */
public interface WarehouseRepository extends JpaRepository<WarehouseEntity, String> {
}
