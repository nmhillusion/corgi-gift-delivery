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
@Table(name = "t_cx_commodity_export")
public class CommodityExportEntity {
    @Id
    @Column(name = "export_id", nullable = false)
    private long exportId;

    @Column(name = "export_name", nullable = false)
    private String exportName;

    @Column(name = "export_time", nullable = false)
    private ZonedDateTime exportTime;

    @Column(name = "warehouse_id", nullable = false)
    private int warehouseId;

    public long getExportId() {
        return exportId;
    }

    public CommodityExportEntity setExportId(long exportId) {
        this.exportId = exportId;
        return this;
    }

    public String getExportName() {
        return exportName;
    }

    public CommodityExportEntity setExportName(String exportName) {
        this.exportName = exportName;
        return this;
    }

    public ZonedDateTime getExportTime() {
        return exportTime;
    }

    public CommodityExportEntity setExportTime(ZonedDateTime exportTime) {
        this.exportTime = exportTime;
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public CommodityExportEntity setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
        return this;
    }

    public enum ID {
        EXPORT_ID
    }
}
