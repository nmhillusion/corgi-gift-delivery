package tech.nmhillusion.slight_transportation.domains.delivery.deliveryAttempt;

import tech.nmhillusion.n2mix.type.Stringeable;

import java.time.ZonedDateTime;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-16
 */
public class ProcessAttemptDto extends Stringeable {
    private String deliveryStatusId;
    private ZonedDateTime actionDate;

    public String getDeliveryStatusId() {
        return deliveryStatusId;
    }

    public ProcessAttemptDto setDeliveryStatusId(String deliveryStatusId) {
        this.deliveryStatusId = deliveryStatusId;
        return this;
    }

    public ZonedDateTime getActionDate() {
        return actionDate;
    }

    public ProcessAttemptDto setActionDate(ZonedDateTime actionDate) {
        this.actionDate = actionDate;
        return this;
    }
}
