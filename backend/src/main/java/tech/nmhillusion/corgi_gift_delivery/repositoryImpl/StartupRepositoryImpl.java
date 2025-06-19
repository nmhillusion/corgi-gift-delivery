package tech.nmhillusion.corgi_gift_delivery.repositoryImpl;

import org.springframework.stereotype.Repository;
import tech.nmhillusion.n2mix.helper.database.query.DatabaseExecutor;
import tech.nmhillusion.n2mix.helper.database.query.DatabaseHelper;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.corgi_gift_delivery.helper.UnixPathHelper;
import tech.nmhillusion.corgi_gift_delivery.provider.SqlScriptProvider;
import tech.nmhillusion.corgi_gift_delivery.repository.StartupRepository;

import java.io.IOException;
import java.sql.ResultSet;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-12
 */
@Repository
public class StartupRepositoryImpl implements StartupRepository {
    private static final String FILENAME__INIT_DB_SCHEMA = "init-db-scheme.sql";
    private static final String FILENAME__INIT_DATA = "init-data.sql";
    private static final String FILENAME__CHECK_EXISTED_DATA = "check-existed-data.sql";

    private final DatabaseExecutor dbExecutor;
    private final SqlScriptProvider sqlScriptProvider;
    private final UnixPathHelper unixPathHelper;

    public StartupRepositoryImpl(DatabaseHelper databaseHelper, SqlScriptProvider sqlScriptProvider, UnixPathHelper unixPathHelper) {
        this.dbExecutor = databaseHelper.getExecutor();
        this.sqlScriptProvider = sqlScriptProvider;
        this.unixPathHelper = unixPathHelper;
    }

    private String getStartupSql(String sqlFilename) throws IOException {
        return sqlScriptProvider.getSqlScript(
                unixPathHelper.joinPaths("startup", sqlFilename)
        );
    }

    @Override
    public void initDatabaseSchema() throws Throwable {
        final String startupSql = getStartupSql(FILENAME__INIT_DB_SCHEMA);

        dbExecutor.doWork(conn -> {
            conn.doPreparedStatement(startupSql, preparedStatement -> {
                final boolean executed = preparedStatement.execute();

                getLogger(this).info("executed result: {}", executed);
            });
        });
    }

    private boolean isExistedData() throws Throwable {
        final String checkExistedDataSql = getStartupSql(FILENAME__CHECK_EXISTED_DATA);

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

        final String startupSql = getStartupSql(FILENAME__INIT_DATA);

        dbExecutor.doWork(conn -> {
            conn.doPreparedStatement(startupSql, preparedStatement -> {
                final int affectedRows = preparedStatement.executeUpdate();

                getLogger(this).info("inserted on rows: {}", affectedRows);
            });
        });
    }
}
