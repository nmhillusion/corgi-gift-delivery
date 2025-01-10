package tech.nmhillusion.slight_transportation.domains.commodity.commodityExport.exportItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.constant.IdConstant;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
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

    void deleteById(long itemId);

    WarehouseExportItemEntity findById(long itemId);

    int getExportQuantityOfCommodityWarehouse(int warehouseId, int commodityId);

    @TransactionalService
    class Impl implements WarehouseExportItemService {
        private final WarehouseExportItemRepository repository;
        private final SequenceService sequenceService;

        public Impl(WarehouseExportItemRepository repository, SequenceService sequenceService) {
            this.repository = repository;
            this.sequenceService = sequenceService;
        }

        @Override
        public Page<WarehouseExportItemEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
            long exportId = Long.parseLong(String.valueOf(dto.get("exportId")));

            return repository.search(exportId, PageRequest.of(pageIndex, pageSize));
        }

        private long generateId(WarehouseExportItemEntity dto) {
            if (IdConstant.MIN_ID > dto.getItemId()) {
                return sequenceService.nextValue(
                        sequenceService.generateSeqNameForClass(
                                getClass()
                                , WarehouseExportItemEntity.ID.ITEM_ID.name()
                        )
                );
            }
            return dto.getItemId();
        }

        @Override
        public WarehouseExportItemEntity save(WarehouseExportItemEntity warehouseExportItemEntity) {
            warehouseExportItemEntity.setItemId(generateId(warehouseExportItemEntity));

            LogHelper.getLogger(this).info("warehouseExportItemEntity: {}", warehouseExportItemEntity);

            return repository.save(warehouseExportItemEntity);
        }

        @Override
        public void deleteById(long itemId) {
            repository.deleteById(itemId);
        }

        @Override
        public WarehouseExportItemEntity findById(long itemId) {
            return repository.findById(itemId).orElse(null);
        }

        @Override
        public int getExportQuantityOfCommodityWarehouse(int warehouseId, int commodityId) {
            return repository.getExportQuantityOfCommodityWarehouse(warehouseId, commodityId);
        }
    }
}
