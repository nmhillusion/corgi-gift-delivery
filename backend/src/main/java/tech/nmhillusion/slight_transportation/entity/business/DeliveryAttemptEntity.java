package tech.nmhillusion.slight_transportation.entity.business;

import tech.nmhillusion.n2mix.type.Stringeable;

import java.time.ZonedDateTime;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-16
 */
public class DeliveryAttemptEntity extends Stringeable {
    private String attemptId;
    private String deliveryId;
    private String deliveryTypeId;
    private String shipperId;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String deliveryStatusId;

    public String getAttemptId() {
        return attemptId;
    }

    public DeliveryAttemptEntity setAttemptId(String attemptId) {
        this.attemptId = attemptId;
        return this;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public DeliveryAttemptEntity setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
        return this;
    }

    public String getDeliveryTypeId() {
        return deliveryTypeId;
    }

    public DeliveryAttemptEntity setDeliveryTypeId(String deliveryTypeId) {
        this.deliveryTypeId = deliveryTypeId;
        return this;
    }

    public String getShipperId() {
        return shipperId;
    }

    public DeliveryAttemptEntity setShipperId(String shipperId) {
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

    public String getDeliveryStatusId() {
        return deliveryStatusId;
    }

    public DeliveryAttemptEntity setDeliveryStatusId(String deliveryStatusId) {
        this.deliveryStatusId = deliveryStatusId;
        return this;
    }
}
