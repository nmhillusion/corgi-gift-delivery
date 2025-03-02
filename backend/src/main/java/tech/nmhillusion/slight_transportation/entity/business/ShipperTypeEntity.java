package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-01-26
 */
@Entity
@Table(name = "t_cx_shipper_type")
public class ShipperTypeEntity extends Stringeable {
    @Id
    @Column(name = "type_id", nullable = false)
    private String typeId;

    @Column(name = "type_name", nullable = false, unique = true)
    private String typeName;

    @Column(name = "delivery_type_id", nullable = false)
    private String deliveryTypeId;


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

    public String getDeliveryTypeId() {
        return deliveryTypeId;
    }

    public ShipperTypeEntity setDeliveryTypeId(String deliveryTypeId) {
        this.deliveryTypeId = deliveryTypeId;
        return this;
    }
}
