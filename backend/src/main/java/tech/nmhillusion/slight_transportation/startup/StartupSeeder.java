package tech.nmhillusion.slight_transportation.startup;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import tech.nmhillusion.n2mix.helper.log.LogHelper;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-09
 */
public abstract class StartupSeeder implements ApplicationListener<ApplicationReadyEvent> {

    protected abstract void doSeed() throws Throwable;

    private void seed() throws Throwable {
        LogHelper.getLogger(this).info(">> seeding for seeder [{}]", getClass().getSimpleName());
        doSeed();
        LogHelper.getLogger(this).info("<< seeded for seeder [{}]", getClass().getSimpleName());
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            seed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
