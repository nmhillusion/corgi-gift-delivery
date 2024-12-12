package tech.nmhillusion.slight_transportation.domains.recipient.recipientType;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.nmhillusion.slight_transportation.entity.business.RecipientTypeEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-09
 */

@RestController
@RequestMapping("/api/recipient-type")
public class RecipientTypeController {
    private final RecipientTypeService service;

    public RecipientTypeController(RecipientTypeService service) {
        this.service = service;
    }

    @GetMapping(value = "/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RecipientTypeEntity> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{recipientTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RecipientTypeEntity findById(@PathVariable String recipientTypeId) {
        return service.findById(recipientTypeId);
    }
}
