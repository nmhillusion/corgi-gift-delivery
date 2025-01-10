package tech.nmhillusion.slight_transportation.domains.commodity.commodityExport.exportItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.WarehouseExportItemEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
public interface WarehouseExportItemRepository extends JpaRepository<WarehouseExportItemEntity, Long> {

    @Query("select e from WarehouseExportItemEntity e where e.exportId = :exportId")
    Page<WarehouseExportItemEntity> search(long exportId, PageRequest pageRequest);

    @Query("select max(e.itemId) from WarehouseExportItemEntity e")
    long getMaxId();

    @Query("""
            select sum(e.quantity)
              from WarehouseExportItemEntity e
             where e.warehouseId = :warehouseId
               and e.comId = :commodityId
           """)
    int getExportQuantityOfCommodityWarehouse(int warehouseId, int commodityId);
}
