package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen__cx_commodity_import__import_id")
    @SequenceGenerator(name = "seq_gen__cx_commodity_import__import_id", sequenceName = "seq__cx_commodity_import__import_id", allocationSize = 1, initialValue = 1)
    private int importId;
    @Column(name = "import_name", nullable = false)
    private String importName;
    @Column(name = "import_time")
    private ZonedDateTime importTime;
    @Column(name = "warehouse_id", nullable = false)
    private String warehouseId;

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

    public String getWarehouseId() {
        return warehouseId;
    }

    public CommodityImportEntity setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
        return this;
    }
}
