package tech.nmhillusion.slight_transportation.domains.commodity.commodityImport;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.slight_transportation.entity.business.CommodityImportEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-14
 */

@RestController
@RequestMapping("/api/commodity-import")
public class CommodityImportController {
    private final CommodityImportService service;

    public CommodityImportController(CommodityImportService service) {
        this.service = service;
    }

    @PostMapping(value = "/sync", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommodityImportEntity sync(@RequestBody CommodityImportEntity dto) {
        return service.sync(dto);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CommodityImportEntity> search(@RequestBody Map<String, ?> dto, @RequestParam int pageIndex, @RequestParam int pageSize) {
        return service.search(dto, pageIndex, pageSize);
    }

    @GetMapping(value = "/{commodityImportId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommodityImportEntity findById(@PathVariable long commodityImportId) {
        return service.findById(commodityImportId);
    }

    @DeleteMapping(value = "/{commodityImportId}")
    public void deleteById(@PathVariable long commodityImportId) {
        service.deleteById(commodityImportId);
    }
}
