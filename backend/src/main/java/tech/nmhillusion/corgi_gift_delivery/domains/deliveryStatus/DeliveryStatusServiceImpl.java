package tech.nmhillusion.corgi_gift_delivery.domains.deliveryStatus;

import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryStatusEntity;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
@TransactionalService
public class DeliveryStatusServiceImpl implements DeliveryStatusService {
    private final DeliveryStatusRepository repository;

    public DeliveryStatusServiceImpl(DeliveryStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public DeliveryStatusEntity getDeliveryStatusByStatusName(String statusName) {
        return repository.findByStatusName(statusName);
    }
}
