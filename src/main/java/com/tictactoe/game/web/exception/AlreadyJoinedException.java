package com.tictactoe.game.web.exception;

import java.security.SecureRandom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The <code>AlreadyJoinedException</code> class is custom exception for illegal joining into the game.
 *
 * @author Bosko Mijin
 */
@ResponseStatus(HttpStatus.CONFLICT)
public final class AlreadyJoinedException extends IllegalStateException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = new SecureRandom().nextLong();

    /**
     * Instantiates a new AlreadyJoinedException exception.
     *
     * @param message - the message
     */
    public AlreadyJoinedException(final String message) {
        super(message);
    }
}
