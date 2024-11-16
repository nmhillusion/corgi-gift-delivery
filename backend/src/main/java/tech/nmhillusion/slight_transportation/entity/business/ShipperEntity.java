package tech.nmhillusion.slight_transportation.entity.business;

import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-16
 */
public class ShipperEntity extends Stringeable {
    private String shipperId;
    private String shipperTypeId;
    private String shipperCode;
    private String shipperName;

    public String getShipperId() {
        return shipperId;
    }

    public ShipperEntity setShipperId(String shipperId) {
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
