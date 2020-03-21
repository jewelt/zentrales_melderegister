package de.wirvsvirus.zentralesmelderegister.configuration;

import de.wirvsvirus.zentralesmelderegister.configuration.properties.CorsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.nio.charset.StandardCharsets;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    private final Environment env;
    private final CorsProperties corsConfig;


    public WebConfigurer(final Environment env, final CorsProperties corsConfig) {
        this.env = env;
        this.corsConfig = corsConfig;
    }

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        if (this.env.getActiveProfiles().length != 0) {
            this.log.info("Web application configuration, using profiles: {}", (Object[]) this.env.getActiveProfiles());
        }
        this.log.info("Web application fully configured");
    }

    /**
     * Customize the Servlet engine: Mime types, the document root, the cache.
     */
    @Override
    public void customize(final WebServerFactory server) {
        this.setMimeMappings(server);
    }

    private void setMimeMappings(final WebServerFactory server) {
        if (server instanceof ConfigurableServletWebServerFactory) {
            final MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
            // IE issue, see https://github.com/jhipster/generator-jhipster/pull/711
            mappings.add("html", MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase());
            // CloudFoundry issue, see https://github.com/cloudfoundry/gorouter/issues/64
            mappings.add("json", MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase());
            final ConfigurableServletWebServerFactory servletWebServer = (ConfigurableServletWebServerFactory) server;
            servletWebServer.setMimeMappings(mappings);
        }
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        if (this.corsConfig.getAllowedOrigins() != null && !this.corsConfig.getAllowedOrigins().isEmpty()) {
            this.log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", this.corsConfig);
            source.registerCorsConfiguration("/management/**", this.corsConfig);
            source.registerCorsConfiguration("/v2/api-docs", this.corsConfig);
        }
        return new CorsFilter(source);
    }
}
