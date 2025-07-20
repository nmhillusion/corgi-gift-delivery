package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturn;

import tech.nmhillusion.corgi_gift_delivery.parser.StandardParserEnum;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public enum DeliveryReturnParserEnum implements StandardParserEnum {
    EVENT_ID("event_id"),
    CUSTOMER_ID("customer_id"),
    RETURN_STATUS("return_status"),
    NOTE("note");

    private final String columnName;

    DeliveryReturnParserEnum(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}
