package com.tictactoe.game.service;

import org.springframework.stereotype.Service;

import com.tictactoe.game.model.Player;

/**
 * The <code>PlayerServiceImpl</code> class provides the all the required activities for the player functionality.
 *
 * @author Bosko Mijin
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    /**
     * The <code>createPlayer</code> method creates a new player and returns it for further process.
     *
     * @return player - The initialized player.
     */
    @Override
    public Player createPlayer() {
        return Player.builder().build();
    }
}
