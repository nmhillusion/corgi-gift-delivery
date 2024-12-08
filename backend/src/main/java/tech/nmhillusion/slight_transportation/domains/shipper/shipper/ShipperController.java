package tech.nmhillusion.slight_transportation.domains.shipper.shipper;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.slight_transportation.entity.business.ShipperEntity;

import java.util.List;
import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-30
 */

@RestController
@RequestMapping("/api/shipper")
public class ShipperController {
    private final ShipperService shipperService;

    public ShipperController(ShipperService shipperService) {
        this.shipperService = shipperService;
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ShipperEntity> search(@RequestBody Map<String, ?> dto, @RequestParam int pageIndex, @RequestParam int pageSize) {
        return shipperService.search(dto, pageIndex, pageSize);
    }

    @GetMapping(value = "/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShipperEntity> findAll() {
        return shipperService.findAll();
    }

    @GetMapping(value = "/{shipperId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShipperEntity findById(@PathVariable String shipperId) {
        return shipperService.findById(shipperId);
    }

    @PostMapping(value = "/sync", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShipperEntity sync(@RequestBody ShipperEntity dto) {
        return shipperService.sync(dto);
    }

    @DeleteMapping(value = "/{shipperId}")
    public void deleteById(@PathVariable String shipperId) {
        shipperService.deleteById(shipperId);
    }
}
