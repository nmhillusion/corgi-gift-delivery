package tech.nmhillusion.corgi_gift_delivery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-03
 */
@Configuration
public class AppResourceConfig implements WebMvcConfigurer {
    protected static final String[] RESOURCE_LOCATIONS = {"classpath:/static/", "classpath:/corgi-dist/"};
    protected static final String[] RESOURCE_PATHS = {"/*.js", "/*.css", "/*.svg", "/*.png", "*.ico"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration resourceHandlerRegistration = appendResourceHandler(registry);
        initializeResourceHandler(resourceHandlerRegistration);
    }

    protected ResourceHandlerRegistration appendResourceHandler(ResourceHandlerRegistry registry) {
        return registry.addResourceHandler(RESOURCE_PATHS);
    }

    protected void initializeResourceHandler(ResourceHandlerRegistration resourceHandlerRegistration) {
        resourceHandlerRegistration.addResourceLocations(RESOURCE_LOCATIONS).setCacheControl(getCacheControl());
    }

    protected CacheControl getCacheControl() {
        return CacheControl.maxAge(1, TimeUnit.DAYS).cachePublic();
    }
}
