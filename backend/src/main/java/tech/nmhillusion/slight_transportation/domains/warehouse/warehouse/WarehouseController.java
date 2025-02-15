package tech.nmhillusion.slight_transportation.domains.warehouse.warehouse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.slight_transportation.entity.business.CommodityExportEntity;
import tech.nmhillusion.slight_transportation.entity.business.WarehouseEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-08
 */
@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {
    private final WarehouseService service;

    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @GetMapping(value = "/{warehouseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WarehouseEntity findById(@PathVariable int warehouseId) {
        return service.findById(warehouseId);
    }

    @GetMapping(value = "/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WarehouseEntity> findAll() {
        return service.findAll();
    }

    @PostMapping(value = "/sync", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WarehouseEntity sync(@RequestBody WarehouseEntity warehouseEntity) {
        return service.sync(warehouseEntity);
    }

    @PostMapping(value = "/import/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WarehouseEntity> importExcelFile(@RequestPart MultipartFile excelFile) {
        return service.importExcelFile(excelFile);
    }

    @GetMapping(value = "/{warehouseId}/commodity/{commodityId}/remaining-quantity", produces = MediaType.APPLICATION_JSON_VALUE)
    public Double remainingQuantityOfCommodityOfWarehouse(@PathVariable String warehouseId,
                                                          @PathVariable String commodityId) {
        return service.remainingQuantityOfCommodityOfWarehouse(warehouseId, commodityId);
    }

    @PostMapping(value = "/{warehouseId}/export/delivery/{deliveryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommodityExportEntity requestExportForDelivery(@PathVariable String warehouseId,
                                                          @PathVariable String deliveryId) {
        return service.requestExportForDelivery(warehouseId, deliveryId);
    }
}
