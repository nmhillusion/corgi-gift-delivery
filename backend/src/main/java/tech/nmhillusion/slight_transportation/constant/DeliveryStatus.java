package tech.nmhillusion.slight_transportation.constant;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-02-01
 */
public enum DeliveryStatus {
    CREATED(1),
    IN_TRANSIT(2),
    DELIVERED(3),
    FAILED(4);

    private final int dbValue;

    DeliveryStatus(int dbValue) {
        this.dbValue = dbValue;
    }

    public int getDbValue() {
        return dbValue;
    }
}
