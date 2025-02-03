package tech.nmhillusion.slight_transportation.domains.shipper.shipper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.ShipperEntity;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-01-26
 */
public interface ShipperRepository extends JpaRepository<ShipperEntity, Integer> {

    @Query(" select s from ShipperEntity s where :name is null or lower(s.shipperName) like %:name% ")
    Page<ShipperEntity> search(String name, PageRequest pageRequest);
}
