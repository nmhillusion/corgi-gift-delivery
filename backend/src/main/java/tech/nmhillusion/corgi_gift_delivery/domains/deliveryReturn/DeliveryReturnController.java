package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturn;

import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.nmhillusion.corgi_gift_delivery.domains.base.BaseDeliveryController;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnEntity;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
@RestController
@RequestMapping("/api/delivery-return")
public class DeliveryReturnController extends BaseDeliveryController<DeliveryReturnEntity, DeliveryReturnSearchDto> {
    private final DeliveryReturnService deliveryReturnService;

    public DeliveryReturnController(DeliveryReturnService service) {
        super(service);
        this.deliveryReturnService = service;
    }

    @GetMapping(value = "/{deliveryId}/latest-return", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryReturnEntity getLatestAttemptByDeliveryId(@PathVariable @NotBlank String deliveryId) {
        return deliveryReturnService.getLatestReturnByDeliveryId(deliveryId);
    }
}
