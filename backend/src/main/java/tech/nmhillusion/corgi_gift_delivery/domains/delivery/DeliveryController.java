package tech.nmhillusion.corgi_gift_delivery.domains.delivery;

import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.corgi_gift_delivery.domains.base.BaseDeliveryController;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
@RestController
@RequestMapping("/api/delivery")
public class DeliveryController extends BaseDeliveryController<DeliveryEntity, DeliverySearchDto> {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        super(deliveryService);
        this.deliveryService = deliveryService;
    }

    @GetMapping(value = "/{deliveryId}/customer/{customerId}/name", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getCustomerNameOfDelivery(@PathVariable String deliveryId, @PathVariable String customerId) {
        return deliveryService.getCustomerNameOfDelivery(deliveryId, customerId);
    }

    @GetMapping(value = "/{deliveryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryEntity getById(@PathVariable String deliveryId) {
        return deliveryService.getById(deliveryId);
    }

    @PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource exportDeliveries(@RequestBody @Valid DeliverySearchDto deliveryDto) {
        return deliveryService.exportDeliveries(deliveryDto);
    }
}
