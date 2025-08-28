package tech.nmhillusion.corgi_gift_delivery.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */

@Entity
@Table(name = "t_cx_delivery_attempt")
public class DeliveryAttemptEntity extends BaseBusinessEntity<Long> {
    @Id
    @Column(name = "attempt_id")
    private Long attemptId;

    @Column(name = "delivery_id")
    private Long deliveryId;

    @Column(name = "delivery_type_id")
    private Integer deliveryTypeId;

    @Column(name = "delivery_status_id")
    private Integer deliveryStatusId;

    @Column(name = "delivery_date")
    private ZonedDateTime deliveryDate;

    @Column(name = "note")
    private String note;

    public Long getAttemptId() {
        return attemptId;
    }

    public DeliveryAttemptEntity setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
        return this;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public DeliveryAttemptEntity setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
        return this;
    }

    public Integer getDeliveryTypeId() {
        return deliveryTypeId;
    }

    public DeliveryAttemptEntity setDeliveryTypeId(Integer deliveryTypeId) {
        this.deliveryTypeId = deliveryTypeId;
        return this;
    }

    public Integer getDeliveryStatusId() {
        return deliveryStatusId;
    }

    public DeliveryAttemptEntity setDeliveryStatusId(Integer deliveryStatusId) {
        this.deliveryStatusId = deliveryStatusId;
        return this;
    }

    public ZonedDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public DeliveryAttemptEntity setDeliveryDate(ZonedDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public String getNote() {
        return note;
    }

    public DeliveryAttemptEntity setNote(String note) {
        this.note = note;
        return this;
    }

    @Override
    public Long getId() {
        return attemptId;
    }

    @Override
    public BaseBusinessEntity<Long> setId(Long id) {
        return setAttemptId(id);
    }
}
