package tech.nmhillusion.slight_transportation.domains.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.NoteEntity;

import java.lang.constant.ConstantDesc;
import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-18
 */
public interface NoteRepository extends JpaRepository<NoteEntity, String> {
    @Query(" select n from NoteEntity n where n.recipientId = :recipientId ")
    List<NoteEntity> findAllByRecipientId(String recipientId);

    @Query(" select n from NoteEntity n where n.deliveryId = :deliveryId ")
    List<NoteEntity> findAllByDeliveryId(String deliveryId);

    @Query(" select n from NoteEntity n where n.deliveryAttemptId = :deliveryAttemptId ")
    List<NoteEntity> findAllByDeliveryAttemptId(String deliveryAttemptId);

    @Query(" select n from NoteEntity n where n.importId = :importId ")
    List<NoteEntity> findAllByImportId(ConstantDesc importId);

    @Query(" select n from NoteEntity n where n.warehouseItemId = :warehouseItemId ")
    List<NoteEntity> findAllByWarehouseItemId(String warehouseItemId);

}
