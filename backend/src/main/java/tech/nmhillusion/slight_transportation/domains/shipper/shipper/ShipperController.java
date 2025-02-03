package tech.nmhillusion.slight_transportation.domains.shipper.shipper;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.slight_transportation.entity.business.ShipperEntity;

import java.util.List;
import java.util.Map;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-01-26
 */
@RestController
@RequestMapping("/api/shipper")
public class ShipperController {

    private final ShipperService service;

    public ShipperController(ShipperService service) {
        this.service = service;
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ShipperEntity> search(@RequestBody Map<String, ?> dto,
                                      @RequestParam int pageIndex,
                                      @RequestParam int pageSize) {
        return service.search(dto, pageIndex, pageSize);
    }

    @GetMapping(value = "/{shipperId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShipperEntity findById(@PathVariable int shipperId) {
        return service.findById(shipperId);
    }

    @DeleteMapping(value = "/{shipperId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteById(@PathVariable int shipperId) {
        service.deleteById(shipperId);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ShipperEntity save(@RequestBody ShipperEntity shipperEntity) {
        return service.save(shipperEntity);
    }

    @PostMapping(value = "/import/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShipperEntity> importExcelFile(@RequestPart MultipartFile excelFile) {
        return service.importExcelFile(excelFile);
    }
}
