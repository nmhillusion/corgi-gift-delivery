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
@Table(name = "t_cx_commodity")
public class CommodityEntity extends Stringeable {
    @Id
    @Column(name = "com_id", nullable = false)
    private String comId;
    @Column(name = "com_name", nullable = false)
    private String comName;
    @Column(name = "com_type_id", nullable = false)
    private String comTypeId;
    @Column(name = "create_time", nullable = false)
    private ZonedDateTime createTime;

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

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public CommodityEntity setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public enum ID {
        COM_ID
    }
}
