package tech.nmhillusion.slight_transportation.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.nmhillusion.n2mix.constant.CommonConfigDataSourceValue;
import tech.nmhillusion.n2mix.exception.InvalidArgument;
import tech.nmhillusion.n2mix.helper.database.config.DataSourceProperties;
import tech.nmhillusion.n2mix.helper.database.config.DatabaseConfigHelper;
import tech.nmhillusion.n2mix.helper.database.query.DatabaseHelper;

import javax.sql.DataSource;
import java.io.IOException;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-09
 */
@Configuration
public class DatasourceConfig {

    private final DataSourceProperties dataSourceProperties;

    public DatasourceConfig() {
        final CommonConfigDataSourceValue.DataSourceConfig dataSourceConfig = new CommonConfigDataSourceValue
                .DataSourceConfig()
                .setDriverClass("org.h2.Driver")
                .setDialectClass("org.hibernate.dialect.H2Dialect");

        final String dbUrl = "jdbc:h2:file:./dbdata/slitran";
        final String user = "sa"; // Default user
        final String password = ""; // Default password

        dataSourceProperties = DataSourceProperties.generateFromDefaultDataSourceProperties(
                dataSourceConfig
                , dbUrl
                , user
                , password
        );
    }

    @Bean("mainDatasource")
    public DataSource initDatasource() {
        getLogger(this).info("initialize for DataSource");
        return DatabaseConfigHelper.INSTANCE
                .generateDataSource(
                        dataSourceProperties
                );
    }

    @Bean("mainSessionFactory")
    public SessionFactory initSessionFactory(@Autowired DataSource dataSource) throws IOException {
        getLogger(this).info("initialize for SessionFactory");
        return DatabaseConfigHelper.INSTANCE
                .generateSessionFactory(
                        dataSourceProperties
                        , dataSource
                );
    }

    @Bean("databaseHelper")
    public DatabaseHelper initDatabaseHelper(@Autowired DataSource dataSource,
                                             @Autowired SessionFactory sessionFactory) throws InvalidArgument {
        return new DatabaseHelper(dataSource, sessionFactory);
    }
}
