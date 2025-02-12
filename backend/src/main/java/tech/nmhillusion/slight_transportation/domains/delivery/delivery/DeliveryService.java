package tech.nmhillusion.slight_transportation.domains.delivery.delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.constant.DeliveryStatus;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryEntity;
import tech.nmhillusion.slight_transportation.validator.IdValidator;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
public interface DeliveryService {
    Page<DeliveryEntity> search(Map<String, ?> dto, int pageIndex, int pageSize);

    DeliveryEntity findById(long deliveryId);

    DeliveryEntity save(DeliveryEntity deliveryEntity);

    @TransactionalService
    class Impl implements DeliveryService {
        private final DeliveryRepository repository;
        private final SequenceService sequenceService;

        public Impl(DeliveryRepository repository, SequenceService sequenceService) {
            this.repository = repository;
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

            LogHelper.getLogger(this).info("deliveryEntity: {}", deliveryEntity);

            return repository.save(deliveryEntity);
        }
    }
}
