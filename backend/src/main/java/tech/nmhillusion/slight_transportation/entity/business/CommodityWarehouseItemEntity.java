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
@Table(name = "t_cx_commodity_warehouse_item")
public class CommodityWarehouseItemEntity extends Stringeable {
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


    public String getItemId() {
        return itemId;
    }

    public CommodityWarehouseItemEntity setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getImportId() {
        return importId;
    }

    public CommodityWarehouseItemEntity setImportId(String importId) {
        this.importId = importId;
        return this;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public CommodityWarehouseItemEntity setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
        return this;
    }

    public String getComId() {
        return comId;
    }

    public CommodityWarehouseItemEntity setComId(String comId) {
        this.comId = comId;
        return this;
    }

    public double getQuantity() {
        return quantity;
    }

    public CommodityWarehouseItemEntity setQuantity(double quantity) {
        this.quantity = quantity;
        return this;
    }
}
