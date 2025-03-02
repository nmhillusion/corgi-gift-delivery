package tech.nmhillusion.slight_transportation.domains.delivery.deliverPackage.packageItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryPackageItemEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
public interface DeliveryPackageItemRepository extends JpaRepository<DeliveryPackageItemEntity, String> {

    @Query("select i from DeliveryPackageItemEntity i where i.packageId = :packageId")
    Page<DeliveryPackageItemEntity> search(String packageId, PageRequest pageRequest);

    @Query("select max(i.itemId) from DeliveryPackageItemEntity i")
    long getMaxId();

    @Query(" select i from DeliveryPackageItemEntity i where i.packageId = :packageId ")
    List<DeliveryPackageItemEntity> getAllItemsOfPackage(String packageId);
}
