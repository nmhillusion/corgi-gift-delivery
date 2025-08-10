package tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt;

import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.nmhillusion.corgi_gift_delivery.domains.base.BaseDeliveryController;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryAttemptEntity;
import tech.nmhillusion.n2mix.exception.NotFoundException;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */

@RestController
@RequestMapping("/api/delivery-attempt")
public class DeliveryAttemptController extends BaseDeliveryController<DeliveryAttemptEntity, DeliveryAttemptDto> {
    private final DeliveryAttemptService deliveryAttemptService;

    public DeliveryAttemptController(DeliveryAttemptService service) {
        super(service);
        this.deliveryAttemptService = service;
    }

    @GetMapping(value = "/{deliveryId}/latest-attempt", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryAttemptEntity getLatestAttemptByDeliveryId(@PathVariable @NotBlank String deliveryId) {
        return deliveryAttemptService.getLatestAttemptByDeliveryId(deliveryId);
    }

}
