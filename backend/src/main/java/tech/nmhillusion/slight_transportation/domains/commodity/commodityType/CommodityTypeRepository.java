package tech.nmhillusion.slight_transportation.domains.commodity.commodityType;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nmhillusion.slight_transportation.entity.business.CommodityTypeEntity;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-23
 */
public interface CommodityTypeRepository extends JpaRepository<CommodityTypeEntity, String> {
}
