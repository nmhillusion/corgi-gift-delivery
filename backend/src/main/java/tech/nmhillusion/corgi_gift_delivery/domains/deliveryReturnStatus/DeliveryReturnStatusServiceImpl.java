package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturnStatus;

import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnStatusEntity;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
@TransactionalService
public class DeliveryReturnStatusServiceImpl implements DeliveryReturnStatusService {
    private final DeliveryReturnStatusRepository repository;

    public DeliveryReturnStatusServiceImpl(DeliveryReturnStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public DeliveryReturnStatusEntity getDeliveryReturnStatusByStatusName(String statusName) {
        return repository.findByStatusName(statusName);
    }
}
