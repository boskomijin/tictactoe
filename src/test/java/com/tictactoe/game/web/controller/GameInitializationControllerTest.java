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
import com.tictactoe.game.service.GameInitializationService;
import com.tictactoe.game.web.BaseControllerTest;
import com.tictactoe.game.web.response.DefaultResponseBody;
import com.tictactoe.game.web.response.GameCreationResponseBody;

/**
 * The <code>GameControllerTest</code> class extends {@link BaseControllerTest} and tests behavior implemented in
 * {@link GameInitializationController} class.
 *
 * @author Bosko Mijin.
 */
public class GameInitializationControllerTest extends BaseControllerTest {

    /** The constant STATUS_CHECK. */
    private static final String STATUS_CHECK = "Status check";

    /** The constant TOKEN. */
    private static final String TOKEN = "token";

    /** The game initialization service. */
    @MockBean
    private GameInitializationService gameInitializationService;

    /**
     * The <code>testCreateGame</code> method tests the createGame method implemented in GameInitializationController
     * and ensures that this method returns expected status, has token, has expected format in body and that it calling
     * service only once.
     *
     * @throws Exception the exception
     */
    @Test
    public void testCreateGame() throws Exception {
        Game mockGame = getGameMock();
        mockGame.setId(1);
        Map<String, String> mockMap = new HashMap<>();
        mockMap.put("gameId", String.valueOf(1));
        mockMap.put("token", TOKEN);
        Mockito.when(gameInitializationService.createGame()).thenReturn(mockMap);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/game").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(STATUS_CHECK, HttpStatus.OK.value(), response.getStatus());
        Assert.assertEquals("Check token", TOKEN, response.getHeader("Set-Auth-Token"));
        Assert.assertNotNull("Invitation URL check",
                mapper.readValue(response.getContentAsString(), GameCreationResponseBody.class).getInvitationUrl());
        Mockito.verify(gameInitializationService, Mockito.times(1)).createGame();
    }

    /**
     * The <code>testJoinGame</code> method tests the joinGame method implemented in GameInitializationController and
     * ensures that this method returns expected status, has token, has expected format in body and that it calling
     * service only once.
     *
     * @throws Exception the exception
     */
    @Test
    public void testPlayGame() throws Exception {
        Game mockGame = getGameMock();
        mockGame.setId(1);
        mockGame.getPlayers().put(Player.builder().build(), 'O');
        Mockito.when(gameInitializationService.joinGame(1)).thenReturn(TOKEN);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/game/1/join").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(STATUS_CHECK, HttpStatus.OK.value(), response.getStatus());
        Assert.assertEquals("Check token", TOKEN, response.getHeader("Set-Auth-Token"));
        Assert.assertNotNull(STATUS_CHECK,
                mapper.readValue(response.getContentAsString(), DefaultResponseBody.class).getStatus());
        Mockito.verify(gameInitializationService, Mockito.times(1)).joinGame(1);
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
