package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-14
 */
@Entity
@Table(name = "t_cx_warehouse_item")
public class WarehouseItemEntity {
    @Id
    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "import_id", nullable = false)
    private String importId;

    @Column(name = "warehouse_id", nullable = false)
    private String warehouseId;

    @Column(name = "com_id", nullable = false)
    private String comId;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Column(name = "used_quantity", columnDefinition = "numeric default 0")
    private double usedQuantity;

    @Column(name = "create_time", nullable = false)
    private ZonedDateTime createTime;

    @Column(name = "update_time", nullable = true)
    private ZonedDateTime updateTime;

    public String getItemId() {
        return itemId;
    }

    public WarehouseItemEntity setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getImportId() {
        return importId;
    }

    public WarehouseItemEntity setImportId(String importId) {
        this.importId = importId;
        return this;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public WarehouseItemEntity setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
        return this;
    }

    public String getComId() {
        return comId;
    }

    public WarehouseItemEntity setComId(String comId) {
        this.comId = comId;
        return this;
    }

    public double getQuantity() {
        return quantity;
    }

    public WarehouseItemEntity setQuantity(double quantity) {
        this.quantity = quantity;
        return this;
    }

    public double getUsedQuantity() {
        return usedQuantity;
    }

    public WarehouseItemEntity setUsedQuantity(double usedQuantity) {
        this.usedQuantity = usedQuantity;
        return this;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public WarehouseItemEntity setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public WarehouseItemEntity setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public enum ID {
        ITEM_ID
    }
}
