package com.tictactoe.game.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.tictactoe.game.model.Board;
import com.tictactoe.game.model.Game;
import com.tictactoe.game.model.Player;
import com.tictactoe.game.repository.GamesRepository;
import com.tictactoe.game.service.GamePlayService;
import com.tictactoe.game.util.TokenUtil;
import com.tictactoe.game.web.BaseControllerTest;
import com.tictactoe.game.web.request.PlayGameRequestBody;
import com.tictactoe.game.web.response.DefaultResponseBody;

/**
 * The <code>GameControllerTest</code> class extends {@link BaseControllerTest} and tests behavior implemented in
 * {@link GameInitializationController} class.
 *
 * @author Bosko Mijin.
 */
public class GamePlayControllerTest extends BaseControllerTest {

    /** The constant STATUS_CHECK. */
    private static final String STATUS_CHECK = "Status check";

    /** The constant TOKEN. */
    private static final String TOKEN = "token";

    /** The game play service. */
    @MockBean
    private GamePlayService gamePlayService;

    /** The game repository. */
    @MockBean
    private GamesRepository gamesRepository;

    /** The token util. */
    @MockBean
    private TokenUtil tokenUtil;

    /**
     * The <code>testPlayGameOK</code> method tests the playGame method implemented in GamePlayController and ensures
     * that this method returns expected status, has token, has expected format in body and that it calling service only
     * once.
     *
     * @throws Exception the exception
     */
    @Test
    public void testPlayGameOK() throws Exception {
        Game mockGame = getGameMock();
        Player playerO = Player.builder().build();
        mockGame.getPlayers().put(playerO, 'O');
        Mockito.when(gamesRepository.getGameById(1)).thenReturn(mockGame);
        Mockito.when(gamePlayService.playGame(1, "A1", TOKEN)).thenReturn(true);
        Mockito.when(tokenUtil.validateToken(TOKEN, mockGame)).thenReturn(true);
        Mockito.when(tokenUtil.getUsernameFromToken(TOKEN)).thenReturn(playerO.getId());
        PlayGameRequestBody body = new PlayGameRequestBody("A1");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/game/1").accept(MediaType.APPLICATION_JSON)
                .header("Auth-Token", TOKEN).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(STATUS_CHECK, HttpStatus.OK.value(), response.getStatus());
        Assert.assertEquals("Status check", "OK",
                mapper.readValue(response.getContentAsString(), DefaultResponseBody.class).getStatus());
        Mockito.verify(gamePlayService, Mockito.times(1)).playGame(1, "A1", TOKEN);
    }

    /**
     * The <code>testPlayGameSpaceTaken</code> method tests the playGame method implemented in GamePlayController and
     * ensures that this method returns expected status, has token, has expected format in body and that it calling
     * service only once.
     *
     * @throws Exception the exception
     */
    @Test
    public void testPlayGameSpaceTaken() throws Exception {
        Game mockGame = getGameMock();
        Player playerO = Player.builder().build();
        mockGame.getPlayers().put(playerO, 'O');
        Mockito.when(gamesRepository.getGameById(1)).thenReturn(mockGame);
        Mockito.when(gamePlayService.playGame(1, "A1", TOKEN)).thenReturn(false);
        Mockito.when(tokenUtil.validateToken(TOKEN, mockGame)).thenReturn(true);
        Mockito.when(tokenUtil.getUsernameFromToken(TOKEN)).thenReturn(playerO.getId());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/game/1").accept(MediaType.APPLICATION_JSON)
                .header("Auth-Token", TOKEN).contentType(MediaType.APPLICATION_JSON).content("{\"position\": \"A1\"}");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(STATUS_CHECK, HttpStatus.OK.value(), response.getStatus());
        Assert.assertEquals(STATUS_CHECK, "SPACE_TAKEN",
                mapper.readValue(response.getContentAsString(), DefaultResponseBody.class).getStatus());
        Mockito.verify(gamePlayService, Mockito.times(1)).playGame(1, "A1", TOKEN);
    }

    /**
     * The <code>testObtainGameStatus</code> method tests the obtainGameStatus method implemented in GamePlayController
     * and ensures that this method returns expected status, has token, has expected format in body and that it calling
     * service only once.
     *
     * @throws Exception the exception
     */
    @Test
    public void testObtainGameStatus() throws Exception {
        Game mockGame = getGameMock();
        Player playerO = Player.builder().build();
        mockGame.getPlayers().put(playerO, 'O');
        Mockito.when(gamesRepository.getGameById(1)).thenReturn(mockGame);
        Mockito.when(gamePlayService.obtainGameStatus(1, TOKEN)).thenReturn("YOUR_TURN");
        Mockito.when(tokenUtil.validateToken(TOKEN, mockGame)).thenReturn(true);
        Mockito.when(tokenUtil.getUsernameFromToken(TOKEN)).thenReturn(playerO.getId());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/game/1").accept(MediaType.APPLICATION_JSON)
                .header("Auth-Token", TOKEN).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(STATUS_CHECK, HttpStatus.OK.value(), response.getStatus());
        Assert.assertEquals(STATUS_CHECK, "YOUR_TURN",
                mapper.readValue(response.getContentAsString(), DefaultResponseBody.class).getStatus());
        Mockito.verify(gamePlayService, Mockito.times(1)).obtainGameStatus(1, TOKEN);
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
