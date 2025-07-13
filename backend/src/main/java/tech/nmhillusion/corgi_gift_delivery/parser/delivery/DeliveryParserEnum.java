package tech.nmhillusion.corgi_gift_delivery.parser.delivery;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-12
 */
public enum DeliveryParserEnum {
    EVENT_ID("event_id"),
    DELIVERY_PERIOD_YEAR("delivery_period_year"),
    DELIVERY_PERIOD_MONTH("delivery_period_month"),
    TERRITORY("territory"),
    REGION("region"),
    ORGAN_ID("organ_id"),
    RECEIVED_ORGAN("received_organ"),
    AMD_NAME("amd_name"),
    CUSTOMER_LEVEL("customer_level"),
    CUSTOMER_ID("customer_id"),
    CUSTOMER_NAME("customer_name"),
    ID_CARD_NUMBER("id_card_number"),
    PHONE_NUMBER("phone_number"),
    ADDRESS("address"),
    GIFT_NAME("gift_name"),
    NOTE("note");

    private final String columnName;

    DeliveryParserEnum(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
