package de.wirvsvirus.zentralesmelderegister.configuration.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "melderegister.jwt")
public class JwtProperties {

    private static final Logger log = LoggerFactory.getLogger(JwtProperties.class);

    private long tokenValidity;
    private long tokenValidityRememberMe;
    private String secret;

    @PostConstruct
    private void init() {
        log.debug("Using JwtProperties: " + this.toString());
    }

    public long getTokenValidity() {
        return this.tokenValidity;
    }

    public void setTokenValidity(final long tokenValidity) {
        this.tokenValidity = tokenValidity;
    }

    public long getTokenValidityRememberMe() {
        return this.tokenValidityRememberMe;
    }

    public void setTokenValidityRememberMe(final long tokenValidityRememberMe) {
        this.tokenValidityRememberMe = tokenValidityRememberMe;
    }

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(final String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "{" +
                "tokenValidity=" + this.tokenValidity +
                ", tokenValidityRememberMe=" + this.tokenValidityRememberMe +
                ", secret='" + this.getSecretStringRepresentation() + '\'' +
                '}';
    }

    private String getSecretStringRepresentation() {
        if (this.secret != null) {
            return this.secret.substring(0, 3) + "{" + (this.secret.length() - 3) + "}";
        } else {
            return "<NULL>";
        }
    }

}
