package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
@Entity
@Table(name = "t_cx_delivery_package")
public class DeliveryPackageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen__cx_delivery_package__package_id")
    @SequenceGenerator(name = "seq_gen__cx_delivery_package__package_id", sequenceName = "seq__cx_delivery_package__package_id", allocationSize = 1, initialValue = 1)
    @Column(name = "package_id", nullable = false)
    private int packageId;

    @Column(name = "delivery_id", nullable = false)
    private String deliveryId;

    @Column(name = "package_name", nullable = false)
    private String packageName;

    @Column(name = "package_time", nullable = false)
    private ZonedDateTime packageTime;


    public int getPackageId() {
        return packageId;
    }

    public DeliveryPackageEntity setPackageId(int packageId) {
        this.packageId = packageId;
        return this;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public DeliveryPackageEntity setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public DeliveryPackageEntity setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public ZonedDateTime getPackageTime() {
        return packageTime;
    }

    public DeliveryPackageEntity setPackageTime(ZonedDateTime packageTime) {
        this.packageTime = packageTime;
        return this;
    }
}
