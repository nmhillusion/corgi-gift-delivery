package tech.nmhillusion.corgi_gift_delivery.startup;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import tech.nmhillusion.corgi_gift_delivery.repository.StartupRepository;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-09
 */
@Component
public class DatabaseSchemeSeeder extends StartupSeeder {
    private final ApplicationContext applicationContext;
    private final StartupRepository startupRepo;

    public DatabaseSchemeSeeder(ApplicationContext applicationContext,
                                StartupRepository startupRepository) {
        this.applicationContext = applicationContext;
        this.startupRepo = startupRepository;
    }

    @Override
    protected void doSeed() throws Throwable {
        getLogger(this).info("Seed on application[{}]", applicationContext);

        getLogger(this).info("init database scheme");
        startupRepo.initDatabaseSchema();

        getLogger(this).info("delete old data");
        startupRepo.deleteOldData();

        getLogger(this).info("init data for app");
        startupRepo.initData();
    }

}
