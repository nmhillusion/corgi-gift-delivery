package tech.nmhillusion.slight_transportation.config;

import jakarta.servlet.ServletContext;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import tech.nmhillusion.n2mix.helper.log.LogHelper;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import static java.net.URLDecoder.decode;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-03
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {
    private final Environment env;


    public WebConfigurer(Environment env) {
        this.env = env;
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        if (env.getActiveProfiles().length != 0) {
            LogHelper.getLogger(this).info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }

        LogHelper.getLogger(this).info("Web application fully configured");
    }

    /**
     * Customize the Servlet engine: Mime types, the document root, the cache.
     */
    @Override
    public void customize(WebServerFactory server) {
        // When running in an IDE or with ./mvnw spring-boot:run, set location of the static web assets.
        setLocationForStaticAssets(server);
    }

    private void setLocationForStaticAssets(WebServerFactory server) {
        if (server instanceof ConfigurableServletWebServerFactory servletWebServer) {
            File root;
            String prefixPath = resolvePathPrefix();
            root = new File(prefixPath + "target/classes/static/");
            if (root.exists() && root.isDirectory()) {
                LogHelper.getLogger(this).info("set document root for {}", root);
                servletWebServer.setDocumentRoot(root);
            }
        }
    }

    /**
     * Resolve path prefix to static resources.
     */
    private String resolvePathPrefix() {
        String fullExecutablePath = decode(this.getClass().getResource("").getPath(), StandardCharsets.UTF_8);
        String rootPath = Paths.get(".").toUri().normalize().getPath();
        String extractedPath = fullExecutablePath.replace(rootPath, "");
        int extractionEndIndex = extractedPath.indexOf("target/");
        if (extractionEndIndex <= 0) {
            return "";
        }
        final String pathPrefix = extractedPath.substring(0, extractionEndIndex);
        LogHelper.getLogger(this).info("resolved pathPrefix = {}", pathPrefix);
        return pathPrefix;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        LogHelper.getLogger(this).debug("Registering CORS filter");
        source.registerCorsConfiguration("/api/**"
                , new CorsConfiguration()
                        .applyPermitDefaultValues()
        );
        return new CorsFilter(source);
    }
}
