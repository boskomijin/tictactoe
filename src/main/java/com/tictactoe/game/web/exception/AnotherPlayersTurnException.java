package com.tictactoe.game.web.exception;

import java.security.SecureRandom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The <code>AnotherPlayersTurnException</code> class is custom exception for illegal placing mark - not on schedule.
 *
 * @author Bosko Mijin
 */
@ResponseStatus(HttpStatus.CONFLICT)
public final class AnotherPlayersTurnException extends IllegalStateException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = new SecureRandom().nextLong();

    /**
     * Instantiates a new AlreadyJoinedException exception.
     *
     * @param message - the message
     */
    public AnotherPlayersTurnException(final String message) {
        super(message);
    }
}
