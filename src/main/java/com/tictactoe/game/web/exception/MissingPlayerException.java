package com.tictactoe.game.web.exception;

import java.security.SecureRandom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The <code>MissingPlayerException</code> class is custom exception joining in game where player is missing.
 *
 * @author Bosko Mijin
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public final class MissingPlayerException extends IllegalStateException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = new SecureRandom().nextLong();

    /**
     * Instantiates a new MissingPlayerException exception.
     *
     * @param message - the message
     */
    public MissingPlayerException(final String message) {
        super(message);
    }
}
