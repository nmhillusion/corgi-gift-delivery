package tech.nmhillusion.slight_transportation.domains.commodity.commodityType;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommodityTypeEntity> findAll() {
        return commodityTypeService.findAll();
    }

    @PostMapping(value = "/sync", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommodityTypeEntity sync(@RequestBody Map<String, ?> dto) {
        return commodityTypeService.sync(dto);
    }
}
