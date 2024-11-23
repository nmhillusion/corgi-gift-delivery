package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.*;
import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-16
 */
@Entity
@Table(name = "t_cx_commodity_type")
public class CommodityTypeEntity extends Stringeable {
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq__cx_commodity_type_type_id")
    @SequenceGenerator(name = "t_cx_commodity_type_type_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "type_id", nullable = false)
    private int typeId;

    @Column(name = "type_name", nullable = false, unique = true)
    private String typeName;

    public Long getVersion() {
        return version;
    }

    public CommodityTypeEntity setVersion(Long version) {
        this.version = version;
        return this;
    }

    public int getTypeId() {
        return typeId;
    }

    public CommodityTypeEntity setTypeId(int typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public CommodityTypeEntity setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }
}
