package tech.nmhillusion.corgi_gift_delivery.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */

@Entity
@Table(name = "t_cx_delivery_return")
public class DeliveryReturnEntity extends BaseBusinessEntity<Long> {
    @Id
    @Column(name = "return_id")
    private Long returnId;

    @Column(name = "delivery_id")
    private Long deliveryId;

    @Column(name = "attempt_id")
    private Long attemptId;

    @Column(name = "return_status_id")
    private Integer returnStatusId;

    @Column(name = "note")
    private String note;


    public Long getReturnId() {
        return returnId;
    }

    public DeliveryReturnEntity setReturnId(Long returnId) {
        this.returnId = returnId;
        return this;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public DeliveryReturnEntity setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
        return this;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public DeliveryReturnEntity setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
        return this;
    }

    public Integer getReturnStatusId() {
        return returnStatusId;
    }

    public DeliveryReturnEntity setReturnStatusId(Integer returnStatusId) {
        this.returnStatusId = returnStatusId;
        return this;
    }

    public String getNote() {
        return note;
    }

    public DeliveryReturnEntity setNote(String note) {
        this.note = note;
        return this;
    }

    @Override
    public Long getId() {
        return returnId;
    }

    @Override
    public BaseBusinessEntity<Long> setId(Long id) {
        return setReturnId(id);
    }
}
