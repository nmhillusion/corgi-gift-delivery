package tech.nmhillusion.slight_transportation.constant;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-02-01
 */
public enum DeliveryStatus {
    CREATED("1"),
    IN_TRANSIT("2"),
    DELIVERED("3"),
    FAILED("4");

    private final String dbValue;

    DeliveryStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static DeliveryStatus fromDbValue(String dbValue) {
        for (DeliveryStatus deliveryStatus : DeliveryStatus.values()) {
            if (deliveryStatus.dbValue.equals(dbValue)) {
                return deliveryStatus;
            }
        }
        return null;
    }
}
