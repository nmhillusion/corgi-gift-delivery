package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-16
 */
@Entity
@Table(name = "t_cx_recipient")
public class RecipientEntity extends Stringeable {
    @Id
    @Column(name = "recipient_id", nullable = false)
    private long recipientId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "id_card_number", nullable = false, unique = true)
    private String idCardNumber;

    @Column(name = "recipient_type_id", nullable = false)
    private int recipientTypeId;

    public long getRecipientId() {
        return recipientId;
    }

    public RecipientEntity setRecipientId(long recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public RecipientEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public RecipientEntity setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
        return this;
    }

    public int getRecipientTypeId() {
        return recipientTypeId;
    }

    public RecipientEntity setRecipientTypeId(int recipientTypeId) {
        this.recipientTypeId = recipientTypeId;
        return this;
    }

    public enum ID {
        RECIPIENT_ID
    }
}
