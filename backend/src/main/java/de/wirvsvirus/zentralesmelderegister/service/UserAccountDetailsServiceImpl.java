package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.UserAccountDTO;
import org.jooq.DSLContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.wirvsvirus.zentralesmelderegister.model.jooq.Tables.USER_ACCOUNT;


@Service
public class UserAccountDetailsServiceImpl implements UserAccountDetailsService {
    private final DSLContext dslContext;

    public UserAccountDetailsServiceImpl(final DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private Optional<UserAccountDTO> getUser(final long id) {
        return this.dslContext.selectFrom(USER_ACCOUNT)
                .where(USER_ACCOUNT.ID.eq(id))
                .fetchOptional().map(UserAccountDTO::new);
    }

    @Override
    public UserDetails loadUserByUsername(final String s) throws UsernameNotFoundException {
        final long id;
        try {
            id = Long.parseLong(s);
        } catch (final NumberFormatException e) {
            throw new UsernameNotFoundException("Username " + s + " is invalid");
        }
        final Optional<UserAccountDTO> userOptional = this.getUser(id);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Could not find user " + s);
        }
        final UserAccountDTO user = userOptional.get();
        final List<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(String.valueOf(user.getId()), user.getPasswordHash(), authorities);
    }
}
