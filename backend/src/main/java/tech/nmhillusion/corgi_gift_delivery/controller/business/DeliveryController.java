package tech.nmhillusion.corgi_gift_delivery.controller.business;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;
import tech.nmhillusion.corgi_gift_delivery.service.business.DeliveryService;

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

    @PostMapping(value = "/save/batch", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DeliveryEntity> saveBatchByExcelFile(@RequestBody MultipartFile excelFile) {
        return deliveryService.saveBatchByExcelFile(excelFile);
    }
}
