package tech.nmhillusion.corgi_gift_delivery.domains.delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Query("SELECT d FROM DeliveryEntity d")
    Page<DeliveryEntity> search(DeliveryDto deliveryDto, PageRequest pageRequest);

    @Query("SELECT d.customerName FROM DeliveryEntity d WHERE d.deliveryId = :deliveryId AND d.customerId = :customerId")
    String getCustomerNameOfDelivery(String deliveryId, String customerId);

}
