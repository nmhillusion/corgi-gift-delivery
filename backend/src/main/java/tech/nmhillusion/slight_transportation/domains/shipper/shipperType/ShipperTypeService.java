package tech.nmhillusion.slight_transportation.domains.shipper.shipperType;

import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.entity.business.ShipperTypeEntity;

import java.util.List;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-01-26
 */
public interface ShipperTypeService {

    ShipperTypeEntity findById(int typeId);

    List<ShipperTypeEntity> findAll();

    @TransactionalService
    class Impl implements ShipperTypeService {

        private final ShipperTypeRepository repository;

        public Impl(ShipperTypeRepository repository) {
            this.repository = repository;
        }

        @Override
        public ShipperTypeEntity findById(int typeId) {
            return repository.findById(typeId).orElse(null);
        }

        @Override
        public List<ShipperTypeEntity> findAll() {
            return repository.findAll();
        }
    }

}
