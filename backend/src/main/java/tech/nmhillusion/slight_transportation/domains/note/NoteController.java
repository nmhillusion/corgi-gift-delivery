package tech.nmhillusion.slight_transportation.domains.note;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.slight_transportation.entity.business.NoteEntity;

import java.util.Map;

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

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<NoteEntity> search(@RequestBody @Valid Map<String, ?> searchDto,
                                   @RequestParam(defaultValue = "0") int pageIndex,
                                   @RequestParam(defaultValue = "10") int pageSize) {
        return service.search(searchDto, pageIndex, pageSize);
    }
}
