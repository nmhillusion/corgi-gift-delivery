package tech.nmhillusion.corgi_gift_delivery.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */

@Entity
@Table(name = "t_cx_delivery_return_status")
public class DeliveryReturnStatusEntity {
    @Id
    @Column(name = "status_id")
    private String statusId;

    @Column(name = "status_name")
    private String statusName;

    public String getStatusId() {
        return statusId;
    }

    public DeliveryReturnStatusEntity setStatusId(String statusId) {
        this.statusId = statusId;
        return this;
    }

    public String getStatusName() {
        return statusName;
    }

    public DeliveryReturnStatusEntity setStatusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

}
