package tech.nmhillusion.corgi_gift_delivery;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import tech.nmhillusion.n2mix.annotation.EnableN2mix;

import java.awt.*;
import java.util.Calendar;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;
import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

@EnableAutoConfiguration(
        exclude = {
                DataSourceAutoConfiguration.class,
                DataSourceTransactionManagerAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class
        }
)
@EnableN2mix
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        getLogger(this).info("Started app successfully at {}", Calendar.getInstance()
                .getTime()
        );

        if (Desktop.isDesktopSupported()) {
            final Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new java.net.URI("http://localhost:8080"));
                } catch (Exception e) {
                    getLogger(this).error(e);
                    getLogger(this).error("Error when open browser. " + e.getMessage());
                }
            } else {
                getLogger(this).warn("Desktop is not supported opening browser");
            }
        } else {
            getLogger(this).warn("Desktop is not supported");
        }
    }
}
