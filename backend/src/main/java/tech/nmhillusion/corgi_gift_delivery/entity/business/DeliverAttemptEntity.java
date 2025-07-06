package tech.nmhillusion.corgi_gift_delivery.entity.business;

import jakarta.persistence.*;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */

@Entity
@Table(name = "t_cx_deliver_attempt")
public class DeliverAttemptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_id")
    private Long attemptId;

    @Column(name = "delivery_id")
    private Long deliveryId;

    @Column(name = "delivery_type_id")
    private Integer deliveryTypeId;

    @Column(name = "delivery_status_id")
    private Integer deliveryStatusId;

    @Column(name = "note")
    private String note;

    public Long getAttemptId() {
        return attemptId;
    }

    public DeliverAttemptEntity setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
        return this;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public DeliverAttemptEntity setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
        return this;
    }

    public Integer getDeliveryTypeId() {
        return deliveryTypeId;
    }

    public DeliverAttemptEntity setDeliveryTypeId(Integer deliveryTypeId) {
        this.deliveryTypeId = deliveryTypeId;
        return this;
    }

    public Integer getDeliveryStatusId() {
        return deliveryStatusId;
    }

    public DeliverAttemptEntity setDeliveryStatusId(Integer deliveryStatusId) {
        this.deliveryStatusId = deliveryStatusId;
        return this;
    }

    public String getNote() {
        return note;
    }

    public DeliverAttemptEntity setNote(String note) {
        this.note = note;
        return this;
    }

}
