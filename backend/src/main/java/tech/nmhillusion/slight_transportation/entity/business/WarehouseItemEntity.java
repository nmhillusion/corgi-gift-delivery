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
    private int importId;

    @Column(name = "warehouse_id", nullable = false)
    private int warehouseId;

    @Column(name = "com_id", nullable = false)
    private long comId;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Column(name = "create_time", nullable = false)
    private ZonedDateTime createTime;

    public String getItemId() {
        return itemId;
    }

    public WarehouseItemEntity setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public int getImportId() {
        return importId;
    }

    public WarehouseItemEntity setImportId(int importId) {
        this.importId = importId;
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public WarehouseItemEntity setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
        return this;
    }

    public long getComId() {
        return comId;
    }

    public WarehouseItemEntity setComId(long comId) {
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

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public WarehouseItemEntity setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }
}
