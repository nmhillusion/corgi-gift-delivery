package tech.nmhillusion.slight_transportation.domains.warehouse;

import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.entity.business.WarehouseEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-08
 */
public interface WarehouseService {
    List<WarehouseEntity> findAll();

    WarehouseEntity sync(WarehouseEntity warehouseEntity);

    WarehouseEntity findById(String warehouseId);

    @TransactionalService
    class Impl implements WarehouseService {
        private final WarehouseRepository repository;

        public Impl(WarehouseRepository repository) {
            this.repository = repository;
        }

        @Override
        public List<WarehouseEntity> findAll() {
            return repository.findAll();
        }

        @Override
        public WarehouseEntity sync(WarehouseEntity warehouseEntity) {
            final WarehouseEntity savedEntity = repository.save(warehouseEntity);
            LogHelper.getLogger(this).info("savedEntity: {}", savedEntity);
            return savedEntity;
        }

        @Override
        public WarehouseEntity findById(String warehouseId) {
            return repository.findById(warehouseId).orElse(null);
        }
    }
}
