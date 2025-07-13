package tech.nmhillusion.corgi_gift_delivery.domains.delivery;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<DeliveryEntity> search(@RequestBody DeliveryDto deliveryDto,
                                       @RequestParam(defaultValue = "0") int pageIndex,
                                       @RequestParam(defaultValue = "10") int pageSize) {
        return deliveryService.search(
                deliveryDto
                , pageIndex
                , pageSize
        );
    }

    @PostMapping(value = "/insert/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeliveryEntity> insertBatchByExcelFile(@RequestBody MultipartFile excelFile) {
        return deliveryService.insertBatchByExcelFile(excelFile);
    }

    @PostMapping(value = "/update/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeliveryEntity> updateBatchByExcelFile(@RequestBody MultipartFile excelFile) {
        return deliveryService.updateBatchByExcelFile(excelFile);
    }
}
