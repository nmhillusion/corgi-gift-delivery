package tech.nmhillusion.slight_transportation.domains.delivery.deliveryType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryTypeEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-03
 */

@RestController
@RequestMapping("/api/delivery-type")
public class DeliveryTypeController {
    private final DeliveryTypeService deliveryTypeService;

    public DeliveryTypeController(DeliveryTypeService deliveryTypeService) {
        this.deliveryTypeService = deliveryTypeService;
    }

    @GetMapping("/find-all")
    public List<DeliveryTypeEntity> findAll() {
        return deliveryTypeService.findAll();
    }

    @GetMapping("/{typeId}")
    public DeliveryTypeEntity findById(@PathVariable int typeId) {
        return deliveryTypeService.findById(typeId);
    }
}
