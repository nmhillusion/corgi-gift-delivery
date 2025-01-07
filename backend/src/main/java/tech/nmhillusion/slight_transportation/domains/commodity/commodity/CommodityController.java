package tech.nmhillusion.slight_transportation.domains.commodity.commodity;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.slight_transportation.entity.business.CommodityEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-07
 */
@RestController
@RequestMapping("/api/commodity")
public class CommodityController {

    private final CommodityService commodityService;

    public CommodityController(CommodityService commodityService) {
        this.commodityService = commodityService;
    }

    @GetMapping(value = "/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommodityEntity> findAll() {
        return commodityService.findAll();
    }

    @PostMapping(value = "/sync", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommodityEntity sync(@RequestBody CommodityEntity commodityEntity) {
        return commodityService.sync(commodityEntity);
    }

    @PostMapping(value = "/import/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommodityEntity> importExcelFile(@RequestPart MultipartFile excelFile) {
        return commodityService.importExcelFile(excelFile);
    }

    @GetMapping(value = "/{commodityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommodityEntity findById(@PathVariable long commodityId) {
        return commodityService.findById(commodityId);
    }
}
