package tech.nmhillusion.slight_transportation.domains.delivery.deliveryAttempt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryAttemptEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-03
 */
public interface DeliveryAttemptRepository extends JpaRepository<DeliveryAttemptEntity, Long> {

    @Query(" select d from DeliveryAttemptEntity d where d.deliveryId = :deliveryId ")
    Page<DeliveryAttemptEntity> search(long deliveryId, PageRequest pageRequest);
}
