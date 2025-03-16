package tech.nmhillusion.slight_transportation.domains.commodity.commodity;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.slight_transportation.entity.business.CommodityEntity;

import java.util.List;
import java.util.Map;

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

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CommodityEntity> search(@RequestBody Map<String, ?> dto,
                                        @RequestParam int pageIndex,
                                        @RequestParam int pageSize) {
        return commodityService.search(dto, pageIndex, pageSize);
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
