package tech.nmhillusion.slight_transportation.startup;

import tech.nmhillusion.n2mix.helper.log.LogHelper;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-09
 */
public abstract class StartupSeeder {

    protected abstract void doSeed() throws Throwable;

    public final void seed() throws Throwable {
        LogHelper.getLogger(this).info(">> seeding for seeder [{}]", getClass().getSimpleName());
        doSeed();
        LogHelper.getLogger(this).info("<< seeded for seeder [{}]", getClass().getSimpleName());
    }
}
