package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tech.nmhillusion.n2mix.type.Stringeable;

import java.time.ZonedDateTime;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-16
 */
@Entity
@Table(name = "t_cx_commodity_import")
public class CommodityImportEntity extends Stringeable {
    @Id
    @Column(name = "import_id", nullable = false)
    private int importId;
    @Column(name = "import_name", nullable = false)
    private String importName;
    @Column(name = "import_time")
    private ZonedDateTime importTime;
    @Column(name = "warehouse_id", nullable = false)
    private int warehouseId;

    public int getImportId() {
        return importId;
    }

    public CommodityImportEntity setImportId(int importId) {
        this.importId = importId;
        return this;
    }

    public ZonedDateTime getImportTime() {
        return importTime;
    }

    public CommodityImportEntity setImportTime(ZonedDateTime importTime) {
        this.importTime = importTime;
        return this;
    }

    public String getImportName() {
        return importName;
    }

    public CommodityImportEntity setImportName(String importName) {
        this.importName = importName;
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public CommodityImportEntity setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
        return this;
    }

    public enum ID {
        IMPORT_ID
    }
}
