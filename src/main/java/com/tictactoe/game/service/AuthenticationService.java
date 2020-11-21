package com.tictactoe.game.service;

import com.tictactoe.game.web.exception.AuthenticationException;

/**
 * The <code>AuthenticationService</code> interface defines all the business behaviors for authentication functionality.
 *
 * @author Bosko Mijin
 */
public interface AuthenticationService {

    /**
     * The <code>authenticate</code> method authenticates player according gameId and token.
     *
     * @param gameId - The gameId of the game which player intends to play.
     * @param token - The player's token.
     * @throws AuthenticationException - The exception if user isn't authenticated.
     */
    public void authenticate(int gameId, String token) throws AuthenticationException;
}
