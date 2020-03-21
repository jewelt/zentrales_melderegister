package de.wirvsvirus.zentralesmelderegister.security;

import de.wirvsvirus.zentralesmelderegister.model.UserAccountDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MelderegisterAuthentication implements Authentication {

    private final MelderegisterUserDetails melderegisterUserDetails;

    public MelderegisterAuthentication(final UserAccountDTO user) {
        this.melderegisterUserDetails = new MelderegisterUserDetails(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.melderegisterUserDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return this.melderegisterUserDetails;
    }

    @Override
    public Object getPrincipal() {
        return this.melderegisterUserDetails.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return this.melderegisterUserDetails.isAccountNonExpired() && this.melderegisterUserDetails.isAccountNonLocked() &&
                this.melderegisterUserDetails.isCredentialsNonExpired() && this.melderegisterUserDetails.isEnabled();
    }

    @Override
    public void setAuthenticated(final boolean b) throws IllegalArgumentException {
        this.melderegisterUserDetails.setCredentialsNonExpired(b);
    }

    @Override
    public String getName() {
        return this.melderegisterUserDetails.getUsername();
    }

    private class MelderegisterUserDetails implements UserDetails {
        private final List<SimpleGrantedAuthority> authorities;
        private final String passwordHash;
        private final String username;
        private final boolean accountNonExpired = true;
        private final boolean accountNonLocked = true;
        private boolean credentialsNonExpired = false;
        private final boolean enabled = true;

        MelderegisterUserDetails(final UserAccountDTO user) {
            this.username = String.valueOf(user.getId());
            this.passwordHash = user.getPasswordHash();
            this.authorities = user.getAuthorities().stream()
                    .map(Enum::name)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.authorities;
        }

        @Override
        public String getPassword() {
            return this.passwordHash;
        }

        @Override
        public String getUsername() {
            return this.username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return this.accountNonExpired;
        }

        @Override
        public boolean isAccountNonLocked() {
            return this.accountNonLocked;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return this.credentialsNonExpired;
        }

        @Override
        public boolean isEnabled() {
            return this.enabled;
        }

        private void setCredentialsNonExpired(final boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
        }
    }
}
