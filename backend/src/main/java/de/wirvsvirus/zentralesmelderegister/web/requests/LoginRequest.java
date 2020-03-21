package de.wirvsvirus.zentralesmelderegister.web.requests;

public class LoginRequest {
    private String email;
    private boolean rememberMe;
    private String password;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public boolean isRememberMe() {
        return this.rememberMe;
    }

    public void setRememberMe(final boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + this.email + '\'' +
                ", rememberMe=" + this.rememberMe +
                ", password='XXX'" +
                '}';
    }
}
