package tech.nmhillusion.slight_transportation.domains.delivery.deliverPackage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryPackageEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
public interface DeliveryPackageRepository extends JpaRepository<DeliveryPackageEntity, Long> {

    @Query("select d from DeliveryPackageEntity d where d.deliveryId = :deliveryId")
    Page<DeliveryPackageEntity> search(long deliveryId, PageRequest pageRequest);

}
