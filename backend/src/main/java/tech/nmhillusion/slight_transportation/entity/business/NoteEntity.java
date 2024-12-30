package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tech.nmhillusion.n2mix.type.Stringeable;

import java.time.ZonedDateTime;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-16
 */
@Entity
@Table(name = "t_cx_note")
public class NoteEntity extends Stringeable {
    @Id
    @Column(name = "note_id", nullable = false)
    private long noteId;

    @Column(name = "note_content", nullable = false)
    private String noteContent;

    @Column(name = "note_time", nullable = false)
    private ZonedDateTime noteTime;

    @Column(name = "recipient_id")
    private String recipientId;

    @Column(name = "delivery_id")
    private String deliveryId;

    @Column(name = "delivery_attempt_id")
    private String deliveryAttemptId;

    @Column(name = "import_id")
    private String importId;

    @Column(name = "warehouse_item_id")
    private String warehouseItemId;

    public long getNoteId() {
        return noteId;
    }

    public NoteEntity setNoteId(long noteId) {
        this.noteId = noteId;
        return this;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public NoteEntity setNoteContent(String noteContent) {
        this.noteContent = noteContent;
        return this;
    }

    public ZonedDateTime getNoteTime() {
        return noteTime;
    }

    public NoteEntity setNoteTime(ZonedDateTime noteTime) {
        this.noteTime = noteTime;
        return this;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public NoteEntity setRecipientId(String recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public NoteEntity setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
        return this;
    }

    public String getDeliveryAttemptId() {
        return deliveryAttemptId;
    }

    public NoteEntity setDeliveryAttemptId(String deliveryAttemptId) {
        this.deliveryAttemptId = deliveryAttemptId;
        return this;
    }

    public String getImportId() {
        return importId;
    }

    public NoteEntity setImportId(String importId) {
        this.importId = importId;
        return this;
    }

    public String getWarehouseItemId() {
        return warehouseItemId;
    }

    public NoteEntity setWarehouseItemId(String warehouseItemId) {
        this.warehouseItemId = warehouseItemId;
        return this;
    }
}
