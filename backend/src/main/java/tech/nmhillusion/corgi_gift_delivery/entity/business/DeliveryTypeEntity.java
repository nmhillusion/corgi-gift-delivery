package tech.nmhillusion.corgi_gift_delivery.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
@Entity
@Table(name = "t_cx_delivery_type")
public class DeliveryTypeEntity extends Stringeable {
    @Id
    @Column(name = "type_id")
    private String typeId;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "type_desc")
    private String typeDesc;


    public String getTypeId() {
        return typeId;
    }

    public DeliveryTypeEntity setTypeId(String typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public DeliveryTypeEntity setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public DeliveryTypeEntity setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
        return this;
    }
}
