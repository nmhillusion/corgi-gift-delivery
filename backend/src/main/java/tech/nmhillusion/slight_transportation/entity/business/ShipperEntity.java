package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.*;
import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-16
 */

@Entity
@Table(name = "t_cx_shipper")
public class ShipperEntity extends Stringeable {
    @Id
    @Column(name = "shipper_id", nullable = false)
    @SequenceGenerator(name = "seq_gen__cx_shipper__shipper_id", sequenceName = "seq__cx_shipper__shipper_id", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen__cx_shipper__shipper_id")
    private int shipperId;

    @Column(name = "shipper_type_id")
    private String shipperTypeId;

    @Column(name = "shipper_code", nullable = false)
    private String shipperCode;

    @Column(name = "shipper_name", nullable = false)
    private String shipperName;

    public int getShipperId() {
        return shipperId;
    }

    public ShipperEntity setShipperId(int shipperId) {
        this.shipperId = shipperId;
        return this;
    }

    public String getShipperTypeId() {
        return shipperTypeId;
    }

    public ShipperEntity setShipperTypeId(String shipperTypeId) {
        this.shipperTypeId = shipperTypeId;
        return this;
    }

    public String getShipperCode() {
        return shipperCode;
    }

    public ShipperEntity setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
        return this;
    }

    public String getShipperName() {
        return shipperName;
    }

    public ShipperEntity setShipperName(String shipperName) {
        this.shipperName = shipperName;
        return this;
    }
}
