package com.tictactoe.game.service;

import com.tictactoe.game.model.Player;

/**
 * The <code>PlayerService</code> interface defines all the business behaviors for operations related to player
 * functionalities.
 *
 * @author Bosko Mijin
 */
public interface PlayerService {

    /**
     * The <code>createPlayer</code> method defines the player creation process.
     *
     * @return player - The initialized player.
     */
    public Player createPlayer();

}
