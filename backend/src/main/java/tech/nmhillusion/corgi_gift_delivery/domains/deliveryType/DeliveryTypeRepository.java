package tech.nmhillusion.corgi_gift_delivery.domains.deliveryType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryTypeEntity;

import java.util.Optional;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public interface DeliveryTypeRepository extends JpaRepository<DeliveryTypeEntity, String> {
    @Query("SELECT d FROM DeliveryTypeEntity d WHERE trim(lower(d.typeName)) = trim(lower(:typeName))")
    Optional<DeliveryTypeEntity> findByTypeName(String typeName);
}
