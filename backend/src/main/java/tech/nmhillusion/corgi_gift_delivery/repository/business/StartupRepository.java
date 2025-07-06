package tech.nmhillusion.corgi_gift_delivery.repository.business;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-12
 */
public interface StartupRepository {
    void initDatabaseSchema() throws Throwable;

    void deleteOldData() throws Throwable;

    void initData() throws Throwable;
}
