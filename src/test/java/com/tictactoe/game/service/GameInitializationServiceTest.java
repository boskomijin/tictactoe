package com.tictactoe.game.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tictactoe.game.model.Board;
import com.tictactoe.game.model.Game;
import com.tictactoe.game.model.Player;
import com.tictactoe.game.repository.GamesRepository;
import com.tictactoe.game.web.exception.AlreadyJoinedException;
import com.tictactoe.game.web.exception.MissingPlayerException;

/**
 * The <code>GameInitializationServiceTest</code> class tests behavior implemented in
 * {@link GameInitializationServiceImpl} class.
 *
 * @author Bosko Mijin.
 */
@SpringBootTest
public class GameInitializationServiceTest {

    /** The game initialization service. */
    @Autowired
    private GameInitializationService gameInitializationService;

    /** The games repository mock. */
    @MockBean
    private GamesRepository gamesRepositoryMock;

    /**
     * The <code>testCreateGame</code> method tests the createGame method and ensures that created game is not null.
     */
    @Test
    void testCreateGame() {
        Game gameMock = getGameMock();
        gameMock.setId(1);
        gameMock.setBoard(new Board(3));
        Mockito.when(gamesRepositoryMock.persistNewGameInAggregator(Mockito.any())).thenReturn(gameMock);
        Mockito.when(gamesRepositoryMock.getGameById(1)).thenReturn(gameMock);
        Assert.assertNotNull(gameInitializationService.createGame());
    }

    /**
     * The <code>joinGame</code> method tests the joinGame method and ensures that player O joined the game.
     */
    @Test
    void testJoinGame() {
        Game gameMock = getGameMock();
        Mockito.when(gamesRepositoryMock.getGameById(1)).thenReturn(gameMock);
        Assert.assertNotNull(gameInitializationService.joinGame(1));
    }

    /**
     * The <code>joinGame</code> method tests the joinGame method and ensures that player O can't join twice.
     */
    @Test
    void testJoinGameAlreadyJoined() {
        Game gameMock = getGameMock();
        gameMock.getPlayers().put(Player.builder().build(), 'O');
        Mockito.when(gamesRepositoryMock.getGameById(1)).thenReturn(gameMock);
        Assertions.assertThrows(AlreadyJoinedException.class, () -> gameInitializationService.joinGame(1));
    }

    /**
     * The <code>joinGame</code> method tests the joinGame method and ensures that player O can't join if player X
     * missing.
     */
    @Test
    void testJoinGameMissingPlayer() {
        Game gameMock = getGameMock();
        gameMock.getPlayers().clear();
        Mockito.when(gamesRepositoryMock.getGameById(1)).thenReturn(gameMock);
        Assertions.assertThrows(MissingPlayerException.class, () -> gameInitializationService.joinGame(1));
    }

    /**
     * The <code>getGameMock</code> method creates the mock game object which is used into testCreateGame.
     *
     * @return Game - the game mock prepared for testing.
     */
    private Game getGameMock() {
        Map<Player, Character> players = new HashMap<>();
        players.put(Player.builder().build(), 'X');
        return Game.builder().players(players).board(new Board(3)).build();
    }
}
