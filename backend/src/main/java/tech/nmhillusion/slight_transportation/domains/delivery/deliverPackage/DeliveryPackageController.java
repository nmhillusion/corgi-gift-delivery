package tech.nmhillusion.slight_transportation.domains.delivery.deliverPackage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryPackageEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
@RestController
@RequestMapping("/api/delivery-package")
public class DeliveryPackageController {

    private final DeliveryPackageService service;

    public DeliveryPackageController(DeliveryPackageService service) {
        this.service = service;
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<DeliveryPackageEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
        return service.search(dto, PageRequest.of(pageIndex, pageSize));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryPackageEntity save(@RequestBody DeliveryPackageEntity entity) {
        return service.save(entity);
    }

    @DeleteMapping(value = "/delete/{packageId}")
    public void deleteById(@PathVariable long packageId) {
        service.deleteById(packageId);
    }

    @GetMapping(value = "/{packageId}")
    public DeliveryPackageEntity findById(@PathVariable long packageId) {
        return service.findById(packageId);
    }

}
