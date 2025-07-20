package tech.nmhillusion.corgi_gift_delivery.domains.delivery;

import org.springframework.web.bind.annotation.RequestMapping;
import tech.nmhillusion.corgi_gift_delivery.domains.base.BaseDeliveryController;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */

@RequestMapping("/api/delivery")
public class DeliveryController extends BaseDeliveryController<DeliveryEntity, DeliveryDto> {

    public DeliveryController(DeliveryService deliveryService) {
        super(deliveryService);
    }
}
