package tech.nmhillusion.slight_transportation.domains.commodity.commodityType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.CommodityTypeEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-23
 */
public interface CommodityTypeRepository extends JpaRepository<CommodityTypeEntity, String> {

    @Query("select max(t.typeId) from CommodityTypeEntity t")
    int getMaxTypeId();

    @Query("select t from CommodityTypeEntity t where :keyword is null or (t.typeName like %:keyword% or t.typeId like %:keyword%) ")
    Page<CommodityTypeEntity> search(String keyword, PageRequest pageRequest);
}
