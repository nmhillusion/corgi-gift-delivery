package tech.nmhillusion.slight_transportation.domains.delivery.deliveryAttempt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.constant.DeliveryStatus;
import tech.nmhillusion.slight_transportation.domains.delivery.delivery.DeliveryService;
import tech.nmhillusion.slight_transportation.domains.delivery.deliveryStatus.DeliveryStatusService;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryAttemptEntity;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryEntity;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryStatusEntity;
import tech.nmhillusion.slight_transportation.validator.IdValidator;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-03
 */
public interface DeliveryAttemptService {

    DeliveryAttemptEntity save(DeliveryAttemptEntity deliveryAttemptEntity);

    DeliveryAttemptEntity findById(String attemptId);

    void deleteById(String attemptId);

    Page<DeliveryAttemptEntity> search(String deliveryId, Map<String, ?> dto, int pageIndex, int pageSize);

    DeliveryAttemptEntity process(String attemptId, ProcessAttemptDto processAttemptDto);

    List<DeliveryStatusEntity> getAvailableStatusForProcess(String attemptId);

    @TransactionalService
    class Impl implements DeliveryAttemptService {
        private final DeliveryAttemptRepository repository;
        private final DeliveryService deliveryService;
        private final DeliveryStatusService deliveryStatusService;
        private final SequenceService sequenceService;

        public Impl(DeliveryAttemptRepository repository, DeliveryService deliveryService, DeliveryStatusService deliveryStatusService, SequenceService sequenceService) {
            this.repository = repository;
            this.deliveryService = deliveryService;
            this.deliveryStatusService = deliveryStatusService;
            this.sequenceService = sequenceService;
        }


        @Override
        public DeliveryAttemptEntity save(DeliveryAttemptEntity deliveryAttemptEntity) {
            if (IdValidator.isNotSetId(deliveryAttemptEntity.getAttemptId())) {
                deliveryAttemptEntity.setAttemptId(
                                sequenceService.nextValueInString(
                                        sequenceService.generateSeqNameForClass(
                                                getClass()
                                                , DeliveryAttemptEntity.ID.ATTEMPT_ID.name()
                                        )
                                )
                        )
                        .setDeliveryStatusId(
                                DeliveryStatus.CREATED.getDbValue()
                        )
                        .setStartTime(
                                ZonedDateTime.now()
                        );
            }

            return repository.save(deliveryAttemptEntity);
        }

        @Override
        public DeliveryAttemptEntity findById(String attemptId) {
            return repository.findById(attemptId).orElse(null);
        }

        @Override
        public void deleteById(String attemptId) {
            repository.deleteById(attemptId);
        }

        @Override
        public Page<DeliveryAttemptEntity> search(String deliveryId, Map<String, ?> dto, int pageIndex, int pageSize) {
            return repository.search(
                    deliveryId,
                    PageRequest.of(pageIndex, pageSize)
            );
        }

        @Override
        public DeliveryAttemptEntity process(String attemptId, ProcessAttemptDto processAttemptDto) {
            final DeliveryAttemptEntity deliveryAttemptEntity = findById(attemptId);
            deliveryAttemptEntity.setDeliveryStatusId(processAttemptDto.getDeliveryStatusId());

            final DeliveryEntity deliveryEntity = deliveryService.findById(Long.parseLong(deliveryAttemptEntity.getDeliveryId()));

            switch (DeliveryStatus.fromDbValue(processAttemptDto.getDeliveryStatusId())) {
                case DeliveryStatus.IN_TRANSIT:
                    deliveryEntity.setCurrentAttemptId(attemptId);
                    deliveryAttemptEntity.setStartTime(processAttemptDto.getActionDate());
                    break;
                case DeliveryStatus.FAILED:
                    deliveryEntity.setCurrentAttemptId(attemptId);
                    deliveryAttemptEntity.setEndTime(processAttemptDto.getActionDate());
                    break;
                case DeliveryStatus.DELIVERED:
                    deliveryEntity
                            .setCurrentAttemptId(attemptId)
                            .setDeliveryStatusId(DeliveryStatus.DELIVERED.getDbValue())
                            .setEndTime(processAttemptDto.getActionDate());
                    deliveryAttemptEntity.setEndTime(processAttemptDto.getActionDate());
                    break;
                case null:
                default:
                    break;
            }

            deliveryService.save(deliveryEntity);
            return save(deliveryAttemptEntity);
        }

        @Override
        public List<DeliveryStatusEntity> getAvailableStatusForProcess(String attemptId) {
            final DeliveryAttemptEntity entity_ = findById(attemptId);
            final DeliveryStatus deliveryStatus = DeliveryStatus.fromDbValue(entity_.getDeliveryStatusId());

            final List<DeliveryStatus> availableStatus = switch (deliveryStatus) {
                case CREATED -> List.of(
                        DeliveryStatus.IN_TRANSIT
                );
                case IN_TRANSIT -> List.of(
                        DeliveryStatus.DELIVERED,
                        DeliveryStatus.FAILED
                );
                case null, default -> List.of();
            };

            return availableStatus
                    .stream()
                    .map(sts -> deliveryStatusService.findById(sts.getDbValue()))
                    .toList();
        }
    }
}
