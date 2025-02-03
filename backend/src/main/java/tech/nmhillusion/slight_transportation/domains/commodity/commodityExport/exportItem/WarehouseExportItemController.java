package tech.nmhillusion.slight_transportation.domains.commodity.commodityExport.exportItem;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.slight_transportation.entity.business.WarehouseExportItemEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */

@RestController
@RequestMapping("/api/warehouse-export-item")
public class WarehouseExportItemController {

    private final WarehouseExportItemService service;

    public WarehouseExportItemController(WarehouseExportItemService service) {
        this.service = service;
    }

    @PostMapping(value = "/{exportId}/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<WarehouseExportItemEntity> search(@PathVariable String exportId,
                                                  @RequestBody Map<String, ?> dto,
                                                  @RequestParam int pageIndex,
                                                  @RequestParam int pageSize) {
        return service.search(exportId, dto, pageIndex, pageSize);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WarehouseExportItemEntity save(@RequestBody WarehouseExportItemEntity warehouseExportItemEntity) {
        return service.save(warehouseExportItemEntity);
    }

    @DeleteMapping("/delete/{itemId}")
    public void deleteById(@PathVariable long itemId) {
        service.deleteById(itemId);
    }

    @GetMapping("/{itemId}")
    public WarehouseExportItemEntity findById(@PathVariable long itemId) {
        return service.findById(itemId);
    }

    @GetMapping(path = "/commodity/exported/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer countExportedCommodity(@RequestParam int warehouseId,
                                          @RequestParam int commodityId) {
        return service.getExportQuantityOfCommodityWarehouse(warehouseId, commodityId);
    }

}
