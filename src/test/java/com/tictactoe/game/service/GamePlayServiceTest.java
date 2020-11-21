package com.tictactoe.game.service;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tictactoe.game.web.exception.AnotherPlayersTurnException;
import com.tictactoe.game.web.exception.AuthenticationException;
import com.tictactoe.game.web.exception.GameOverException;
import com.tictactoe.game.web.exception.InvalidPositionException;
import com.tictactoe.game.web.exception.MissingPlayerException;

/**
 * The <code>GamePlayServiceTest</code> class tests behavior implemented in {@link GamePlayServiceImpl} class.
 *
 * @author Bosko Mijin.
 */
@SpringBootTest
public class GamePlayServiceTest {

    /** The constant gameId. */
    private static final String GAME_ID = "gameId";

    /** The constant gameId. */
    private static final String TOKEN = "token";

    /** The game play service. */
    @Autowired
    private GamePlayService gamePlayService;

    /** The game initialization service. */
    @Autowired
    private GameInitializationService gameInitializationService;

    /**
     * The <code>testIsGamePlayable</code> method tests expected result - playable.
     *
     * @throws AuthenticationException - the authentication exception.
     */
    @Test
    public void testIsGamePlayable() throws AuthenticationException {
        Map<String, String> data = gameInitializationService.createGame();
        Integer gameId = Integer.parseInt(data.get(GAME_ID));
        String tokenPlayerO = gameInitializationService.joinGame(gameId);
        Assertions.assertTrue(gamePlayService.playGame(gameId, "A1", tokenPlayerO), "Test game is playable.");
    }

    /**
     * The <code>testIsGamePlayableMissingPlayer</code> method tests missing player exception.
     *
     * @throws AuthenticationException - the authentication exception.
     */
    @Test
    public void testIsGamePlayableMissingPlayer() throws AuthenticationException {
        Map<String, String> data = gameInitializationService.createGame();
        Integer gameId = Integer.parseInt(data.get(GAME_ID));
        String token = data.get(TOKEN);
        MissingPlayerException missingPlayerException = null;
        try {
            gamePlayService.playGame(gameId, "A1", token);
        } catch (MissingPlayerException e) {
            missingPlayerException = e;
        }
        Assertions.assertNotNull(missingPlayerException, "Missing player test.");
        Assertions.assertEquals("The other player is missing.", missingPlayerException.getMessage(),
                "Missing player exception message test");
    }

    /**
     * The <code>testIsGamePlayableInvalidPositionExceptionRegex</code> method tests invalid regex entry.
     *
     * @throws AuthenticationException - the authentication exception.
     */
    @Test
    public void testIsGamePlayableInvalidPositionExceptionRegex() throws AuthenticationException {
        Map<String, String> data = gameInitializationService.createGame();
        Integer gameId = Integer.parseInt(data.get(GAME_ID));
        String tokenPlayerO = gameInitializationService.joinGame(gameId);
        InvalidPositionException invalidPositionException = null;
        try {
            gamePlayService.playGame(gameId, "f7", tokenPlayerO);
        } catch (InvalidPositionException e) {
            invalidPositionException = e;
        }
        Assertions.assertNotNull(invalidPositionException, "Invalid position test.");
        Assertions.assertEquals("Illegal player mark.", invalidPositionException.getMessage(),
                "Invalid position exception message test");
    }

    /**
     * The <code>testFullGameOwin</code> method tests expected full game - O win in the row.
     *
     * @throws AuthenticationException - the authentication exception.
     */
    @Test
    public void testFullGameOwin() throws AuthenticationException {
        Map<String, String> data = gameInitializationService.createGame();
        Integer gameId = Integer.parseInt(data.get(GAME_ID));
        final String tokenPlayerX = data.get(TOKEN);
        Assertions.assertEquals("AWAITING_OTHER_PLAYER", gamePlayService.obtainGameStatus(gameId, tokenPlayerX));
        final String tokenPlayerO = gameInitializationService.joinGame(gameId);
        Assertions.assertTrue(gamePlayService.playGame(gameId, "A1", tokenPlayerO), "Test game - O is playing A1.");
        Assertions.assertEquals("OTHER_PLAYER_TURN", gamePlayService.obtainGameStatus(gameId, tokenPlayerO));
        Assertions.assertTrue(gamePlayService.playGame(gameId, "B1", tokenPlayerX), "Test game - X is playing B1.");
        Assertions.assertEquals("YOUR_TURN", gamePlayService.obtainGameStatus(gameId, tokenPlayerO));
        Assertions.assertTrue(gamePlayService.playGame(gameId, "A2", tokenPlayerO), "Test game - O is playing A2.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "B2", tokenPlayerX), "Test game - X is playing B2.");
        Assertions.assertFalse(gamePlayService.playGame(gameId, "B2", tokenPlayerO), "Test game - O is playing B2.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "A3", tokenPlayerO), "Test game - O is playing C1.");
    }

    /**
     * The <code>testFullGameXwin</code> method tests expected full game - X win in the column.
     *
     * @throws AuthenticationException - the authentication exception.
     */
    @Test
    public void testFullGameXwin() throws AuthenticationException {
        Map<String, String> data = gameInitializationService.createGame();
        Integer gameId = Integer.parseInt(data.get(GAME_ID));
        final String tokenPlayerX = data.get(TOKEN);
        final String tokenPlayerO = gameInitializationService.joinGame(gameId);
        Assertions.assertTrue(gamePlayService.playGame(gameId, "A1", tokenPlayerO), "Test game - O is playing A1.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "A2", tokenPlayerX), "Test game - X is playing A2.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "B1", tokenPlayerO), "Test game - O is playing B1.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "B2", tokenPlayerX), "Test game - X is playing B2.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "C3", tokenPlayerO), "Test game - O is playing C3.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "C2", tokenPlayerX), "Test game - O is playing C2.");
        Assertions.assertEquals("YOU_WON", gamePlayService.obtainGameStatus(gameId, tokenPlayerX));
        Assertions.assertEquals("YOU_LOST", gamePlayService.obtainGameStatus(gameId, tokenPlayerO));
    }

    /**
     * The <code>testFullGameOwin</code> method tests expected full game - O win in the major diagonal.
     *
     * @throws AuthenticationException - the authentication exception.
     */
    @Test
    public void testFullGameOwinMajor() throws AuthenticationException {
        Map<String, String> data = gameInitializationService.createGame();
        Integer gameId = Integer.parseInt(data.get(GAME_ID));
        final String tokenPlayerX = data.get(TOKEN);
        final String tokenPlayerO = gameInitializationService.joinGame(gameId);
        Assertions.assertTrue(gamePlayService.playGame(gameId, "A3", tokenPlayerO), "Test game - O is playing A3.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "B1", tokenPlayerX), "Test game - X is playing B1.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "B2", tokenPlayerO), "Test game - O is playing B2.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "A1", tokenPlayerX), "Test game - X is playing A1.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "C1", tokenPlayerO), "Test game - O is playing C1.");
    }

    /**
     * The <code>testFullGameOwin</code> method tests expected full game - O win in the minor diagonal.
     *
     * @throws AuthenticationException - the authentication exception.
     */
    @Test
    public void testFullGameOwinMinor() throws AuthenticationException {
        Map<String, String> data = gameInitializationService.createGame();
        Integer gameId = Integer.parseInt(data.get(GAME_ID));
        final String tokenPlayerX = data.get(TOKEN);
        final String tokenPlayerO = gameInitializationService.joinGame(gameId);
        Assertions.assertTrue(gamePlayService.playGame(gameId, "A1", tokenPlayerO), "Test game - O is playing A1.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "B1", tokenPlayerX), "Test game - X is playing B1.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "B2", tokenPlayerO), "Test game - O is playing B2.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "A2", tokenPlayerX), "Test game - X is playing A2.");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "C3", tokenPlayerO), "Test game - O is playing C3.");
    }

    /**
     * The <code>testFullGameDraw</code> method tests expected full game - draw.
     *
     * @throws AuthenticationException - the authentication exception.
     */
    @Test
    public void testFullGameDraw() throws AuthenticationException {
        Map<String, String> data = gameInitializationService.createGame();
        Integer gameId = Integer.parseInt(data.get(GAME_ID));
        final String tokenPlayerX = data.get(TOKEN);
        final String tokenPlayerO = gameInitializationService.joinGame(gameId);
        AnotherPlayersTurnException anotherPlayersTurnException = null;
        try {
            gamePlayService.playGame(gameId, "A1", tokenPlayerX);
        } catch (AnotherPlayersTurnException e) {
            anotherPlayersTurnException = e;
        }
        Assertions.assertNotNull(anotherPlayersTurnException, "Another player test.");
        Assertions.assertEquals("Another player has to play.", anotherPlayersTurnException.getMessage(),
                "Another player exception message test");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "A1", tokenPlayerO), "Test game - O is playing A1!");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "A2", tokenPlayerX), "Test game - X is playing A2!");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "B1", tokenPlayerO), "Test game - O is playing B1!");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "B2", tokenPlayerX), "Test game - X is playing B2!");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "C2", tokenPlayerO), "Test game - O is playing C2!");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "C1", tokenPlayerX), "Test game - X is playing C1!");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "A3", tokenPlayerO), "Test game - O is playing A3!");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "B3", tokenPlayerX), "Test game - X is playing B3!");
        Assertions.assertTrue(gamePlayService.playGame(gameId, "C3", tokenPlayerO), "Test game - O is playing C3!");
        GameOverException gameOverException = null;
        try {
            gamePlayService.playGame(gameId, "A1", tokenPlayerX);
        } catch (GameOverException e) {
            gameOverException = e;
        }
        Assertions.assertNotNull(gameOverException, "Game over test.");
        Assertions.assertEquals("This game is already over.", gameOverException.getMessage(),
                "Game over exception message test");
        try {
            gamePlayService.playGame(gameId, "A1", tokenPlayerO);
        } catch (GameOverException e) {
            gameOverException = e;
        }
        Assertions.assertNotNull(gameOverException, "Game over test.");
        Assertions.assertEquals("This game is already over.", gameOverException.getMessage(),
                "Game over exception message test");
        Assertions.assertEquals("DRAW", gamePlayService.obtainGameStatus(gameId, tokenPlayerX));
    }
}
