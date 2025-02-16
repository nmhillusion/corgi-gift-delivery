package tech.nmhillusion.slight_transportation.domains.delivery.deliveryAttempt;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import tech.nmhillusion.n2mix.type.Stringeable;

import java.time.ZonedDateTime;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-16
 */
@Validated
public class ProcessAttemptDto extends Stringeable {
    @NotNull
    private String deliveryStatusId;
    @NotNull
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
