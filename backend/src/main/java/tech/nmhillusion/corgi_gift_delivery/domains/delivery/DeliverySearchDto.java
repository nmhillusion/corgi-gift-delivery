package tech.nmhillusion.corgi_gift_delivery.domains.delivery;

import tech.nmhillusion.corgi_gift_delivery.domains.base.CoreDeliverySearchDto;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-13
 */
public class DeliverySearchDto extends CoreDeliverySearchDto {
    private String deliveryStatusId;
    private String returnStatusId;

    public String getDeliveryStatusId() {
        return deliveryStatusId;
    }

    public DeliverySearchDto setDeliveryStatusId(String deliveryStatusId) {
        this.deliveryStatusId = deliveryStatusId;
        return this;
    }

    public String getReturnStatusId() {
        return returnStatusId;
    }

    public DeliverySearchDto setReturnStatusId(String returnStatusId) {
        this.returnStatusId = returnStatusId;
        return this;
    }
}
