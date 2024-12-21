package tech.nmhillusion.slight_transportation.domains.delivery.deliverPackage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
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

        public Impl(DeliveryPackageRepository repository) {
            this.repository = repository;
        }

        @Override
        public Page<DeliveryPackageEntity> search(Map<String, ?> dto, PageRequest pageRequest) {
            final long deliveryId = Long.parseLong(String.valueOf(dto.get("deliveryId")));
            return repository.search(deliveryId, pageRequest);
        }

        @Override
        public DeliveryPackageEntity save(DeliveryPackageEntity deliveryPackageEntity) {
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
