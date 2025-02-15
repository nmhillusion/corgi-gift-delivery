package tech.nmhillusion.slight_transportation.domains.delivery.deliverPackage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryPackageEntity;
import tech.nmhillusion.slight_transportation.validator.IdValidator;

import java.util.Map;
import java.util.Optional;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
public interface DeliveryPackageService {

    Page<DeliveryPackageEntity> search(Map<String, ?> dto, PageRequest pageRequest);

    DeliveryPackageEntity save(DeliveryPackageEntity deliveryPackageEntity);

    void deleteById(long packageId);

    DeliveryPackageEntity findById(long packageId);

    Optional<DeliveryPackageEntity> getFirstPackageOfDelivery(String deliveryId);

    @TransactionalService
    class Impl implements DeliveryPackageService {

        private final DeliveryPackageRepository repository;
        private final SequenceService sequenceService;

        public Impl(DeliveryPackageRepository repository, SequenceService sequenceService) {
            this.repository = repository;
            this.sequenceService = sequenceService;
        }

        @Override
        public Page<DeliveryPackageEntity> search(Map<String, ?> dto, PageRequest pageRequest) {
            final long deliveryId = Long.parseLong(String.valueOf(dto.get("deliveryId")));
            return repository.search(deliveryId, pageRequest);
        }

        private String generateId(DeliveryPackageEntity dto) {
            if (IdValidator.isNotSetId(dto.getPackageId())) {
                return sequenceService.nextValueInString(
                        sequenceService.generateSeqNameForClass(
                                getClass()
                                , DeliveryPackageEntity.ID.PACKAGE_ID.name()
                        )
                );
            }
            return dto.getPackageId();
        }

        @Override
        public DeliveryPackageEntity save(DeliveryPackageEntity deliveryPackageEntity) {
            deliveryPackageEntity.setPackageId(generateId(deliveryPackageEntity));

            LogHelper.getLogger(this).info("deliveryPackageEntity: {}", deliveryPackageEntity);

            return repository.save(deliveryPackageEntity);
        }

        @Override
        public void deleteById(long packageId) {
            repository.deleteById(packageId);
        }

        @Override
        public DeliveryPackageEntity findById(long packageId) {
            return repository.findById(packageId).orElse(null);
        }

        @Override
        public Optional<DeliveryPackageEntity> getFirstPackageOfDelivery(String deliveryId) {
            return repository.getPackagesOfDelivery(deliveryId)
                    .stream()
                    .findFirst();
        }
    }
}
