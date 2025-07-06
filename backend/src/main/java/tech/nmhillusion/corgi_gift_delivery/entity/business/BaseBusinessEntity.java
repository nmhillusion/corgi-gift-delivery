package tech.nmhillusion.corgi_gift_delivery.entity.business;

import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public abstract class BaseBusinessEntity<ID> extends Stringeable {
    public abstract ID getId();

    public abstract BaseBusinessEntity<ID> setId(ID id);
}
