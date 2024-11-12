package tech.nmhillusion.slight_transportation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import tech.nmhillusion.slight_transportation.startup.DatabaseSchemeSeeder;

import java.util.Calendar;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

@EnableAutoConfiguration(
        exclude = {
                DataSourceAutoConfiguration.class,
                DataSourceTransactionManagerAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class
        }
)
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private DatabaseSchemeSeeder databaseSchemeSeeder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        getLogger(this).info("Started app successfully at {}", Calendar.getInstance()
                .getTime()
        );

        try {
            databaseSchemeSeeder.seed();
        } catch (Throwable ex) {
            getLogger(this).error(ex);
            System.exit(-1);
        }
    }
}
