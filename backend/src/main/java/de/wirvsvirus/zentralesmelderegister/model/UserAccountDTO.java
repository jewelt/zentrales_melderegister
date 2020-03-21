package de.wirvsvirus.zentralesmelderegister.model;

import de.wirvsvirus.zentralesmelderegister.model.enums.Authority;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.UserAccountRecord;

import java.time.Instant;
import java.util.List;

public class UserAccountDTO {

    private long id;
    private String passwordHash;
    private String mail;
    private boolean activated;
    private String activationKey;
    private String resetKey;
    private Instant resetDateTime;
    private Instant creationDateTime;
    private String firstName;
    private String lastName;

    private List<Authority> authorities;

    public UserAccountDTO() {

    }


    public UserAccountDTO(final UserAccountRecord userAccountRecord) {
        this.setId(userAccountRecord.getId());
        this.setPasswordHash(userAccountRecord.getPasswordHash());
        this.setMail(userAccountRecord.getMail());
        this.setActivated(userAccountRecord.getActivated());
        this.setActivationKey(userAccountRecord.getActivationKey());
        this.setResetKey(userAccountRecord.getResetKey());
        if (userAccountRecord.getResetDate() != null) {
            this.setResetDateTime(userAccountRecord.getResetDate().toInstant());
        }
        this.setCreationDateTime(userAccountRecord.getCreateDateTime().toInstant());
        this.setFirstName(userAccountRecord.getFirstName());
        this.setLastName(userAccountRecord.getLastName());
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(final String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(final String mail) {
        this.mail = mail;
    }

    public boolean isActivated() {
        return this.activated;
    }

    public void setActivated(final boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return this.activationKey;
    }

    public void setActivationKey(final String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return this.resetKey;
    }

    public void setResetKey(final String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDateTime() {
        return this.resetDateTime;
    }

    public void setResetDateTime(final Instant resetDateTime) {
        this.resetDateTime = resetDateTime;
    }

    public Instant getCreationDateTime() {
        return this.creationDateTime;
    }

    public void setCreationDateTime(final Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public List<Authority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(final List<Authority> authorities) {
        this.authorities = authorities;
    }
}
