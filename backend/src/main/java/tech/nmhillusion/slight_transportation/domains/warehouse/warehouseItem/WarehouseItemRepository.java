package tech.nmhillusion.slight_transportation.domains.warehouse.warehouseItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.WarehouseItemEntity;

import java.time.ZonedDateTime;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-15
 */
public interface WarehouseItemRepository extends JpaRepository<WarehouseItemEntity, Long> {

    @Query("select w from WarehouseItemEntity w where w.warehouseId = :warehouseId and w.createTime between :from and :to")
    Page<WarehouseItemEntity> searchItemsInWarehouse(int warehouseId, ZonedDateTime from, ZonedDateTime to, PageRequest pageRequest);

    @Query("select w from WarehouseItemEntity w where w.importId = :importId and w.createTime between :from and :to")
    Page<WarehouseItemEntity> searchItemsInImport(int importId, ZonedDateTime from, ZonedDateTime to, PageRequest pageRequest);

    @Query("select max(w.itemId) from WarehouseItemEntity w")
    long getMaxId();
}
