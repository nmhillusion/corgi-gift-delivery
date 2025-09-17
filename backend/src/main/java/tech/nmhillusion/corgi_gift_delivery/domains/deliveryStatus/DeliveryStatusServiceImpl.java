package tech.nmhillusion.corgi_gift_delivery.domains.deliveryStatus;

import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryStatusEntity;

import java.util.List;
import java.util.Optional;

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
    public Optional<DeliveryStatusEntity> getDeliveryStatusByStatusName(String statusName) {
        return repository.findByStatusName(statusName);
    }

    @Override
    public DeliveryStatusEntity getDeliveryStatusByStatusId(String statusId) {
        return repository.findById(statusId)
                .orElseThrow();
    }

    @Override
    public List<DeliveryStatusEntity> getAll() {
        return repository.findAll();
    }
}
