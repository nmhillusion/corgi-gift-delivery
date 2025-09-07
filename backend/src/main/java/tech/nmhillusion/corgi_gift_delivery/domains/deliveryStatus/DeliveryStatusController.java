package tech.nmhillusion.corgi_gift_delivery.domains.deliveryStatus;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryStatusEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-08-03
 */

@RestController
@RequestMapping("/api/delivery-status")
public class DeliveryStatusController {
    private final DeliveryStatusService service;

    public DeliveryStatusController(DeliveryStatusService service) {
        this.service = service;
    }

    @GetMapping(value = "/{statusId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryStatusEntity getById(@PathVariable String statusId) {
        return service.getDeliveryStatusByStatusId(statusId);
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeliveryStatusEntity> getAll() {
        return service.getAll();
    }

}
