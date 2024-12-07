package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.*;
import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-16
 */
@Entity
@Table(name = "t_cx_commodity")
public class CommodityEntity extends Stringeable {
    @Id
    @SequenceGenerator(name = "seq__cx_commodity__com_id", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq__cx_commodity__com_id")
    @Column(name = "com_id", nullable = false)
    private Long comId;
    @Column(name = "com_name", nullable = false)
    private String comName;
    @Column(name = "com_type_id", nullable = false)
    private String comTypeId;
    @Column(name = "create_time", nullable = false)
    private String createTime;

    public Long getComId() {
        return comId;
    }

    public CommodityEntity setComId(Long comId) {
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

    public String getCreateTime() {
        return createTime;
    }

    public CommodityEntity setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }
}
