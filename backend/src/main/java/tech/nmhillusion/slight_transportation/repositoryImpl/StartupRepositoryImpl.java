package tech.nmhillusion.slight_transportation.repositoryImpl;

import org.springframework.stereotype.Repository;
import tech.nmhillusion.n2mix.helper.database.query.DatabaseExecutor;
import tech.nmhillusion.n2mix.helper.database.query.DatabaseHelper;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.util.IOStreamUtil;
import tech.nmhillusion.slight_transportation.provider.SqlScriptProvider;
import tech.nmhillusion.slight_transportation.repository.StartupRepository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-12
 */
@Repository
public class StartupRepositoryImpl implements StartupRepository {
    private static final String INIT_DB_SCHEMA__FILENAME = "startup/init-db-scheme.sql";
    private static final String INIT_DATA__FILENAME = "startup/init-data.sql";

    private final DatabaseExecutor dbExecutor;
    private final SqlScriptProvider sqlScriptProvider;

    public StartupRepositoryImpl(DatabaseHelper databaseHelper, SqlScriptProvider sqlScriptProvider) {
        this.dbExecutor = databaseHelper.getExecutor();
        this.sqlScriptProvider = sqlScriptProvider;
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

    private boolean isExistedData() throws Throwable {
        final String checkExistedDataSql = sqlScriptProvider
                .getSqlScript("startup/check-existed-data.sql");

        return dbExecutor.doReturningWork(conn ->
                conn.doReturningPreparedStatement(checkExistedDataSql, preparedStatement -> {
                    long count = 0;

                    try {
                        final ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next()) {
                            count = resultSet.getLong(1);
                        }
                    } catch (Throwable ex) {
                        getLogger(this).error(ex);
                    }

                    return count > 0;
                }));
    }

    @Override
    public void initData() throws Throwable {
        if (isExistedData()) {
            LogHelper.getLogger(this).warn("Data already existed. Skip init data.");
            return;
        }

        final String startupSql = getStartupSql(INIT_DATA__FILENAME);

        dbExecutor.doWork(conn -> {
            conn.doPreparedStatement(startupSql, preparedStatement -> {
                final int affectedRows = preparedStatement.executeUpdate();

                getLogger(this).info("inserted on rows: {}", affectedRows);
            });
        });
    }
}
