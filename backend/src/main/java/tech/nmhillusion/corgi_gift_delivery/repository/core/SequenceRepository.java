package tech.nmhillusion.corgi_gift_delivery.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nmhillusion.corgi_gift_delivery.entity.core.SequenceEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public interface SequenceRepository extends JpaRepository<SequenceEntity, String> {
}
