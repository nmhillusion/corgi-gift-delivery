package tech.nmhillusion.corgi_gift_delivery.repository.business;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturn.DeliveryReturnDto;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public interface DeliveryReturnRepository extends JpaRepository<DeliveryReturnEntity, Long> {
    @Query(value = "SELECT MAX(t.returnId) FROM DeliveryReturnEntity t WHERE t.deliveryId = :deliveryId")
    Long getMaxReturnIdOfDeliveryId(Long deliveryId);

    @Query(value = "SELECT t FROM DeliveryReturnEntity t")
    Page<DeliveryReturnEntity> search(DeliveryReturnDto dto, PageRequest pageRequest);
}
