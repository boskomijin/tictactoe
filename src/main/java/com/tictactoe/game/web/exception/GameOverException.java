package com.tictactoe.game.web.exception;

import java.security.SecureRandom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The <code>GameOverException</code> class is custom exception for illegal playing finished game.
 *
 * @author Bosko Mijin
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class GameOverException extends IllegalStateException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = new SecureRandom().nextLong();

    /**
     * Instantiates a new GameOverException exception.
     *
     * @param message - the message
     */
    public GameOverException(final String message) {
        super(message);
    }
}
