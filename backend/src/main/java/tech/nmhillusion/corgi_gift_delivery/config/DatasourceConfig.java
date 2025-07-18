package tech.nmhillusion.corgi_gift_delivery.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import tech.nmhillusion.corgi_gift_delivery.Application;
import tech.nmhillusion.n2mix.constant.CommonConfigDataSourceValue;
import tech.nmhillusion.n2mix.exception.InvalidArgument;
import tech.nmhillusion.n2mix.helper.database.config.DataSourceProperties;
import tech.nmhillusion.n2mix.helper.database.config.DatabaseConfigHelper;
import tech.nmhillusion.n2mix.helper.database.query.DatabaseHelper;
import tech.nmhillusion.n2mix.helper.storage.FileHelper;
import tech.nmhillusion.n2mix.util.StringUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-09
 */
@Configuration
public class DatasourceConfig {
    private static final Pattern H2_DB_JDBC_URL_PATTERN = Pattern.compile("^jdbc:h2:file:(.+?)$", Pattern.CASE_INSENSITIVE);

    private final DataSourceProperties dataSourceProperties;

    public DatasourceConfig(@Value("${database.jdbcUrl}")
                            String dbJdbcUrl,
                            @Value("${database.username}")
                            String dbUsername,
                            @Value("${database.password}")
                            String dbPassword,
                            @Value("${database.driverClassName}")
                            String dbDriverClassName) {
        deleteExistedH2Db(dbJdbcUrl);

        dataSourceProperties = initDatasourceProperties(dbJdbcUrl, dbUsername, dbPassword, dbDriverClassName);
    }

    private void deleteExistedH2Db(String dbJdbcUrl) {
        getLogger(this).info("delete existed h2 db of jdbc url: {}", dbJdbcUrl);
        if (dbJdbcUrl.contains("h2")) {
            final Matcher matcher = H2_DB_JDBC_URL_PATTERN.matcher(StringUtil.trimWithNull(dbJdbcUrl));
            if (matcher.find()) {
                final String dbFilePathText = matcher.group(1);
                final Path dbFilePath = Path.of(dbFilePathText);
                final Path dbFolderPath = dbFilePath.getParent();

                getLogger(this).info("delete existed h2 db, db folder path = {}", dbFolderPath);

                if (Files.exists(dbFolderPath)) {
                    FileHelper.recursiveDeleteFolder(
                            dbFolderPath.toFile()
                    );
                }
            }
        }
    }

    private DataSourceProperties initDatasourceProperties(String dbJdbcUrl, String dbUsername, String dbPassword, String dbDriverClassName) {
        final DataSourceProperties dataSourceProperties;
        final CommonConfigDataSourceValue.DataSourceConfig dataSourceConfig = new CommonConfigDataSourceValue
                .DataSourceConfig()
                .setDriverClass(dbDriverClassName)
                .setDialectClass("org.hibernate.dialect.H2Dialect");

        dataSourceProperties = DataSourceProperties.generateFromDefaultDataSourceProperties(
                "slitran-db"
                , dataSourceConfig
                , dbJdbcUrl
                , dbUsername
                , dbPassword
        );
        return dataSourceProperties;
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
                                             @Autowired @Qualifier("mainSessionFactory") SessionFactory sessionFactory) throws InvalidArgument {
        return new DatabaseHelper(dataSource, sessionFactory);
    }

//    @Bean("entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean initEntityManagerFactory(@Autowired SessionFactory sessionFactory) {
//        final LocalContainerEntityManagerFactoryBean emf = DatabaseConfigHelper
//                .INSTANCE
//                .generateEntityManagerFactoryBean(
//                        "slitran-entity-manager-factory"
//                        , dataSourceProperties
//                        , Application.class
//                );
//
//        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//        return emf;
//    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final DataSource dataSource = initDatasource();

        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(Application.class.getPackage().getName()); // Replace with your package
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        // Add Hibernate properties as needed
        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        properties.setProperty("showSql", "true");
        em.setJpaProperties(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }
}
