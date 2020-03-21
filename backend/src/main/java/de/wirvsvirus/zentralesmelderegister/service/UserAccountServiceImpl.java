package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.UserAccountDTO;
import de.wirvsvirus.zentralesmelderegister.model.enums.Authority;
import de.wirvsvirus.zentralesmelderegister.model.jooq.Tables;
import de.wirvsvirus.zentralesmelderegister.security.SecurityUtils;
import de.wirvsvirus.zentralesmelderegister.service.util.RandomUtil;
import de.wirvsvirus.zentralesmelderegister.web.errors.*;
import de.wirvsvirus.zentralesmelderegister.web.requests.CreateUserRequest;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.wirvsvirus.zentralesmelderegister.model.jooq.Tables.*;


/**
 * Service class for managing users.
 */
@Service
@Transactional
@AllArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final Logger log = LoggerFactory.getLogger(UserAccountService.class);

    private final PasswordEncoder passwordEncoder;
    private final DSLContext dslContext;



    @Override
    @Transactional
    public UserAccountDTO registerUser(final CreateUserRequest userRequest) {

        if ("anonymousUser".equalsIgnoreCase(userRequest.getMail())) {
            throw new BadRequestException("anonymousUser is not allowed");
        }

        this.dslContext.selectFrom(USER_ACCOUNT)
                .where(DSL.lower(USER_ACCOUNT.MAIL).eq(userRequest.getMail().toLowerCase()))
                .fetch().stream().findAny().ifPresent((record) -> {
            // TODO: resend activation mail ?
            // if (!record.getActivated()) {
            // }
            throw new AlreadyExistsException(String.format("User with Email %s already exists", userRequest.getMail()));
        });

        final UserAccountDTO userAccountDAO = this.dslContext.insertInto(USER_ACCOUNT)
                .set(USER_ACCOUNT.ACTIVATED, true)//TODO aktivierung einbauen
                .set(USER_ACCOUNT.FIRST_NAME, userRequest.getFirstName())
                .set(USER_ACCOUNT.LAST_NAME, userRequest.getLastName())
                .set(USER_ACCOUNT.ACTIVATION_KEY, RandomUtil.generateActivationKey())
                .set(USER_ACCOUNT.CREATE_DATE_TIME, OffsetDateTime.now())
                .set(USER_ACCOUNT.MAIL, userRequest.getMail().trim())
                .returning().fetchOptional().map(UserAccountDTO::new).get();

        final int result = this.dslContext.insertInto(USER_AUTHORITY)
                .set(USER_AUTHORITY.USER_ACCOUNT_ID, userAccountDAO.getId())
                .set(USER_AUTHORITY.AUTHORITY, Authority.USER.name())
                .execute();
        if (result != 1) {
            throw new InternalServerErrorException(String.format("Rechte zum User %s konnten nicht gespeichert werden", userAccountDAO.getId()));
        }
        userAccountDAO.setAuthorities(Collections.singletonList(Authority.USER));
        return userAccountDAO;
    }

    @Override
    public void updateUser(final UserAccountDTO userAccount) {
        this.dslContext.update(USER_ACCOUNT)
                .set(USER_ACCOUNT.MAIL, userAccount.getMail().trim())
                .set(USER_ACCOUNT.FIRST_NAME, userAccount.getFirstName())
                .set(USER_ACCOUNT.LAST_NAME, userAccount.getLastName())
                .set(USER_ACCOUNT.ACTIVATED, userAccount.isActivated())
                .where(USER_ACCOUNT.ID.eq(userAccount.getId()))
                .execute();
    }

    @Override
    public UserAccountDTO registerAdminUser(final CreateUserRequest userRequest) {
        this.dslContext.selectFrom(USER_ACCOUNT)
                .where(DSL.lower(USER_ACCOUNT.MAIL).eq(userRequest.getMail().toLowerCase().trim()))
                .fetch().stream().findAny().ifPresent((record) -> {
            throw new AlreadyExistsException(String.format("User with Email %s already exists", userRequest.getMail()));
        });

        final String encryptedPassword = this.passwordEncoder.encode(userRequest.getPassword());
        final UserAccountDTO userAccountDAO = this.dslContext.insertInto(USER_ACCOUNT)
                .set(USER_ACCOUNT.ACTIVATED, true)
                .set(USER_ACCOUNT.FIRST_NAME, userRequest.getFirstName())
                .set(USER_ACCOUNT.LAST_NAME, userRequest.getLastName())
                .set(USER_ACCOUNT.ACTIVATION_KEY, RandomUtil.generateActivationKey())
                .set(USER_ACCOUNT.CREATE_DATE_TIME, OffsetDateTime.now())
                .set(USER_ACCOUNT.MAIL, userRequest.getMail().trim())
                .set(USER_ACCOUNT.PASSWORD_HASH, encryptedPassword)
                .returning().fetchOptional().map(UserAccountDTO::new).get();

        final int result = this.dslContext.insertInto(USER_AUTHORITY)
                .set(USER_AUTHORITY.USER_ACCOUNT_ID, userAccountDAO.getId())
                .set(USER_AUTHORITY.AUTHORITY, Authority.ADMIN.name())
                .execute();
        if (result != 1) {
            throw new InternalServerErrorException(String.format("Rechte zum User %s konnten nicht gespeichert werden", userAccountDAO.getId()));
        }
        userAccountDAO.setAuthorities(Collections.singletonList(Authority.ADMIN));
        return userAccountDAO;
    }

    @Override
    public Optional<UserAccountDTO> getUser(final long id) {
        return this.dslContext.selectFrom(USER_ACCOUNT)
                .where(USER_ACCOUNT.ID.eq(id))
                .fetchOptional()
                .map(UserAccountDTO::new)
                .map(this::loadUserRights);
    }

    @Override
    public List<UserAccountDTO> getAllAccounts() {
        return this.dslContext.selectFrom(USER_ACCOUNT)
                .fetch()
                .stream()
                .map(UserAccountDTO::new)
                .peek(this::loadUserRights)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Boolean> isActivated(final String resetKey) {
        return this.dslContext.selectFrom(USER_ACCOUNT)
                .where(USER_ACCOUNT.ACTIVATION_KEY.eq(resetKey))
                .fetchOptional().map(UserAccountDTO::new).map(UserAccountDTO::isActivated);
    }

    @Override
    public Optional<Boolean> isResetting(final String resetKey) {
        return this.dslContext.selectFrom(USER_ACCOUNT)
                .where(USER_ACCOUNT.RESET_KEY.eq(resetKey))
                .fetchOptional().map(UserAccountDTO::new)
                .filter(user -> user.getResetDateTime().isAfter(Instant.now().minusSeconds(14L * 86400L))) // 2 Wochen
                .map(UserAccountDTO::isActivated);
    }

    @Override
    @Transactional
    public void deleteUser(final Long userAccountId) {
        this.dslContext.deleteFrom(USER_AUTHORITY)
                .where(USER_AUTHORITY.USER_ACCOUNT_ID.eq(userAccountId))
                .execute();
        final int affectedRows = this.dslContext.deleteFrom(USER_ACCOUNT)
                .where(USER_ACCOUNT.ID.eq(userAccountId))
                .execute();
        if (affectedRows == 0) {
            throw new ResourceNotFoundException("Could not find user");
        } else if (affectedRows > 1) {
            throw new InternalServerErrorException("More than one row affected");
        }
    }

    @Override
    public Optional<UserAccountDTO> getUserByEmail(final String email) {
        return this.dslContext.selectFrom(USER_ACCOUNT)
                .where(USER_ACCOUNT.MAIL.eq(email))
                .fetchOptional()
                .map(UserAccountDTO::new)
                .map(this::loadUserRights);
    }

    private UserAccountDTO loadUserRights(final UserAccountDTO user) {
        final List<Authority> authorities = this.dslContext.selectFrom(USER_AUTHORITY)
                .where(USER_AUTHORITY.USER_ACCOUNT_ID.eq(user.getId()))
                .fetch(USER_AUTHORITY.AUTHORITY, String.class)
                .stream().map(Authority::valueOf).collect(Collectors.toList());
        user.setAuthorities(authorities);
        return user;
    }

    @Override
    public Optional<UserAccountDTO> getCurrentUser() {
        return this.getUser(SecurityUtils.getCurrentUserId().orElseThrow(() -> new InternalServerErrorException("User is not logged in")));
    }

    @Override
    public Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId().orElseThrow(() -> new InternalServerErrorException("User is not logged in"));
    }

}
