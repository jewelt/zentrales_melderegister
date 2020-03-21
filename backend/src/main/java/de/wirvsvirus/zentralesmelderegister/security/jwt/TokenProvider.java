package de.wirvsvirus.zentralesmelderegister.security.jwt;

import de.wirvsvirus.zentralesmelderegister.configuration.properties.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private Key key;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    private final JwtProperties config;

    public TokenProvider(final JwtProperties config) {
        this.config = config;
    }

    @PostConstruct
    public void init() {
        final byte[] keyBytes;
        final String secret = this.config.getSecret();
        keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds = 1000 * this.config.getTokenValidity();
        this.tokenValidityInMillisecondsForRememberMe = 1000 * this.config.getTokenValidityRememberMe();
    }

    public String createToken(final Authentication authentication, final boolean rememberMe) {
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        final long now = (new Date()).getTime();
        final Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(this.key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(final String token) {
        final Claims claims = Jwts.parser()
                .setSigningKey(this.key)
                .parseClaimsJws(token)
                .getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        final User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(final String authToken) {
        try {
            Jwts.parser().setSigningKey(this.key).parseClaimsJws(authToken);
            return true;
        } catch (final io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            this.log.info("Invalid JWT signature.");
            this.log.trace("Invalid JWT signature trace: {}", e);
        } catch (final ExpiredJwtException e) {
            this.log.info("Expired JWT token.");
            this.log.trace("Expired JWT token trace: {}", e);
        } catch (final UnsupportedJwtException e) {
            this.log.info("Unsupported JWT token.");
            this.log.trace("Unsupported JWT token trace: {}", e);
        } catch (final IllegalArgumentException e) {
            this.log.info("JWT token compact of handler are invalid.");
            this.log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }
}
