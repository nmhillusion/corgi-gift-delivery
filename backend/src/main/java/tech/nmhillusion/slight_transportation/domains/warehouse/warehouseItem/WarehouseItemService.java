package tech.nmhillusion.slight_transportation.domains.warehouse.warehouseItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.WarehouseItemEntity;
import tech.nmhillusion.slight_transportation.validator.IdValidator;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-15
 */
public interface WarehouseItemService {
    Page<WarehouseItemEntity> searchItemsInWarehouse(String warehouseId, Map<String, ?> dto, int pageIndex, int pageSize);

    Page<WarehouseItemEntity> searchItemsInImport(String importId, Map<String, ?> dto, int pageIndex, int pageSize);

    WarehouseItemEntity sync(WarehouseItemEntity dto);

    void deleteById(String itemId);

    List<WarehouseItemEntity> getAvailableItemsInWarehouse(String warehouseId, String commodityId);

    WarehouseItemEntity findById(String itemId);

    @TransactionalService
    class Impl implements WarehouseItemService {
        private final WarehouseItemRepository repository;
        private final SequenceService sequenceService;

        public Impl(WarehouseItemRepository repository, SequenceService sequenceService) {
            this.repository = repository;
            this.sequenceService = sequenceService;
        }

        @Override
        public Page<WarehouseItemEntity> searchItemsInWarehouse(String warehouseId, Map<String, ?> dto, int pageIndex, int pageSize) {
            final ZonedDateTime createTimeFrom = dto.containsKey("createTimeFrom") ? ZonedDateTime.parse(
                    StringUtil.trimWithNull(dto.get("createTimeFrom"))
            ) : null;
            final ZonedDateTime createTimeTo = dto.containsKey("createTimeTo") ? ZonedDateTime.parse(
                    StringUtil.trimWithNull(dto.get("createTimeTo"))
            ) : null;

            return repository.searchItemsInWarehouse(
                    warehouseId
                    , createTimeFrom
                    , createTimeTo
                    , PageRequest.of(pageIndex, pageSize)
            );
        }

        @Override
        public Page<WarehouseItemEntity> searchItemsInImport(String importId, Map<String, ?> dto, int pageIndex, int pageSize) {
            final ZonedDateTime createTimeFrom = dto.containsKey("createTimeFrom") ? ZonedDateTime.parse(
                    StringUtil.trimWithNull(dto.get("createTimeFrom"))
            ) : null;
            final ZonedDateTime createTimeTo = dto.containsKey("createTimeTo") ? ZonedDateTime.parse(
                    StringUtil.trimWithNull(dto.get("createTimeTo"))
            ) : null;

            return repository.searchItemsInImport(
                    importId
                    , createTimeFrom
                    , createTimeTo
                    , PageRequest.of(pageIndex, pageSize));
        }

        private String generateId(WarehouseItemEntity dto) {
            if (IdValidator.isNotSetId(dto.getItemId())) {
                return sequenceService.nextValueInString(
                        sequenceService.generateSeqNameForClass(
                                getClass()
                                , WarehouseItemEntity.ID.ITEM_ID.name()
                        )
                );
            }

            return dto.getItemId();
        }

        @Override
        public WarehouseItemEntity sync(WarehouseItemEntity dto) {
            dto.setItemId(generateId(dto));

            LogHelper.getLogger(this).info("dto: {}", dto);

            return repository.save(dto);
        }

        @Override
        public void deleteById(String itemId) {
            repository.deleteById(itemId);
        }

        @Override
        public List<WarehouseItemEntity> getAvailableItemsInWarehouse(String warehouseId, String commodityId) {
            return repository.getAvailableItemsInWarehouse(warehouseId, commodityId);
        }

        @Override
        public WarehouseItemEntity findById(String itemId) {
            return repository.findById(itemId).orElse(null);
        }
    }
}
