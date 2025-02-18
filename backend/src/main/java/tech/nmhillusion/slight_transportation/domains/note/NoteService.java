package tech.nmhillusion.slight_transportation.domains.note;

import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.NoteEntity;
import tech.nmhillusion.slight_transportation.validator.IdValidator;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-18
 */
public interface NoteService {

    NoteEntity save(NoteEntity noteEntity);

    NoteEntity findById(String noteId);

    void deleteById(String noteId);

    List<NoteEntity> findAllByRecipientId(String recipientId);

    List<NoteEntity> findAllByDeliveryId(String deliveryId);

    List<NoteEntity> findAllByDeliveryAttemptId(String deliveryAttemptId);

    List<NoteEntity> findAllByImportId(String importId);

    List<NoteEntity> findAllByWarehouseItemId(String warehouseItemId);

    @TransactionalService
    class Impl implements NoteService {
        private final NoteRepository repository;
        private final SequenceService sequenceService;

        public Impl(NoteRepository repository, SequenceService sequenceService) {
            this.repository = repository;
            this.sequenceService = sequenceService;
        }

        @Override
        public NoteEntity save(NoteEntity noteEntity) {
            if (IdValidator.isNotSetId(noteEntity.getNoteId())) {
                noteEntity
                        .setNoteId(
                                sequenceService.nextValueInString(
                                        sequenceService.generateSeqNameForClass(
                                                getClass()
                                                , NoteEntity.ID.NOTE_ID.name()
                                        )
                                )
                        )
                        .setNoteTime(
                                ZonedDateTime.now()
                        );
            }

            return repository.save(noteEntity);
        }

        @Override
        public NoteEntity findById(String noteId) {
            return repository.findById(noteId).orElse(null);
        }

        @Override
        public void deleteById(String noteId) {
            repository.deleteById(noteId);
        }

        @Override
        public List<NoteEntity> findAllByRecipientId(String recipientId) {
            return repository.findAllByRecipientId(recipientId);
        }

        @Override
        public List<NoteEntity> findAllByDeliveryId(String deliveryId) {
            return repository.findAllByDeliveryId(deliveryId);
        }

        @Override
        public List<NoteEntity> findAllByDeliveryAttemptId(String deliveryAttemptId) {
            return repository.findAllByDeliveryAttemptId(deliveryAttemptId);
        }

        @Override
        public List<NoteEntity> findAllByImportId(String importId) {
            return repository.findAllByImportId(importId);
        }

        @Override
        public List<NoteEntity> findAllByWarehouseItemId(String warehouseItemId) {
            return repository.findAllByWarehouseItemId(warehouseItemId);
        }
    }

}
