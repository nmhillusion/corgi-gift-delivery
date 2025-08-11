package tech.nmhillusion.corgi_gift_delivery.domains.base;

import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-08-11
 */
public abstract class CoreDeliverySearchDto extends Stringeable {
    private String eventId;
    private String customerId;

    public String getEventId() {
        return eventId;
    }

    public CoreDeliverySearchDto setEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public CoreDeliverySearchDto setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }
}
