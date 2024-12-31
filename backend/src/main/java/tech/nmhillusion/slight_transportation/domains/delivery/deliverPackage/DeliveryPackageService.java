package tech.nmhillusion.slight_transportation.domains.delivery.deliverPackage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.constant.IdConstant;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryPackageEntity;

import java.util.Map;

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

        private long generateId(DeliveryPackageEntity dto) {
            if (IdConstant.MIN_ID > dto.getPackageId()) {
                return sequenceService.nextValue(
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
    }
}
