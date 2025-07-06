package tech.nmhillusion.corgi_gift_delivery.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

    @Query("SELECT d FROM DeliveryEntity d WHERE d.eventId = :eventId AND d.customerId = :customerId")
    DeliveryEntity findByEventIdAndCustomerId(String eventId, String customerId);

}
