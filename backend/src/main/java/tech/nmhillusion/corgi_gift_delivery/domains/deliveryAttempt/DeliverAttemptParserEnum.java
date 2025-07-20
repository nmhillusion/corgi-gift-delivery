package tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt;

import tech.nmhillusion.corgi_gift_delivery.parser.StandardParserEnum;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public enum DeliverAttemptParserEnum implements StandardParserEnum {
    EVENT_ID("event_id"),
    CUSTOMER_ID("customer_id"),
    DELIVERY_TYPE("delivery_type"),
    DELIVERY_STATUS("delivery_status"),
    NOTE("note");

    private final String columnName;

    DeliverAttemptParserEnum(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}
