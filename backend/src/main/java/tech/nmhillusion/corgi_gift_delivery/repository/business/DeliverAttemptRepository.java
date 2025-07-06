package tech.nmhillusion.corgi_gift_delivery.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliverAttemptEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public interface DeliverAttemptRepository extends JpaRepository<DeliverAttemptEntity, Long> {
}
