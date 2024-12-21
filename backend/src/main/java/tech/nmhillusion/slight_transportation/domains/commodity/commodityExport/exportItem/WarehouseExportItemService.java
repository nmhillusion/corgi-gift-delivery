package tech.nmhillusion.slight_transportation.domains.commodity.commodityExport.exportItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.entity.business.WarehouseItemExportEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
public interface WarehouseExportItemService {
    Page<WarehouseItemExportEntity> search(Map<String, ?> dto, int pageIndex, int pageSize);

    WarehouseItemExportEntity save(WarehouseItemExportEntity warehouseItemExportEntity);

    void deleteById(String itemId);

    WarehouseItemExportEntity findById(String itemId);

    @TransactionalService
    class Impl implements WarehouseExportItemService {
        private final WarehouseExportItemRepository repository;

        public Impl(WarehouseExportItemRepository repository) {
            this.repository = repository;
        }

        @Override
        public Page<WarehouseItemExportEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
            long exportId = Long.parseLong(String.valueOf(dto.get("exportId")));

            return repository.search(exportId, PageRequest.of(pageIndex, pageSize));
        }

        @Override
        public WarehouseItemExportEntity save(WarehouseItemExportEntity warehouseItemExportEntity) {
            return repository.save(warehouseItemExportEntity);
        }

        @Override
        public void deleteById(String itemId) {
            repository.deleteById(itemId);
        }

        @Override
        public WarehouseItemExportEntity findById(String itemId) {
            return repository.findById(itemId).orElse(null);
        }
    }
}
