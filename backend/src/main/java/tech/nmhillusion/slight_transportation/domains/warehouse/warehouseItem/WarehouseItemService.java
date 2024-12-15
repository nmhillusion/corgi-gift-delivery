package tech.nmhillusion.slight_transportation.domains.warehouse.warehouseItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.entity.business.WarehouseItemEntity;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-15
 */
public interface WarehouseItemService {
    Page<WarehouseItemEntity> searchItemsInWarehouse(int warehouseId, Map<String, ?> dto, int pageIndex, int pageSize);

    Page<WarehouseItemEntity> searchItemsInImport(int importId, Map<String, ?> dto, int pageIndex, int pageSize);

    WarehouseItemEntity sync(WarehouseItemEntity dto);

    void deleteById(String itemId);

    @TransactionalService
    class Impl implements WarehouseItemService {
        private final WarehouseItemRepository repository;

        public Impl(WarehouseItemRepository repository) {
            this.repository = repository;
        }

        @Override
        public Page<WarehouseItemEntity> searchItemsInWarehouse(int warehouseId, Map<String, ?> dto, int pageIndex, int pageSize) {
            final ZonedDateTime createTimeFrom = ZonedDateTime.parse(
                    StringUtil.trimWithNull(dto.get("createTimeFrom"))
            );
            final ZonedDateTime createTimeTo = ZonedDateTime.parse(
                    StringUtil.trimWithNull(dto.get("createTimeTo"))
            );

            return repository.searchItemsInWarehouse(
                    warehouseId
                    , createTimeFrom
                    , createTimeTo
                    , PageRequest.of(pageIndex, pageSize)
            );
        }

        @Override
        public Page<WarehouseItemEntity> searchItemsInImport(int importId, Map<String, ?> dto, int pageIndex, int pageSize) {
            final ZonedDateTime createTimeFrom = ZonedDateTime.parse(
                    StringUtil.trimWithNull(dto.get("createTimeFrom"))
            );
            final ZonedDateTime createTimeTo = ZonedDateTime.parse(
                    StringUtil.trimWithNull(dto.get("createTimeTo"))
            );

            return repository.searchItemsInImport(
                    importId
                    , createTimeFrom
                    , createTimeTo
                    , PageRequest.of(pageIndex, pageSize));
        }

        @Override
        public WarehouseItemEntity sync(WarehouseItemEntity dto) {
            return repository.save(dto);
        }

        @Override
        public void deleteById(String itemId) {
            repository.deleteById(itemId);
        }
    }
}
