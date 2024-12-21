package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */

@Entity
@Table(name = "t_cx_delivery_package_item")
public class DeliveryPackageItemEntity {

    @Id
    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "package_id", nullable = false)
    private int packageId;

    @Column(name = "warehouse_id", nullable = false)
    private int warehouseId;

    @Column(name = "export_id", nullable = false)
    private int exportId;

    @Column(name = "com_id", nullable = false)
    private long comId;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Column(name = "create_time", nullable = false)
    private ZonedDateTime createTime;

    public String getItemId() {
        return itemId;
    }

    public DeliveryPackageItemEntity setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public int getPackageId() {
        return packageId;
    }

    public DeliveryPackageItemEntity setPackageId(int packageId) {
        this.packageId = packageId;
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public DeliveryPackageItemEntity setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
        return this;
    }

    public int getExportId() {
        return exportId;
    }

    public DeliveryPackageItemEntity setExportId(int exportId) {
        this.exportId = exportId;
        return this;
    }

    public long getComId() {
        return comId;
    }

    public DeliveryPackageItemEntity setComId(long comId) {
        this.comId = comId;
        return this;
    }

    public double getQuantity() {
        return quantity;
    }

    public DeliveryPackageItemEntity setQuantity(double quantity) {
        this.quantity = quantity;
        return this;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public DeliveryPackageItemEntity setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }
}
