package com.tictactoe.game.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tictactoe.game.model.Player;

/**
 * The <code>PlayerServiceTest</code> class tests behavior implemented in {@link PlayerServiceImpl} class.
 *
 * @author Bosko Mijin.
 */
@SpringBootTest
public class PlayerServiceTest {

    /** The player service. */
    @Autowired
    private PlayerService playerService;

    /**
     * The <code>testCreatePlayer</code> method tests the createPlayer method and ensures that created player is not
     * null.
     */
    @Test
    void testCreatePlayer() {
        Player player = playerService.createPlayer();
        Assert.assertNotNull("Create player check", player);
    }
}
