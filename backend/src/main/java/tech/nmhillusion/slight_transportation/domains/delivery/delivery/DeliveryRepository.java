package tech.nmhillusion.slight_transportation.domains.delivery.delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

    @Query(" select d from DeliveryEntity d where :recipientId is null or d.recipientId = :recipientId ")
    Page<DeliveryEntity> search(String recipientId, PageRequest pageRequest);

    @Query(" select max(d.deliveryId) from DeliveryEntity d ")
    long getMaxId();

}
