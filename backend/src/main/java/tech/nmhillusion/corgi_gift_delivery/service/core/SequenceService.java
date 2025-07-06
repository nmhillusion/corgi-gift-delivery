package tech.nmhillusion.corgi_gift_delivery.service.core;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public interface SequenceService {
    <T> Long getNextValue(Class<T> clazz);
}
