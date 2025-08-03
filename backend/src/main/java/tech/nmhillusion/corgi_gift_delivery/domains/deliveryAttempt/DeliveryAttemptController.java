package tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.nmhillusion.corgi_gift_delivery.domains.base.BaseDeliveryController;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryAttemptEntity;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */

@RestController
@RequestMapping("/api/delivery-attempt")
public class DeliveryAttemptController extends BaseDeliveryController<DeliveryAttemptEntity, DeliveryAttemptDto> {
    public DeliveryAttemptController(DeliveryAttemptService service) {
        super(service);
    }
}
