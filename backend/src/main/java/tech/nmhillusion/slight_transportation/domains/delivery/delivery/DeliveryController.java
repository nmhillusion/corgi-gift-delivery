package tech.nmhillusion.slight_transportation.domains.delivery.delivery;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-31
 */

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    private final DeliveryService service;

    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @GetMapping(value = "/{deliveryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    DeliveryEntity findById(@PathVariable long deliveryId) {
        return service.findById(deliveryId);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Page<DeliveryEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
        return service.search(dto, pageIndex, pageSize);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryEntity save(@RequestBody DeliveryEntity entity) {
        return service.save(entity);
    }
}
