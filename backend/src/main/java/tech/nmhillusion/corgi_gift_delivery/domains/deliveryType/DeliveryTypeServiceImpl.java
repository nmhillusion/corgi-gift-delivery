package tech.nmhillusion.corgi_gift_delivery.domains.deliveryType;

import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryTypeEntity;

import java.util.List;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
@TransactionalService
public class DeliveryTypeServiceImpl implements DeliveryTypeService {
    private final DeliveryTypeRepository repository;

    public DeliveryTypeServiceImpl(DeliveryTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public DeliveryTypeEntity getDeliveryTypeByTypeName(String typeName) {
        return repository.findByTypeName(typeName);
    }

    @Override
    public DeliveryTypeEntity getDeliveryTypeByTypeId(String typeId) {
        return repository.findById(typeId)
                .orElseThrow();
    }

    @Override
    public List<DeliveryTypeEntity> getAll() {
        return repository.findAll();
    }
}
