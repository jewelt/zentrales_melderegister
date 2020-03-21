package de.wirvsvirus.zentralesmelderegister.web.requests;

public class CreateUserRequest {

    // TODO: Size Validation im Request, in welchem das Passwort gesetzt wird
    public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 100;


    private String mail;
    private String firstName;
    private String lastName;
    private String password;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(final String mail) {
        this.mail = mail;
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


    @Override
    public String toString() {
        return "CreateUserRequest{" +
                "mail='" + this.mail + '\'' +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                '}';
    }


}
