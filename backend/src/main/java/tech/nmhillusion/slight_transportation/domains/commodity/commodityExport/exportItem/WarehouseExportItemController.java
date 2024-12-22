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

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<WarehouseExportItemEntity> search(@RequestBody Map<String, ?> dto,
                                                  @RequestParam int pageIndex,
                                                  @RequestParam int pageSize) {
        return service.search(dto, pageIndex, pageSize);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WarehouseExportItemEntity save(@RequestBody WarehouseExportItemEntity warehouseExportItemEntity) {
        return service.save(warehouseExportItemEntity);
    }

    @DeleteMapping("/delete/{itemId}")
    public void deleteById(@PathVariable String itemId) {
        service.deleteById(itemId);
    }

    @GetMapping("/{itemId}")
    public WarehouseExportItemEntity findById(@PathVariable String itemId) {
        return service.findById(itemId);
    }
}
