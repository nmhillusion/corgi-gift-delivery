package tech.nmhillusion.slight_transportation.entity.business;

import tech.nmhillusion.n2mix.type.Stringeable;

import java.time.ZonedDateTime;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-16
 */
public class CommodityImportEntity extends Stringeable {
    private String importId;
    private ZonedDateTime importTime;

    public String getImportId() {
        return importId;
    }

    public CommodityImportEntity setImportId(String importId) {
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
}
