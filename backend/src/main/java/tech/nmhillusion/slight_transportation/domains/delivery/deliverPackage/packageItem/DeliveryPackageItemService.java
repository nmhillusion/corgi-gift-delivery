package tech.nmhillusion.slight_transportation.domains.delivery.deliverPackage.packageItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryPackageItemEntity;
import tech.nmhillusion.slight_transportation.validator.IdValidator;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
public interface DeliveryPackageItemService {

    Page<DeliveryPackageItemEntity> search(Map<String, ?> dto, int pageIndex, int pageSize);

    void deleteById(String packageItemId);

    DeliveryPackageItemEntity findById(String packageItemId);

    DeliveryPackageItemEntity save(DeliveryPackageItemEntity entity);

    List<DeliveryPackageItemEntity> getAllItemsOfPackage(String packageId);

    @TransactionalService
    class Impl implements DeliveryPackageItemService {

        private final DeliveryPackageItemRepository repository;
        private final SequenceService sequenceService;

        public Impl(DeliveryPackageItemRepository repository, SequenceService sequenceService) {
            this.repository = repository;
            this.sequenceService = sequenceService;
        }

        @Override
        public Page<DeliveryPackageItemEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
            final String packageId = StringUtil.trimWithNull(dto.get("packageId"));
            return repository.search(packageId, PageRequest.of(pageIndex, pageSize));
        }

        @Override
        public void deleteById(String packageItemId) {
            repository.deleteById(packageItemId);
        }

        @Override
        public DeliveryPackageItemEntity findById(String packageItemId) {
            return repository.findById(packageItemId).orElse(null);
        }

        private String generateId(DeliveryPackageItemEntity dto) {
            if (IdValidator.isNotSetId(dto.getItemId())) {
                return sequenceService.nextValueInString(
                        sequenceService.generateSeqNameForClass(
                                getClass()
                                , DeliveryPackageItemEntity.ID.ITEM_ID.name()
                        )
                );
            }
            return dto.getItemId();
        }

        @Override
        public DeliveryPackageItemEntity save(DeliveryPackageItemEntity entity) {
            if (IdValidator.isNotSetId(entity.getItemId())) {
                entity
                        .setItemId(generateId(entity))
                        .setCreateTime(ZonedDateTime.now());
            }

            LogHelper.getLogger(this).info("entity: {}", entity);

            return repository.save(entity);
        }

        @Override
        public List<DeliveryPackageItemEntity> getAllItemsOfPackage(String packageId) {
            return repository.getAllItemsOfPackage(packageId);
        }
    }

}
