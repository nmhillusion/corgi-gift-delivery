package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturnStatus;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnStatusEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-08-03
 */

@RestController
@RequestMapping("/api/delivery-return-status")
public class DeliveryReturnStatusController {
    private final DeliveryReturnStatusService service;

    public DeliveryReturnStatusController(DeliveryReturnStatusService service) {
        this.service = service;
    }

    @GetMapping(value = "/{statusId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryReturnStatusEntity getById(@PathVariable String statusId) {
        return service.getDeliveryReturnStatusByStatusId(statusId);
    }

}
