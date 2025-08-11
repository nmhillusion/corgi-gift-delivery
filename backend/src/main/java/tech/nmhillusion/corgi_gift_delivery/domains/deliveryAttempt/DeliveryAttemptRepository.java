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
    Long getMaxAttemptIdOfDeliveryId(long deliveryId);

    @Query(value = """
            SELECT a FROM DeliveryAttemptEntity a
            join DeliveryEntity d
            on d.deliveryId = a.deliveryId
            where 1 = 1
            and (:#{#dto.eventId} is null or d.eventId = :#{#dto.eventId})
            and (:#{#dto.customerId} is null or d.customerId = :#{#dto.customerId})
            """)
    Page<DeliveryAttemptEntity> search(DeliveryAttemptSearchDto dto, PageRequest pageRequest);
}
