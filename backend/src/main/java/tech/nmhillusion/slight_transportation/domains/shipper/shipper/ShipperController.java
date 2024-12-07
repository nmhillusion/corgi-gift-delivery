package tech.nmhillusion.slight_transportation.domains.shipper.shipper;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.slight_transportation.entity.business.ShipperEntity;

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

    @GetMapping(value = "/{shipperId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShipperEntity findById(@PathVariable String shipperId) {
        return shipperService.findById(shipperId);
    }

    @PostMapping(value = "/sync", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShipperEntity sync(@RequestBody Map<String, ?> dto) {
        return shipperService.sync(dto);
    }

    @DeleteMapping(value = "/{shipperId}")
    public void deleteById(@PathVariable String shipperId) {
        shipperService.deleteById(shipperId);
    }
}
