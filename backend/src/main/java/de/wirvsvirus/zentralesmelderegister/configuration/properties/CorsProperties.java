package de.wirvsvirus.zentralesmelderegister.configuration.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "melderegister.cors")
public class CorsProperties extends CorsConfiguration {
    private static final Logger log = LoggerFactory.getLogger(CorsProperties.class);

    @PostConstruct
    public void init() {
        log.info("Using Cors Configuration: " + this.toString());
    }

}
