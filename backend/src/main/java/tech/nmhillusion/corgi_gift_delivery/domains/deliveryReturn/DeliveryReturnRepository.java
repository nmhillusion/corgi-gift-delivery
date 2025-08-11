package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnEntity;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public interface DeliveryReturnRepository extends JpaRepository<DeliveryReturnEntity, Long> {
    @Query(value = "SELECT MAX(t.returnId) FROM DeliveryReturnEntity t WHERE t.deliveryId = :deliveryId")
    Long getMaxReturnIdOfDeliveryId(Long deliveryId);

    @Query(value = """
            SELECT r FROM DeliveryReturnEntity r
            join DeliveryEntity d
            on d.deliveryId = r.deliveryId
            where 1 = 1
            and (:#{#dto.eventId} is null or d.eventId = :#{#dto.eventId})
            and (:#{#dto.customerId} is null or d.customerId = :#{#dto.customerId})
            """)
    Page<DeliveryReturnEntity> search(DeliveryReturnSearchDto dto, PageRequest pageRequest);
}
