package tech.nmhillusion.corgi_gift_delivery.service.business;

import tech.nmhillusion.corgi_gift_delivery.entity.business.BaseBusinessEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public interface BaseBusinessService<E extends BaseBusinessEntity<Long>> {
    List<E> saveBatch(Iterable<E> entities);
}
