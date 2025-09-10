package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturn;

import tech.nmhillusion.corgi_gift_delivery.domains.base.CoreDeliverySearchDto;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public class DeliveryReturnSearchDto extends CoreDeliverySearchDto {
    private String returnStatusId;

    public String getReturnStatusId() {
        return returnStatusId;
    }

    public DeliveryReturnSearchDto setReturnStatusId(String returnStatusId) {
        this.returnStatusId = returnStatusId;
        return this;
    }
}
