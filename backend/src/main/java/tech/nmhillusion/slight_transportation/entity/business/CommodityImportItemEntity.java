package tech.nmhillusion.slight_transportation.entity.business;

import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-16
 */
public class CommodityImportItemEntity extends Stringeable {
    private String itemId;
    private String importId;
    private String commodityId;
    private double quantity;

    public String getItemId() {
        return itemId;
    }

    public CommodityImportItemEntity setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getImportId() {
        return importId;
    }

    public CommodityImportItemEntity setImportId(String importId) {
        this.importId = importId;
        return this;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public CommodityImportItemEntity setCommodityId(String commodityId) {
        this.commodityId = commodityId;
        return this;
    }

    public double getQuantity() {
        return quantity;
    }

    public CommodityImportItemEntity setQuantity(double quantity) {
        this.quantity = quantity;
        return this;
    }
}
