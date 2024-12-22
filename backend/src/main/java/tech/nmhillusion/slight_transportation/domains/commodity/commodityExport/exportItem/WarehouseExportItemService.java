package tech.nmhillusion.slight_transportation.domains.commodity.commodityExport.exportItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.entity.business.WarehouseExportItemEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
public interface WarehouseExportItemService {
    Page<WarehouseExportItemEntity> search(Map<String, ?> dto, int pageIndex, int pageSize);

    WarehouseExportItemEntity save(WarehouseExportItemEntity warehouseExportItemEntity);

    void deleteById(String itemId);

    WarehouseExportItemEntity findById(String itemId);

    @TransactionalService
    class Impl implements WarehouseExportItemService {
        private final WarehouseExportItemRepository repository;

        public Impl(WarehouseExportItemRepository repository) {
            this.repository = repository;
        }

        @Override
        public Page<WarehouseExportItemEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
            long exportId = Long.parseLong(String.valueOf(dto.get("exportId")));

            return repository.search(exportId, PageRequest.of(pageIndex, pageSize));
        }

        @Override
        public WarehouseExportItemEntity save(WarehouseExportItemEntity warehouseExportItemEntity) {
            return repository.save(warehouseExportItemEntity);
        }

        @Override
        public void deleteById(String itemId) {
            repository.deleteById(itemId);
        }

        @Override
        public WarehouseExportItemEntity findById(String itemId) {
            return repository.findById(itemId).orElse(null);
        }
    }
}
