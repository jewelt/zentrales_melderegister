package de.wirvsvirus.zentralesmelderegister.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wirvsvirus.zentralesmelderegister.model.UserAccountDTO;
import de.wirvsvirus.zentralesmelderegister.security.MelderegisterAuthentication;
import de.wirvsvirus.zentralesmelderegister.security.jwt.JWTFilter;
import de.wirvsvirus.zentralesmelderegister.security.jwt.TokenProvider;
import de.wirvsvirus.zentralesmelderegister.service.UserAccountService;
import de.wirvsvirus.zentralesmelderegister.web.errors.ForbiddenException;
import de.wirvsvirus.zentralesmelderegister.web.errors.LockedException;
import de.wirvsvirus.zentralesmelderegister.web.requests.LoginRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserJWTApiController {

    private static final Logger log = LoggerFactory.getLogger(UserJWTApiController.class);

    private final TokenProvider tokenProvider;

    private final UserAccountService userAccountService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody final LoginRequest loginRequest) {
        final UserAccountDTO user = this.userAccountService.getUserByEmail(loginRequest.getEmail().trim())
                .orElseThrow(() -> {
                    log.warn("Unsuccessfull login for " + loginRequest.getEmail() + " (User not found)");
                    return new ForbiddenException("Invalid credentials");
                });

        final MelderegisterAuthentication melderegisterAuthentication = new MelderegisterAuthentication(user);
        if (this.passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            melderegisterAuthentication.setAuthenticated(true);
        } else {
            log.warn("Unsuccessfull login for " + loginRequest.getEmail() + " (wrong pw)");
            throw new ForbiddenException("Invalid credentials");
        }
        if (!user.isActivated()) {
            throw new LockedException("Der User ist nicht aktiv.");
        }
        SecurityContextHolder.getContext().setAuthentication(melderegisterAuthentication);

        if (melderegisterAuthentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equalsIgnoreCase("USER"))) {
        }

        // Token generieren
        final boolean rememberMe = loginRequest.isRememberMe();
        final String jwt = this.tokenProvider.createToken(melderegisterAuthentication, rememberMe);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        log.debug("User " + loginRequest.getEmail() + " authenticated with id " + user.getId());
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(final String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return this.idToken;
        }

        void setIdToken(final String idToken) {
            this.idToken = idToken;
        }
    }
}
