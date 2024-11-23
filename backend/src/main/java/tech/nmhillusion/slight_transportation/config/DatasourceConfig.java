package tech.nmhillusion.slight_transportation.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import tech.nmhillusion.n2mix.constant.CommonConfigDataSourceValue;
import tech.nmhillusion.n2mix.exception.InvalidArgument;
import tech.nmhillusion.n2mix.helper.database.config.DataSourceProperties;
import tech.nmhillusion.n2mix.helper.database.config.DatabaseConfigHelper;
import tech.nmhillusion.n2mix.helper.database.query.DatabaseHelper;
import tech.nmhillusion.slight_transportation.Application;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

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
                "slitran-db"
                , dataSourceConfig
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
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
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
