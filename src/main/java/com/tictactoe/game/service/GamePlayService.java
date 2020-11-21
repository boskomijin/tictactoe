package com.tictactoe.game.service;

import com.tictactoe.game.web.exception.AuthenticationException;

/**
 * The <code>GamePlayService</code> interface defines all the business behaviors for operations related to game playing.
 *
 * @author Bosko Mijin
 */
public interface GamePlayService {

    /**
     * The <code>playGame</code> method defines the playing game functionality.
     *
     * @param gameId - The id of the game.
     * @param playerMark - The action (placing mark) which player intends to play.
     * @param token - The token of the user which was performed playing action.
     * @return boolean - If placing mark is performed returns true, otherwise false.
     * @throws AuthenticationException - The authentication exception if the user is not authorized.
     */
    public boolean playGame(int gameId, String playerMark, String token) throws AuthenticationException;

    /**
     * The <code>obtainGameStatus</code> method defines game status obtaining functionality.
     *
     * @param gameId - The id of the game.
     * @param token - The token of the user which was performed playing action.
     * @return String - The message of the status.
     * @throws AuthenticationException - The authentication exception if the user is not authorized.
     */
    public String obtainGameStatus(int gameId, String token) throws AuthenticationException;
}
