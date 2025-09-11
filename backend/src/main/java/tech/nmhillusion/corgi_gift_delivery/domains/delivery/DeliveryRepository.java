package tech.nmhillusion.corgi_gift_delivery.domains.delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryAttemptEntity;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;

import java.util.Optional;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

    @Query(value = "SELECT d FROM DeliveryEntity d WHERE d.eventId = :eventId AND d.customerId = :customerId")
    Optional<DeliveryEntity> findByEventIdAndCustomerId(String eventId, String customerId);

    @Query(value = """
            SELECT d FROM DeliveryEntity d
            LEFT JOIN DeliveryAttemptEntity ea ON d.deliveryId = ea.deliveryId AND ea.attemptId = (
                SELECT MAX(dea2.attemptId) FROM DeliveryAttemptEntity dea2 WHERE dea2.deliveryId = d.deliveryId
            )
            left join DeliveryReturnEntity r on r.deliveryId = d.deliveryId AND r.returnId = (
                SELECT MAX(r2.returnId) FROM DeliveryReturnEntity r2 WHERE r2.deliveryId = d.deliveryId
            )
            WHERE (:#{#dto.eventId} IS NULL OR d.eventId = :#{#dto.eventId})
            AND (:#{#dto.customerId} IS NULL OR d.customerId = :#{#dto.customerId})
            AND (:#{#dto.deliveryStatusId} IS NULL OR ea.deliveryStatusId = :#{#dto.deliveryStatusId})
            AND (:#{#dto.returnStatusId} IS NULL OR r.returnStatusId = :#{#dto.returnStatusId})
            """)
    Page<DeliveryEntity> search(DeliverySearchDto dto, PageRequest pageRequest);

    @Query(value = "SELECT d.customerName FROM DeliveryEntity d WHERE d.deliveryId = :deliveryId AND d.customerId = :customerId")
    String getCustomerNameOfDelivery(String deliveryId, String customerId);

}
