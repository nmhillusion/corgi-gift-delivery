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
@Table(name = "t_cx_delivery_attempt")
public class DeliveryAttemptEntity extends Stringeable {
    @Id
    @Column(name = "attempt_id", nullable = false)
    private long attemptId;

    @Column(name = "delivery_id", nullable = false)
    private long deliveryId;

    @Column(name = "delivery_type_id", nullable = false)
    private int deliveryTypeId;

    @Column(name = "shipper_id")
    private long shipperId;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "delivery_status_id")
    private int deliveryStatusId;

    public long getAttemptId() {
        return attemptId;
    }

    public DeliveryAttemptEntity setAttemptId(long attemptId) {
        this.attemptId = attemptId;
        return this;
    }

    public long getDeliveryId() {
        return deliveryId;
    }

    public DeliveryAttemptEntity setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
        return this;
    }

    public int getDeliveryTypeId() {
        return deliveryTypeId;
    }

    public DeliveryAttemptEntity setDeliveryTypeId(int deliveryTypeId) {
        this.deliveryTypeId = deliveryTypeId;
        return this;
    }

    public long getShipperId() {
        return shipperId;
    }

    public DeliveryAttemptEntity setShipperId(long shipperId) {
        this.shipperId = shipperId;
        return this;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public DeliveryAttemptEntity setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public DeliveryAttemptEntity setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public int getDeliveryStatusId() {
        return deliveryStatusId;
    }

    public DeliveryAttemptEntity setDeliveryStatusId(int deliveryStatusId) {
        this.deliveryStatusId = deliveryStatusId;
        return this;
    }

    public enum ID {
        ATTEMPT_ID
    }
}
