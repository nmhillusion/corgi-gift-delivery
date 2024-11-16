package tech.nmhillusion.slight_transportation.entity.business;

import tech.nmhillusion.n2mix.type.Stringeable;

import java.time.ZonedDateTime;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-16
 */
public class DeliveryEntity extends Stringeable {
    private String deliveryId;
    private String customerId;
    private String commodityId;
    private double comQuantity;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String currentAttemptId;
    private String deliveryStatusId;

    public String getDeliveryId() {
        return deliveryId;
    }

    public DeliveryEntity setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public DeliveryEntity setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public DeliveryEntity setCommodityId(String commodityId) {
        this.commodityId = commodityId;
        return this;
    }

    public double getComQuantity() {
        return comQuantity;
    }

    public DeliveryEntity setComQuantity(double comQuantity) {
        this.comQuantity = comQuantity;
        return this;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public DeliveryEntity setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public DeliveryEntity setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getCurrentAttemptId() {
        return currentAttemptId;
    }

    public DeliveryEntity setCurrentAttemptId(String currentAttemptId) {
        this.currentAttemptId = currentAttemptId;
        return this;
    }

    public String getDeliveryStatusId() {
        return deliveryStatusId;
    }

    public DeliveryEntity setDeliveryStatusId(String deliveryStatusId) {
        this.deliveryStatusId = deliveryStatusId;
        return this;
    }
}
