package tech.nmhillusion.slight_transportation.domains.note;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.NoteEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-18
 */
public interface NoteRepository extends JpaRepository<NoteEntity, String> {

    @Query(value = """
            select n
            from NoteEntity n
            where 1 = 1
            and (:recipientId is null or n.recipientId = :recipientId)
            and (:deliveryId is null or n.deliveryId = :deliveryId)
            and (:deliveryAttemptId is null or n.deliveryAttemptId = :deliveryAttemptId)
            and (:importId is null or n.importId = :importId)
            and (:warehouseItemId is null or n.warehouseItemId = :warehouseItemId)
            order by n.noteTime desc
            """)
    Page<NoteEntity> search(String recipientId,
                            String deliveryId,
                            String deliveryAttemptId,
                            String importId,
                            String warehouseItemId,
                            PageRequest pageRequest);
}
