package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturn;

import org.springframework.web.bind.annotation.RequestMapping;
import tech.nmhillusion.corgi_gift_delivery.domains.base.BaseDeliveryController;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnEntity;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */

@RequestMapping("/api/delivery-return")
public class DeliveryReturnController extends BaseDeliveryController<DeliveryReturnEntity, DeliveryReturnDto> {
    public DeliveryReturnController(DeliveryReturnService service) {
        super(service);
    }
}
