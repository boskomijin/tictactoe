package com.tictactoe.game.service;

import java.util.Map;

import com.tictactoe.game.web.exception.AlreadyJoinedException;
import com.tictactoe.game.web.exception.MissingGameException;
import com.tictactoe.game.web.exception.MissingPlayerException;

/**
 * The <code>GameInitializationService</code> interface defines all the business behaviors for operations related to
 * game initialization, such as creating game and joining into the game.
 *
 * @author Bosko Mijin
 */
public interface GameInitializationService {

    /**
     * The <code>createGame</code> method defines the game creation process.
     *
     * @return Map - The map which contains game id and token of the user which is initialized game.
     */
    public Map<String, String> createGame();

    /**
     * The <code>joinGame</code> method defines the game joining process.
     *
     * @param gameId - The id of the requested game.
     * @return String - The player id string of the player which is joined game.
     * @throws AlreadyJoinedException - The case when somebody already joined the game.
     * @throws MissingGameException - The case when requested game doesn't exists.
     * @throws MissingPlayerException - The case when creator of the requested game doesn't exists.
     */
    public String joinGame(int gameId) throws AlreadyJoinedException, MissingGameException, MissingPlayerException;

}
