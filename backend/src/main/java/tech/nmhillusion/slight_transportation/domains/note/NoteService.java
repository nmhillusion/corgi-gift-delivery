package tech.nmhillusion.slight_transportation.domains.note;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.NoteEntity;
import tech.nmhillusion.slight_transportation.helper.CollectionHelper;
import tech.nmhillusion.slight_transportation.util.NumberUtil;
import tech.nmhillusion.slight_transportation.validator.IdValidator;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-18
 */
public interface NoteService {

    NoteEntity save(NoteEntity noteEntity);

    NoteEntity findById(String noteId);

    void deleteById(String noteId);

    Page<NoteEntity> search(Map<?, ?> searchDto, int pageIndex, int pageSize);

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
            noteEntity
                    .setDeliveryAttemptId(
                            NumberUtil.parseStringFromDoubleToLong(noteEntity.getDeliveryAttemptId())
                    )
                    .setDeliveryId(
                            NumberUtil.parseStringFromDoubleToLong(noteEntity.getDeliveryId())
                    )
                    .setImportId(
                            NumberUtil.parseStringFromDoubleToLong(noteEntity.getImportId())
                    )
                    .setRecipientId(
                            NumberUtil.parseStringFromDoubleToLong(noteEntity.getRecipientId())
                    )
                    .setWarehouseItemId(
                            NumberUtil.parseStringFromDoubleToLong(noteEntity.getWarehouseItemId())
                    );

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
        public Page<NoteEntity> search(Map<?, ?> searchDto, int pageIndex, int pageSize) {
            final String recipientId = NumberUtil.parseStringFromDoubleToLong(
                    CollectionHelper.getStringOrNullIfAbsent(
                            searchDto,
                            "recipientId"
                    )
            );

            final String deliveryId = CollectionHelper.getStringOrNullIfAbsent(
                    searchDto,
                    "deliveryId"
            );

            final String deliveryAttemptId = CollectionHelper.getStringOrNullIfAbsent(
                    searchDto,
                    "deliveryAttemptId"
            );

            final String importId = CollectionHelper.getStringOrNullIfAbsent(
                    searchDto,
                    "importId"
            );

            final String warehouseItemId = CollectionHelper.getStringOrNullIfAbsent(
                    searchDto,
                    "warehouseItemId"
            );

            return repository.search(
                    recipientId, deliveryId, deliveryAttemptId, importId, warehouseItemId,
                    PageRequest.of(pageIndex, pageSize)
            );
        }
    }

}
