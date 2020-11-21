package com.tictactoe.game.web.exception;

import java.security.SecureRandom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.webjars.NotFoundException;

/**
 * The <code>MissingGameException</code> class is custom exception joining into the non-existing game.
 *
 * @author Bosko Mijin
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public final class MissingGameException extends NotFoundException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = new SecureRandom().nextLong();

    /**
     * Instantiates a new MissingGameException exception.
     *
     * @param message - the message
     */
    public MissingGameException(final String message) {
        super(message);
    }
}
