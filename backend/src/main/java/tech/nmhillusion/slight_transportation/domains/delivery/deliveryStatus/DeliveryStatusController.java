package tech.nmhillusion.slight_transportation.domains.delivery.deliveryStatus;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryStatusEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-16
 */

@RestController
@RequestMapping("/api/v1/delivery-status")
public class DeliveryStatusController {
    private final DeliveryStatusService deliveryStatusService;

    public DeliveryStatusController(DeliveryStatusService deliveryStatusService) {
        this.deliveryStatusService = deliveryStatusService;
    }

    @GetMapping(value = "/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeliveryStatusEntity> findAll() {
        return deliveryStatusService.findAll();
    }

    @GetMapping(value = "/{statusId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryStatusEntity findById(@PathVariable String statusId) {
        return deliveryStatusService.findById(statusId);
    }
}
