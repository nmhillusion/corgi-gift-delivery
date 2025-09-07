package tech.nmhillusion.corgi_gift_delivery.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
@Entity
@Table(name = "t_cx_delivery_status")
public class DeliveryStatusEntity extends Stringeable {
    @Id
    @Column(name = "status_id")
    private String statusId;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "status_desc")
    private String statusDesc;

    @Column(name = "status_order")
    private Integer statusOrder;


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

    public Integer getStatusOrder() {
        return statusOrder;
    }

    public DeliveryStatusEntity setStatusOrder(Integer statusOrder) {
        this.statusOrder = statusOrder;
        return this;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public DeliveryStatusEntity setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
        return this;
    }
}
