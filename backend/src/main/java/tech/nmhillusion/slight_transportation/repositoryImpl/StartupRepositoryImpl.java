package tech.nmhillusion.slight_transportation.repositoryImpl;

import org.springframework.stereotype.Repository;
import tech.nmhillusion.n2mix.helper.database.query.DatabaseExecutor;
import tech.nmhillusion.n2mix.helper.database.query.DatabaseHelper;
import tech.nmhillusion.n2mix.util.IOStreamUtil;
import tech.nmhillusion.slight_transportation.repository.StartupRepository;

import java.io.IOException;
import java.io.InputStream;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-12
 */
@Repository
public class StartupRepositoryImpl implements StartupRepository {
    private static final String INIT_DB_SCHEMA__FILENAME = "startup/init-db-scheme.sql";

    private final DatabaseExecutor dbExecutor;

    public StartupRepositoryImpl(DatabaseHelper databaseHelper) {
        this.dbExecutor = databaseHelper.getExecutor();
    }

    private String getStartupSql(String sqlFilename) throws IOException {
        try (final InputStream sqlFileStream = getClass().getClassLoader().getResourceAsStream(
                sqlFilename
        )) {
            return IOStreamUtil.convertInputStreamToString(sqlFileStream);
        }
    }

    @Override
    public void initDatabaseSchema() throws Throwable {
        final String startupSql = getStartupSql(INIT_DB_SCHEMA__FILENAME);

        dbExecutor.doWork(conn -> {
            conn.doPreparedStatement(startupSql, preparedStatement -> {
                final boolean executed = preparedStatement.execute();

                getLogger(this).info("executed result: {}", executed);
            });
        });
    }
}
