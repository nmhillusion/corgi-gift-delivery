package tech.nmhillusion.slight_transportation.startup;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-09
 */
@Component
public class DatabaseSchemeSeeder extends StartupSeeder {
    private final ApplicationContext applicationContext;

    public DatabaseSchemeSeeder(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void doSeed() {
        getLogger(this).info("Seed on application[{}]", applicationContext);
    }

}
