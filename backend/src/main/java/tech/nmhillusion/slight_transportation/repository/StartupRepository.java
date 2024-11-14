package tech.nmhillusion.slight_transportation.repository;

import java.io.IOException;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-12
 */
public interface StartupRepository {
    void initDatabaseSchema() throws Throwable;
}
