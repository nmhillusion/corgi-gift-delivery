package tech.nmhillusion.slight_transportation.domains.warehouse.warehouseItem;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.slight_transportation.entity.business.WarehouseItemEntity;

import java.util.List;
import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-15
 */

@RestController
@RequestMapping("/api/warehouse-item")
public class WarehouseItemController {
    private final WarehouseItemService service;

    public WarehouseItemController(WarehouseItemService service) {
        this.service = service;
    }

    @PostMapping(value = "/sync", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WarehouseItemEntity sync(@RequestBody WarehouseItemEntity dto) {
        return service.sync(dto);
    }

    @DeleteMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteById(@PathVariable String itemId) {
        service.deleteById(itemId);
    }

    @GetMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WarehouseItemEntity findById(@PathVariable String itemId) {
        return service.findById(itemId);
    }


    @PostMapping(value = "/search-in-warehouse/{warehouseId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<WarehouseItemEntity> searchItemsInWarehouse(@PathVariable String warehouseId, @RequestBody Map<String, ?> dto, @RequestParam int pageIndex, @RequestParam int pageSize) {
        return service.searchItemsInWarehouse(warehouseId, dto, pageIndex, pageSize);
    }

    @PostMapping(value = "/search-in-import/{importId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<WarehouseItemEntity> searchItemsInImport(@PathVariable String importId, @RequestBody Map<String, ?> dto, @RequestParam int pageIndex, @RequestParam int pageSize) {
        return service.searchItemsInImport(importId, dto, pageIndex, pageSize);
    }

    @GetMapping(value = "/available-items-in-warehouse/{warehouseId}/{commodityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WarehouseItemEntity> getAvailableItemsInWarehouse(@PathVariable String warehouseId,
                                                                  @PathVariable String commodityId) {
        return service.getAvailableItemsInWarehouse(warehouseId, commodityId);
    }
}
