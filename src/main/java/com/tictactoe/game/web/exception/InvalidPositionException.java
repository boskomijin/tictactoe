package com.tictactoe.game.web.exception;

import java.security.SecureRandom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The <code>InvalidPositionException</code> class is custom exception for putting illegal playing mark (different that
 * it is already allowed according to the regular expressions defined in game configuration).
 *
 * @author Bosko Mijin
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class InvalidPositionException extends IllegalStateException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = new SecureRandom().nextLong();

    /**
     * Instantiates a new GameOverException exception.
     *
     * @param message - the message
     */
    public InvalidPositionException(final String message) {
        super(message);
    }
}
