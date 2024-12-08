package tech.nmhillusion.slight_transportation.domains.shipper.shipperType;

import org.springframework.stereotype.Service;
import tech.nmhillusion.slight_transportation.entity.business.ShipperTypeEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-30
 */
@Service
public interface ShipperTypeService {
    List<ShipperTypeEntity> findAll();

    ShipperTypeEntity findById(String shipperTypeId);

    @Service
    class Impl implements ShipperTypeService {
        private final ShipperTypeRepository repository;

        public Impl(ShipperTypeRepository repository) {
            this.repository = repository;
        }


        @Override
        public List<ShipperTypeEntity> findAll() {
            return repository.findAll();
        }

        @Override
        public ShipperTypeEntity findById(String shipperTypeId) {
            return repository.findById(shipperTypeId).orElse(null);
        }
    }
}
