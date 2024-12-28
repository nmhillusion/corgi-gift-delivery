package tech.nmhillusion.slight_transportation.domains.commodity.commodityImport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.CommodityImportEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-14
 */
public interface CommodityImportRepository extends JpaRepository<CommodityImportEntity, Long> {

    @Query("select max(t.importId) from CommodityImportEntity t")
    long getMaxId();

    @Query("select c from CommodityImportEntity c where :importName is null or c.importName like %:importName%")
    Page<CommodityImportEntity> search(String importName, PageRequest pageRequest);


}
