package tech.nmhillusion.corgi_gift_delivery.domains.deliveryType;

import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryTypeEntity;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public interface DeliveryTypeService {
    DeliveryTypeEntity getDeliveryTypeByTypeName(String typeName);

    DeliveryTypeEntity getDeliveryTypeByTypeId(String typeId);

}
