package tech.nmhillusion.slight_transportation.domains.delivery.deliveryStatus;

import org.springframework.stereotype.Service;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryStatusEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-16
 */
@Service
public class DeliveryStatusServiceImpl implements DeliveryStatusService {

    private final DeliveryStatusRepository deliveryStatusRepository;

    public DeliveryStatusServiceImpl(DeliveryStatusRepository deliveryStatusRepository) {
        this.deliveryStatusRepository = deliveryStatusRepository;
    }

    @Override
    public List<DeliveryStatusEntity> list() {
        return deliveryStatusRepository.list();
    }
}
