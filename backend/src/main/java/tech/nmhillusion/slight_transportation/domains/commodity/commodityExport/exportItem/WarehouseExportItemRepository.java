package tech.nmhillusion.slight_transportation.domains.commodity.commodityExport.exportItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.WarehouseItemExportEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
public interface WarehouseExportItemRepository extends JpaRepository<WarehouseItemExportEntity, String> {

    @Query("select e from WarehouseItemExportEntity e where e.exportId = :exportId")
    Page<WarehouseItemExportEntity> search(long exportId, PageRequest pageRequest);

}
