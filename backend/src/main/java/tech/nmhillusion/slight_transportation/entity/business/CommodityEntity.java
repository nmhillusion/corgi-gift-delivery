package tech.nmhillusion.slight_transportation.entity.business;

import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-16
 */
public class CommodityEntity extends Stringeable {
    private String comId;
    private String comName;
    private String comTypeId;

    public String getComId() {
        return comId;
    }

    public CommodityEntity setComId(String comId) {
        this.comId = comId;
        return this;
    }

    public String getComName() {
        return comName;
    }

    public CommodityEntity setComName(String comName) {
        this.comName = comName;
        return this;
    }

    public String getComTypeId() {
        return comTypeId;
    }

    public CommodityEntity setComTypeId(String comTypeId) {
        this.comTypeId = comTypeId;
        return this;
    }
}
