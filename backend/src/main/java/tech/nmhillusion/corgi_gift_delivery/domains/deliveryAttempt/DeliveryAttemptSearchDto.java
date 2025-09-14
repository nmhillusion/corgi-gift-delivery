package tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt;

import tech.nmhillusion.corgi_gift_delivery.domains.base.CoreDeliverySearchDto;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public class DeliveryAttemptSearchDto extends CoreDeliverySearchDto {
    private String deliveryStatusId;
    private String deliveryTypeId;

    public String getDeliveryStatusId() {
        return deliveryStatusId;
    }

    public DeliveryAttemptSearchDto setDeliveryStatusId(String deliveryStatusId) {
        this.deliveryStatusId = deliveryStatusId;
        return this;
    }

    public String getDeliveryTypeId() {
        return deliveryTypeId;
    }

    public DeliveryAttemptSearchDto setDeliveryTypeId(String deliveryTypeId) {
        this.deliveryTypeId = deliveryTypeId;
        return this;
    }
}
