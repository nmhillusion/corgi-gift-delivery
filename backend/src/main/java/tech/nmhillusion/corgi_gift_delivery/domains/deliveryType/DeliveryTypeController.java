package tech.nmhillusion.corgi_gift_delivery.domains.deliveryType;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryTypeEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-08-03
 */

@RestController
@RequestMapping("/api/delivery-type")
public class DeliveryTypeController {
    private final DeliveryTypeService service;

    public DeliveryTypeController(DeliveryTypeService service) {
        this.service = service;
    }

    @GetMapping(value = "/{typeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryTypeEntity getById(@PathVariable String typeId) {
        return service.getDeliveryTypeByTypeId(typeId);
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeliveryTypeEntity> getAll() {
        return service.getAll();
    }

}
