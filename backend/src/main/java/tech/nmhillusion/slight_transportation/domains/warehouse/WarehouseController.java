package tech.nmhillusion.slight_transportation.domains.warehouse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WarehouseEntity> findAll() {
        return service.findAll();
    }

    @PostMapping(value = "/sync", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WarehouseEntity sync(@RequestBody WarehouseEntity warehouseEntity) {
        return service.sync(warehouseEntity);
    }
}
