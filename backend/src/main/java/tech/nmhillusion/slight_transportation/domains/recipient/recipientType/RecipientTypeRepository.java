package tech.nmhillusion.slight_transportation.domains.recipient.recipientType;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nmhillusion.slight_transportation.entity.business.RecipientTypeEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-09
 */

public interface RecipientTypeRepository extends JpaRepository<RecipientTypeEntity, Integer> {
}
