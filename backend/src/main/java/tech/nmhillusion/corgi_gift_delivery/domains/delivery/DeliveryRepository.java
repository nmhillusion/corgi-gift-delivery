package tech.nmhillusion.corgi_gift_delivery.domains.delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
        WHERE 1 = 1
        and (:#{#dto.eventId} is null or d.eventId = :#{#dto.eventId})
        and (:#{#dto.customerId} is null or d.customerId = :#{#dto.customerId})
        """)
    Page<DeliveryEntity> search(DeliverySearchDto dto, PageRequest pageRequest);

    @Query(value = "SELECT d.customerName FROM DeliveryEntity d WHERE d.deliveryId = :deliveryId AND d.customerId = :customerId")
    String getCustomerNameOfDelivery(String deliveryId, String customerId);

}
