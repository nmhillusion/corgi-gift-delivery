package tech.nmhillusion.slight_transportation.domains.note;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.slight_transportation.entity.business.NoteEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-18
 */

@RestController
@RequestMapping("/api/note")
public class NoteController {
    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteEntity save(@RequestBody NoteEntity noteEntity) {
        return service.save(noteEntity);
    }

    @GetMapping(value = "/{noteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteEntity findById(@PathVariable String noteId) {
        return service.findById(noteId);
    }

    @DeleteMapping(value = "/{noteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteById(@PathVariable String noteId) {
        service.deleteById(noteId);
    }


    @GetMapping(value = "/recipient/{recipientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteEntity> findAllByRecipientId(@PathVariable String recipientId) {
        return service.findAllByRecipientId(recipientId);
    }

    @GetMapping(value = "/delivery/{deliveryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteEntity> findAllByDeliveryId(@PathVariable String deliveryId) {
        return service.findAllByDeliveryId(deliveryId);
    }

    @GetMapping(value = "/deliveryAttempt/{deliveryAttemptId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteEntity> findAllByDeliveryAttemptId(@PathVariable String deliveryAttemptId) {
        return service.findAllByDeliveryAttemptId(deliveryAttemptId);
    }

    @GetMapping(value = "/import/{importId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteEntity> findAllByImportId(@PathVariable String importId) {
        return service.findAllByImportId(importId);
    }

    @GetMapping(value = "/warehouseItem/{warehouseItemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteEntity> findAllByWarehouseItemId(@PathVariable String warehouseItemId) {
        return service.findAllByWarehouseItemId(warehouseItemId);
    }
}
