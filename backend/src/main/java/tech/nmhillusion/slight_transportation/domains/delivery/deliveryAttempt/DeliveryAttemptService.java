package tech.nmhillusion.slight_transportation.domains.delivery.deliveryAttempt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.constant.IdConstant;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryAttemptEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-03
 */
public interface DeliveryAttemptService {

    DeliveryAttemptEntity save(DeliveryAttemptEntity deliveryAttemptEntity);

    DeliveryAttemptEntity findById(long attemptId);

    void deleteById(long attemptId);

    Page<DeliveryAttemptEntity> search(long deliveryId, Map<String, ?> dto, int pageIndex, int pageSize);

    @TransactionalService
    class Impl implements DeliveryAttemptService {
        private final DeliveryAttemptRepository repository;
        private final SequenceService sequenceService;

        public Impl(DeliveryAttemptRepository repository, SequenceService sequenceService) {
            this.repository = repository;
            this.sequenceService = sequenceService;
        }


        @Override
        public DeliveryAttemptEntity save(DeliveryAttemptEntity deliveryAttemptEntity) {
            if (IdConstant.MIN_ID > deliveryAttemptEntity.getAttemptId()) {
                deliveryAttemptEntity.setAttemptId(
                        sequenceService.nextValue(
                                sequenceService.generateSeqNameForClass(
                                        getClass()
                                        , DeliveryAttemptEntity.ID.ATTEMPT_ID.name()
                                )
                        )
                );
            }

            return repository.save(deliveryAttemptEntity);
        }

        @Override
        public DeliveryAttemptEntity findById(long attemptId) {
            return repository.findById(attemptId).orElse(null);
        }

        @Override
        public void deleteById(long attemptId) {
            repository.deleteById(attemptId);
        }

        @Override
        public Page<DeliveryAttemptEntity> search(long deliveryId, Map<String, ?> dto, int pageIndex, int pageSize) {
            return repository.search(
                    deliveryId,
                    PageRequest.of(pageIndex, pageSize)
            );
        }
    }

}
