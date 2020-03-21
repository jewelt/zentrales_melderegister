package de.wirvsvirus.zentralesmelderegister.web.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(final String message) {
        super(message);
    }
}
