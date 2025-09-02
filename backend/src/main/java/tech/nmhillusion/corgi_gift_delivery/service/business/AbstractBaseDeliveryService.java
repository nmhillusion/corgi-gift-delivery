package tech.nmhillusion.corgi_gift_delivery.service.business;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nmhillusion.corgi_gift_delivery.entity.business.BaseBusinessEntity;
import tech.nmhillusion.corgi_gift_delivery.service.core.SequenceService;
import tech.nmhillusion.corgi_gift_delivery.service_impl.business.BaseBusinessServiceImpl;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-09-02
 */
public abstract class AbstractBaseDeliveryService<E extends BaseBusinessEntity<Long>, DTO, R extends JpaRepository<E, Long>> extends BaseBusinessServiceImpl<E, R> implements BaseDeliveryService<E, DTO> {

    protected AbstractBaseDeliveryService(R repository, SequenceService sequenceService) {
        super(repository, sequenceService);
    }

    @Override
    public long getTotalElementsForSearch(DTO dto) {
        return search(dto, 0, Integer.MAX_VALUE).getTotalElements();
    }
}
