package tech.nmhillusion.slight_transportation.domains.sequence;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nmhillusion.slight_transportation.entity.core.SequenceEntity;

/**
 * created by: minguy1
 * <p>
 * created date: 2024-12-28
 */
public interface SequenceRepository extends JpaRepository<SequenceEntity, String> {

}
