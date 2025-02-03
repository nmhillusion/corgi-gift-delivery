package tech.nmhillusion.slight_transportation.domains.delivery.deliveryType;

import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryTypeEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-03
 */
public interface DeliveryTypeService {
    List<DeliveryTypeEntity> findAll();

    DeliveryTypeEntity findById(int typeId);

    @TransactionalService
    class Impl implements DeliveryTypeService {
        private final DeliveryTypeRepository repository;

        public Impl(DeliveryTypeRepository repository) {
            this.repository = repository;
        }

        @Override
        public List<DeliveryTypeEntity> findAll() {
            return repository.findAll();
        }

        @Override
        public DeliveryTypeEntity findById(int typeId) {
            return repository.findById(typeId).orElse(null);
        }
    }
}
