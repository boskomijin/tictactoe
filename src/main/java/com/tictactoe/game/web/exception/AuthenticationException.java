package com.tictactoe.game.web.exception;

import java.security.SecureRandom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The <code>AuthenticationException</code> class is custom authentication exception for unauthenticated players.
 *
 * @author Bosko Mijin
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public final class AuthenticationException extends IllegalAccessException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = new SecureRandom().nextLong();

    /**
     * Instantiates a new Authentication exception.
     *
     * @param message - the message
     */
    public AuthenticationException(final String message) {
        super(message);
    }
}
