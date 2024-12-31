package tech.nmhillusion.slight_transportation.domains.delivery.deliverPackage.packageItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryPackageItemEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */

@RestController
@RequestMapping("/api/delivery-package/item")
public class DeliveryPackageItemController {
    private final DeliveryPackageItemService service;

    public DeliveryPackageItemController(DeliveryPackageItemService service) {
        this.service = service;
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<DeliveryPackageItemEntity> search(@RequestBody Map<String, ?> dto,
                                                  @RequestParam int pageIndex,
                                                  @RequestParam int pageSize) {
        return service.search(dto, PageRequest.of(pageIndex, pageSize));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryPackageItemEntity save(@RequestBody DeliveryPackageItemEntity deliveryPackageItemEntity) {
        return service.save(deliveryPackageItemEntity);
    }

    @DeleteMapping("/delete/{packageItemId}")
    public void deleteById(@PathVariable long packageItemId) {
        service.deleteById(packageItemId);
    }

    @GetMapping("/{packageItemId}")
    public DeliveryPackageItemEntity findById(@PathVariable long packageItemId) {
        return service.findById(packageItemId);
    }
}
