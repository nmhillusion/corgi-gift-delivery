package tech.nmhillusion.slight_transportation.domains.recipient.recipient;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.slight_transportation.entity.business.RecipientEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-09
 */

@RestController
@RequestMapping("/api/recipient")
public class RecipientController {

    private final RecipientService service;

    public RecipientController(RecipientService service) {
        this.service = service;
    }

    @PostMapping(value = "/sync", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public RecipientEntity sync(@RequestBody RecipientEntity recipientEntity) {
        return service.sync(recipientEntity);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<RecipientEntity> search(@RequestBody Map<String, ?> dto, @RequestParam int pageIndex, @RequestParam int pageSize) {
        return service.search(dto, pageIndex, pageSize);
    }
}
