package tech.nmhillusion.slight_transportation.domains.delivery.delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.constant.DeliveryStatus;
import tech.nmhillusion.slight_transportation.domains.delivery.deliverPackage.DeliveryPackageService;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryEntity;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryPackageEntity;
import tech.nmhillusion.slight_transportation.validator.IdValidator;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
public interface DeliveryService {
    Page<DeliveryEntity> search(Map<String, ?> dto, int pageIndex, int pageSize);

    DeliveryEntity findById(long deliveryId);

    DeliveryEntity save(DeliveryEntity deliveryEntity);

    DeliveryPackageEntity packageDelivery(String deliveryId);

    String getCurrentCollectedComQuantity(String deliveryId, String commodityId);

    @TransactionalService
    class Impl implements DeliveryService {
        private final DeliveryRepository repository;
        private final DeliveryPackageService packageService;
        private final SequenceService sequenceService;

        public Impl(DeliveryRepository repository, DeliveryPackageService packageService, SequenceService sequenceService) {
            this.repository = repository;
            this.packageService = packageService;
            this.sequenceService = sequenceService;
        }

        @Override
        public Page<DeliveryEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
            final String recipientId = dto.containsKey("recipientId")
                    ? StringUtil.trimWithNull(dto.get("recipientId"))
                    : null;

            return repository.search(recipientId, PageRequest.of(pageIndex, pageSize));
        }

        @Override
        public DeliveryEntity findById(long deliveryId) {
            return repository.findById(deliveryId).orElse(null);
        }

        @Override
        public DeliveryEntity save(DeliveryEntity deliveryEntity) {
            if (IdValidator.isNotSetId(deliveryEntity.getDeliveryId())) {
                deliveryEntity.setDeliveryId(
                                sequenceService.nextValueInString(
                                        sequenceService.generateSeqNameForClass(
                                                getClass()
                                                , DeliveryEntity.ID.DELIVERY_ID.name()
                                        )
                                )
                        )
                        .setDeliveryStatusId(
                                DeliveryStatus.CREATED.getDbValue()
                        )
                        .setStartTime(
                                ZonedDateTime.now()
                        )
                ;
            }

            getLogger(this).info("deliveryEntity: {}", deliveryEntity);

            return repository.save(deliveryEntity);
        }

        @Override
        public DeliveryPackageEntity packageDelivery(String deliveryId) {
            final Optional<DeliveryPackageEntity> packageOfDelivery = packageService.getFirstPackageOfDelivery(deliveryId);

            if (packageOfDelivery.isPresent()) {
                return packageOfDelivery.get();
            }

            final DeliveryPackageEntity package_ = new DeliveryPackageEntity()
                    .setDeliveryId(deliveryId)
                    .setPackageName("Package for delivery " + deliveryId)
                    .setPackageTime(ZonedDateTime.now());

            return packageService.save(package_);

        }

        @Override
        public String getCurrentCollectedComQuantity(String deliveryId, String commodityId) {
            final DeliveryPackageEntity deliveryPackageEntity = packageDelivery(deliveryId);

            final double currentCollectedComQuantity = repository.getCurrentCollectedComQuantity(deliveryPackageEntity.getPackageId(), commodityId);

            getLogger(this).info(
                    "getCurrentCollectedComQuantity(String deliveryId = {}, String commodityId = {}) = {}"
                    , deliveryId
                    , commodityId
                    , currentCollectedComQuantity
            );

            return String.valueOf(currentCollectedComQuantity);
        }
    }
}
