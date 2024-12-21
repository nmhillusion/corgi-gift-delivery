package tech.nmhillusion.slight_transportation.domains.delivery.delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-21
 */
public interface DeliveryService {
    Page<DeliveryEntity> search(Map<String, ?> dto, int pageIndex, int pageSize);

    DeliveryEntity findById(String deliveryId);

    DeliveryEntity save(DeliveryEntity deliveryEntity);


    class Impl implements DeliveryService {
        private final DeliveryRepository repository;

        public Impl(DeliveryRepository repository) {
            this.repository = repository;
        }

        @Override
        public Page<DeliveryEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
            final String recipientId = StringUtil.trimWithNull(dto.get("recipientId"));

            return repository.search(recipientId, PageRequest.of(pageIndex, pageSize));
        }

        @Override
        public DeliveryEntity findById(String deliveryId) {
            return repository.findById(deliveryId).orElse(null);
        }

        @Override
        public DeliveryEntity save(DeliveryEntity deliveryEntity) {

            return repository.save(deliveryEntity);
        }
    }
}
