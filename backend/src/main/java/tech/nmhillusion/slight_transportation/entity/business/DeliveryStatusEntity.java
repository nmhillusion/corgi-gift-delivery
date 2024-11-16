package tech.nmhillusion.slight_transportation.entity.business;

import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-16
 */
public class DeliveryStatusEntity extends Stringeable {
    private String statusId;
    private String statusName;

    public String getStatusId() {
        return statusId;
    }

    public DeliveryStatusEntity setStatusId(String statusId) {
        this.statusId = statusId;
        return this;
    }

    public String getStatusName() {
        return statusName;
    }

    public DeliveryStatusEntity setStatusName(String statusName) {
        this.statusName = statusName;
        return this;
    }
}
