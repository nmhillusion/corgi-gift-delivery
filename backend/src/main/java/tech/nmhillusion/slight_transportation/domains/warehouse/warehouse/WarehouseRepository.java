package tech.nmhillusion.slight_transportation.domains.warehouse.warehouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.WarehouseEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-08
 */
public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Integer> {

    @Query("select max(t.warehouseId) from WarehouseEntity t")
    int getMaxId();

}
