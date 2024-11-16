package tech.nmhillusion.slight_transportation.domains.delivery.deliveryStatus;

import org.springframework.stereotype.Repository;
import tech.nmhillusion.n2mix.helper.database.query.DatabaseExecutor;
import tech.nmhillusion.n2mix.helper.database.query.DatabaseHelper;
import tech.nmhillusion.n2mix.util.ExceptionUtil;
import tech.nmhillusion.slight_transportation.entity.business.DeliveryStatusEntity;
import tech.nmhillusion.slight_transportation.helper.UnixPathHelper;
import tech.nmhillusion.slight_transportation.provider.SqlScriptProvider;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-16
 */
@Repository
public class DeliveryStatusRepositoryImpl implements DeliveryStatusRepository {
    private static final String FILENAME__LIST_QUERY = "list.sql";
    private static final String FILENAME__GET_BY_ID_QUERY = "getById.sql";

    private final UnixPathHelper unixPathHelper;
    private final DatabaseExecutor databaseExecutor;
    private final SqlScriptProvider sqlScriptProvider;

    public DeliveryStatusRepositoryImpl(UnixPathHelper unixPathHelper, DatabaseHelper databaseHelper, SqlScriptProvider sqlScriptProvider) {
        this.unixPathHelper = unixPathHelper;
        this.databaseExecutor = databaseHelper.getExecutor();
        this.sqlScriptProvider = sqlScriptProvider;
    }

    private String getSqlScript(String filename) throws IOException {
        return sqlScriptProvider.getSqlScript(
                unixPathHelper.joinPaths(
                        "delivery-status", filename
                )
        );
    }

    private DeliveryStatusEntity getDeliveryStatusById(String statusId) {
        try {
            final String query_ = getSqlScript(FILENAME__GET_BY_ID_QUERY);

            return databaseExecutor.doReturningWork(conn ->
                    conn.doReturningPreparedStatement(query_, ps_ -> {
                        ps_.setString(1, statusId);

                        final ResultSet resultSet = ps_.executeQuery();

                        if (resultSet.next()) {
                            return new DeliveryStatusEntity()
                                    .setStatusId(resultSet.getString("status_id"))
                                    .setStatusName(resultSet.getString("status_name"))
                                    ;
                        }

                        throw new RuntimeException("Delivery status not found by id: %s".formatted(statusId));
                    }));
        } catch (Throwable ex) {
            throw ExceptionUtil.throwException(ex);
        }
    }

    @Override
    public List<DeliveryStatusEntity> list() {
        try {
            final String query_ = getSqlScript(FILENAME__LIST_QUERY);

            return databaseExecutor.doReturningWork(conn ->
                            conn.doReturningPreparedStatement(query_, ps_ -> {
                                final ResultSet resultSet = ps_.executeQuery();

                                final List<String> statusIdList = new ArrayList<>();

                                while (resultSet.next()) {
                                    statusIdList.add(resultSet.getString("status_id"));
                                }

                                return statusIdList;
                            }))
                    .stream()
                    .map(this::getDeliveryStatusById)
                    .toList()
                    ;
        } catch (Throwable ex) {
            throw ExceptionUtil.throwException(ex);
        }
    }
}
