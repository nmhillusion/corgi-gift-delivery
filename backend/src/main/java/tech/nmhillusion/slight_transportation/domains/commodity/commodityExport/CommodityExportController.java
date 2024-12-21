package tech.nmhillusion.slight_transportation.domains.commodity.commodityExport;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.slight_transportation.entity.business.CommodityExportEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
@RestController
@RequestMapping("/api/commodity-export")
public class CommodityExportController {

    private final CommodityExportService commodityExportService;

    public CommodityExportController(CommodityExportService commodityExportService) {
        this.commodityExportService = commodityExportService;
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CommodityExportEntity> search(@RequestBody Map<String, ?> dto,
                                              @RequestParam int pageIndex,
                                              @RequestParam int pageSize) {
        return commodityExportService.search(dto, pageIndex, pageSize);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommodityExportEntity save(@RequestBody CommodityExportEntity commodityExportEntity) {
        return commodityExportService.save(commodityExportEntity);
    }

    @DeleteMapping("/delete/{exportId}")
    public void deleteById(@PathVariable long exportId) {
        commodityExportService.deleteById(exportId);
    }

    @GetMapping("/{exportId}")
    public CommodityExportEntity findById(@PathVariable long exportId) {
        return commodityExportService.findById(exportId);
    }
}
