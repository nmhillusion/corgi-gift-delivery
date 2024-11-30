package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-16
 */
@Entity
@Table(name = "t_cx_shipper_type")
public class ShipperTypeEntity extends Stringeable {
    @Id
    @Column(name = "type_id", nullable = false, unique = true)
    private String typeId;

    @Column(name = "type_name", nullable = false, unique = true)
    private String typeName;

    public String getTypeId() {
        return typeId;
    }

    public ShipperTypeEntity setTypeId(String typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public ShipperTypeEntity setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }
}
