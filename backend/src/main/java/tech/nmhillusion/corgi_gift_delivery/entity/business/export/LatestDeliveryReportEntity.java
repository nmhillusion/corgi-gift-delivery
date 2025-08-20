package tech.nmhillusion.corgi_gift_delivery.entity.business.export;

import jakarta.persistence.Transient;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryAttemptEntity;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-08-20
 */
public class LatestDeliveryReportEntity extends DeliveryEntity {
    @Transient
    private DeliveryAttemptEntity latestDeliveryAttempt;
    @Transient
    private DeliveryReturnEntity latestDeliveryReturn;

    public DeliveryAttemptEntity getLatestDeliveryAttempt() {
        return latestDeliveryAttempt;
    }

    public LatestDeliveryReportEntity setLatestDeliveryAttempt(DeliveryAttemptEntity latestDeliveryAttempt) {
        this.latestDeliveryAttempt = latestDeliveryAttempt;
        return this;
    }

    public DeliveryReturnEntity getLatestDeliveryReturn() {
        return latestDeliveryReturn;
    }

    public LatestDeliveryReportEntity setLatestDeliveryReturn(DeliveryReturnEntity latestDeliveryReturn) {
        this.latestDeliveryReturn = latestDeliveryReturn;
        return this;
    }
}
