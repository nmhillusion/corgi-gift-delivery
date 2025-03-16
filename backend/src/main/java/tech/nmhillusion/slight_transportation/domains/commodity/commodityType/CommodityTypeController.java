package tech.nmhillusion.slight_transportation.domains.commodity.commodityType;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.n2mix.constant.ContentType;
import tech.nmhillusion.slight_transportation.entity.business.CommodityTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-23
 */
@RestController
@RequestMapping("/api/commodity-type")
public class CommodityTypeController {

    private final CommodityTypeService commodityTypeService;

    public CommodityTypeController(CommodityTypeService commodityTypeService) {
        this.commodityTypeService = commodityTypeService;
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CommodityTypeEntity> search(@RequestBody Map<String, ?> dto,
                                            @RequestParam int pageIndex,
                                            @RequestParam int pageSize) {
        return commodityTypeService.search(dto, pageIndex, pageSize);
    }

    @GetMapping(value = "/{commodityTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommodityTypeEntity findById(@PathVariable String commodityTypeId) {
        return commodityTypeService.findById(commodityTypeId);
    }

    @PostMapping(value = "/sync", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommodityTypeEntity sync(@RequestBody Map<String, ?> dto) {
        return commodityTypeService.sync(dto);
    }

    @PostMapping(value = "/import/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommodityTypeEntity> importExcel(@RequestPart MultipartFile excelFile) {
        return commodityTypeService.importExcel(excelFile);
    }

    @GetMapping(value = "/export/excel", produces = ContentType.MS_EXCEL_XLSX)
    public byte[] exportExcel() {
        return commodityTypeService.exportExcel();
    }
}
