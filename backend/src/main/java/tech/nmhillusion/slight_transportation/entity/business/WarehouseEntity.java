package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-08
 */
@Entity
@Table(name = "t_cx_warehouse")
public class WarehouseEntity extends Stringeable {
    @Id
    @Column(name = "warehouse_id", nullable = false)
    private String warehouseId;

    @Column(name = "warehouse_name", nullable = false)
    private String warehouseName;

    @Column(name = "warehouse_address", nullable = true)
    private String warehouseAddress;

    public String getWarehouseId() {
        return warehouseId;
    }

    public WarehouseEntity setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
        return this;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public WarehouseEntity setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
        return this;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public WarehouseEntity setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
        return this;
    }

    public enum ID {
        WAREHOUSE_ID
    }
}
