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
@Table(name = "t_cx_delivery_status")
public class DeliveryStatusEntity extends Stringeable {
    @Id
    @Column(name = "status_id", nullable = false)
    private String statusId;
    @Column(name = "status_name", nullable = false, unique = true)
    private String statusName;
    @Column(name = "status_order", nullable = false)
    private double statusOrder;

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

    public double getStatusOrder() {
        return statusOrder;
    }

    public DeliveryStatusEntity setStatusOrder(double statusOrder) {
        this.statusOrder = statusOrder;
        return this;
    }
}
