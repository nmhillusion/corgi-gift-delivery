package tech.nmhillusion.slight_transportation.domains.delivery.deliveryStatus;

import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryStatusEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-16
 */
public interface DeliveryStatusService {
    List<DeliveryStatusEntity> findAll();

    DeliveryStatusEntity findById(String statusId);

    @TransactionalService
    class Impl implements DeliveryStatusService {

        private final DeliveryStatusRepository deliveryStatusRepository;

        public Impl(DeliveryStatusRepository deliveryStatusRepository) {
            this.deliveryStatusRepository = deliveryStatusRepository;
        }

        @Override
        public List<DeliveryStatusEntity> findAll() {
            return deliveryStatusRepository.findAll();
        }

        @Override
        public DeliveryStatusEntity findById(String statusId) {
            return deliveryStatusRepository
                    .findById(statusId)
                    .orElse(null);
        }
    }
}
