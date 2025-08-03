package tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryAttemptEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public interface DeliveryAttemptRepository extends JpaRepository<DeliveryAttemptEntity, Long> {

    @Query(value = "SELECT MAX(t.attemptId) FROM DeliveryAttemptEntity t WHERE t.deliveryId = :deliveryId")
    Long getMaxAttemptIdOfDeliveryId(Long deliveryId);

    @Query(value = "SELECT t FROM DeliveryAttemptEntity t")
    Page<DeliveryAttemptEntity> search(DeliveryAttemptDto dto, PageRequest pageRequest);
}
