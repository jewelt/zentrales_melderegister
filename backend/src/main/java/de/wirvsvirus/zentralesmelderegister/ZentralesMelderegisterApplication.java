package de.wirvsvirus.zentralesmelderegister;

import de.wirvsvirus.zentralesmelderegister.configuration.properties.CorsProperties;
import de.wirvsvirus.zentralesmelderegister.configuration.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties({JwtProperties.class, CorsProperties.class})
public class ZentralesMelderegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZentralesMelderegisterApplication.class, args);
	}

}
