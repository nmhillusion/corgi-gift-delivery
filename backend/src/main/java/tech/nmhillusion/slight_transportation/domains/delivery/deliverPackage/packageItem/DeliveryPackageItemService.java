package tech.nmhillusion.slight_transportation.domains.delivery.deliverPackage.packageItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryPackageItemEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
public interface DeliveryPackageItemService {

    Page<DeliveryPackageItemEntity> search(Map<String, ?> dto, PageRequest pageRequest);

    void deleteById(String packageItemId);

    DeliveryPackageItemEntity findById(String packageItemId);

    DeliveryPackageItemEntity save(DeliveryPackageItemEntity entity);


    @TransactionalService
    class Impl implements DeliveryPackageItemService {

        private final DeliveryPackageItemRepository repository;

        public Impl(DeliveryPackageItemRepository repository) {
            this.repository = repository;
        }

        @Override
        public Page<DeliveryPackageItemEntity> search(Map<String, ?> dto, PageRequest pageRequest) {
            final long packageId = Long.parseLong(StringUtil.trimWithNull(dto.get("packageItemId")));
            return repository.search(packageId, pageRequest);
        }

        @Override
        public void deleteById(String packageItemId) {
            repository.deleteById(packageItemId);
        }

        @Override
        public DeliveryPackageItemEntity findById(String packageItemId) {
            return repository.findById(packageItemId).orElse(null);
        }

        @Override
        public DeliveryPackageItemEntity save(DeliveryPackageItemEntity entity) {
            return repository.save(entity);
        }
    }

}
