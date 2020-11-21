package com.tictactoe.game.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tictactoe.game.model.Game;
import com.tictactoe.game.web.exception.MissingGameException;

/**
 * The <code>GamesRepositoryTest</code> class tests behavior implemented in {@link GamesRepository} class.
 *
 * @author Bosko Mijin.
 */
@SpringBootTest
public class GamesRepositoryTest {

    /** The games repository. */
    @Autowired
    private GamesRepository gamesRepository;

    /**
     * The <code>testPersistsGame</code> tests persisting new game.
     */
    @Test
    public void testPersistsGame() {
        Assert.assertNotNull(gamesRepository.persistNewGameInAggregator(new Game()));
    }

    /**
     * The <code>testGettingExistingGame</code> tests getting the existing game.
     */
    @Test
    public void testGettingExistingGame() {
        Game game = gamesRepository.persistNewGameInAggregator(new Game());
        Assert.assertNotNull(gamesRepository.getGameById(game.getId()));
    }

    /**
     * The <code>testGettingNonExistingGame</code> tests getting the non existing game.
     */
    @Test
    void testGettingNonExistingGame() {
        Assertions.assertThrows(MissingGameException.class, () -> gamesRepository.getGameById(Integer.MAX_VALUE));
    }
}
