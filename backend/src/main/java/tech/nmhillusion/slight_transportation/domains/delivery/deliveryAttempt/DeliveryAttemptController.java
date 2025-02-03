package tech.nmhillusion.slight_transportation.domains.delivery.deliveryAttempt;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryAttemptEntity;

import java.util.List;
import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-03
 */

@RestController
@RequestMapping("/api/delivery-attempt")
public class DeliveryAttemptController {
    private final DeliveryAttemptService deliveryAttemptService;

    public DeliveryAttemptController(DeliveryAttemptService deliveryAttemptService) {
        this.deliveryAttemptService = deliveryAttemptService;
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<DeliveryAttemptEntity> search(@RequestBody Map<String, ?> dto,
                                              @RequestParam int pageIndex,
                                              @RequestParam int pageSize) {
        return deliveryAttemptService.search(dto, pageIndex, pageSize);
    }

    @GetMapping(value = "/{attemptId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryAttemptEntity findById(@PathVariable long attemptId) {
        return deliveryAttemptService.findById(attemptId);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryAttemptEntity save(@RequestBody DeliveryAttemptEntity deliveryAttemptEntity) {
        return deliveryAttemptService.save(deliveryAttemptEntity);
    }

    @DeleteMapping(value = "/{attemptId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteById(@PathVariable long attemptId) {
        deliveryAttemptService.deleteById(attemptId);
    }
}
