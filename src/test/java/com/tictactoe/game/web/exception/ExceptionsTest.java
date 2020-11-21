package com.tictactoe.game.web.exception;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.tictactoe.game.model.Board;
import com.tictactoe.game.model.Game;
import com.tictactoe.game.model.Player;
import com.tictactoe.game.repository.GamesRepository;
import com.tictactoe.game.service.GameInitializationService;
import com.tictactoe.game.service.GamePlayService;
import com.tictactoe.game.util.TokenUtil;
import com.tictactoe.game.web.BaseControllerTest;
import com.tictactoe.game.web.controller.GameInitializationController;

import io.jsonwebtoken.JwtException;

/**
 * The <code>ExceptionsTest</code> class extends {@link BaseControllerTest} and tests behavior implemented in
 * {@link GameInitializationController} class.
 *
 * @author Bosko Mijin.
 */
public class ExceptionsTest extends BaseControllerTest {

    /** The game join path. */
    private static final String GAME_JOIN_PATH = "/game/1/join";

    /** The game one path. */
    private static final String GAME_ONE_PATH = "/game/1";

    /** The constant test exception message. */
    private static final String TEST_EXCEPTION_MESSAGE = "testExceptionMessage";

    /** The constant message. */
    private static final String MESSAGE = "message";

    /** The constant exception. */
    private static final String EXCEPTION = "exception";

    /** The constant status. */
    private static final String STATUS = "status";

    /** The constant TOKEN. */
    private static final String TOKEN = "token";

    /** The constant AUTH_TOKEN. */
    private static final String AUTH_TOKEN = "Auth-Token";

    /** The constant PLAYER. */
    private static final String PLAYER = "player";

    /** The constant DEFAULT_TEST_CONTENT. */
    private static final String DEFAULT_TEST_CONTENT = "{\"position\": \"A1\"}";

    /** The game initialization service. */
    @MockBean
    private GameInitializationService gameInitializationService;

    /** The game play service. */
    @MockBean
    private GamePlayService gamePlayService;

    /** The game repository. */
    @MockBean
    private GamesRepository gamesRepository;

    /** The token util. */
    @MockBean
    private TokenUtil tokenUtil;

    /** The type reference used for converting from json. */
    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<Map<String, Object>>() {

    };

    /**
     * The <code>testMissingGameException</code> method tests the {@link MissingGameException} and related
     * {@link ExceptionAdvisor} and checks is exception functionality implemented correctly.
     *
     * @throws Exception the exception
     */
    @Test
    public void testMissingGameException() throws Exception {
        String testExceptionMessage = TEST_EXCEPTION_MESSAGE;
        Mockito.when(gameInitializationService.joinGame(1)).thenThrow(new MissingGameException(testExceptionMessage));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(GAME_JOIN_PATH).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Map<String, Object> attributes = mapper.readValue(response.getContentAsString(), MAP_TYPE);
        Assert.assertEquals(attributes.get(MESSAGE), testExceptionMessage);
        Assert.assertEquals(attributes.get(EXCEPTION), MissingGameException.class.getSimpleName());
        Assert.assertEquals(attributes.get(STATUS), HttpStatus.NOT_FOUND.value());
    }

    /**
     * The <code>testMissingPlayerException</code> method tests the {@link MissingPlayerException} and related
     * {@link ExceptionAdvisor} and checks is exception functionality implemented correctly.
     *
     * @throws Exception the exception
     */
    @Test
    public void testMissingPlayerException() throws Exception {
        String testExceptionMessage = TEST_EXCEPTION_MESSAGE;
        Mockito.when(gameInitializationService.joinGame(1)).thenThrow(new MissingPlayerException(testExceptionMessage));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(GAME_JOIN_PATH).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Map<String, Object> attributes = mapper.readValue(response.getContentAsString(), MAP_TYPE);
        Assert.assertEquals(attributes.get(MESSAGE), testExceptionMessage);
        Assert.assertEquals(attributes.get(EXCEPTION), MissingPlayerException.class.getSimpleName());
        Assert.assertEquals(attributes.get(STATUS), HttpStatus.NOT_FOUND.value());
    }

    /**
     * The <code>testAlreadyJoinedException</code> method tests the {@link AlreadyJoinedException} and related
     * {@link ExceptionAdvisor} and checks is exception functionality implemented correctly.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAlreadyJoinedException() throws Exception {
        String testExceptionMessage = TEST_EXCEPTION_MESSAGE;
        Mockito.when(gameInitializationService.joinGame(1)).thenThrow(new AlreadyJoinedException(testExceptionMessage));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(GAME_JOIN_PATH).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Map<String, Object> attributes = mapper.readValue(response.getContentAsString(), MAP_TYPE);
        Assert.assertEquals(attributes.get(MESSAGE), testExceptionMessage);
        Assert.assertEquals(attributes.get(EXCEPTION), AlreadyJoinedException.class.getSimpleName());
        Assert.assertEquals(attributes.get(STATUS), HttpStatus.CONFLICT.value());
    }

    /**
     * The <code>testInvalidPositionException</code> method tests the {@link InvalidPositionException} and related
     * {@link ExceptionAdvisor} and checks is exception functionality implemented correctly.
     *
     * @throws Exception the exception
     */
    @Test
    public void testInvalidPositionException() throws Exception {
        String testExceptionMessage = TEST_EXCEPTION_MESSAGE;
        Game mockGame = getGameMock();
        Player playerO = Player.builder().build();
        mockGame.getPlayers().put(playerO, 'O');
        Mockito.when(gamesRepository.getGameById(1)).thenReturn(mockGame);
        Mockito.when(gamePlayService.playGame(1, "A1", TOKEN))
                .thenThrow(new InvalidPositionException(testExceptionMessage));
        Mockito.when(tokenUtil.validateToken(TOKEN, mockGame)).thenReturn(true);
        Mockito.when(tokenUtil.getUsernameFromToken(TOKEN)).thenReturn(playerO.getId());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(GAME_ONE_PATH).accept(MediaType.APPLICATION_JSON)
                .header(AUTH_TOKEN, TOKEN).queryParam(PLAYER, TOKEN).contentType(MediaType.APPLICATION_JSON)
                .content(DEFAULT_TEST_CONTENT);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Map<String, Object> attributes = mapper.readValue(response.getContentAsString(), MAP_TYPE);
        Assert.assertEquals(attributes.get(MESSAGE), testExceptionMessage);
        Assert.assertEquals(attributes.get(EXCEPTION), InvalidPositionException.class.getSimpleName());
        Assert.assertEquals(attributes.get(STATUS), HttpStatus.CONFLICT.value());
    }

    /**
     * The <code>testAnotherPlayersTurnException</code> method tests the {@link AnotherPlayersTurnException} and related
     * {@link ExceptionAdvisor} and checks is exception functionality implemented correctly.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAnotherPlayersTurnException() throws Exception {
        String testExceptionMessage = TEST_EXCEPTION_MESSAGE;
        Game mockGame = getGameMock();
        Player playerO = Player.builder().build();
        mockGame.getPlayers().put(playerO, 'O');
        Mockito.when(gamesRepository.getGameById(1)).thenReturn(mockGame);
        Mockito.when(gamePlayService.playGame(1, "A1", TOKEN))
                .thenThrow(new AnotherPlayersTurnException(testExceptionMessage));
        Mockito.when(tokenUtil.validateToken(TOKEN, mockGame)).thenReturn(true);
        Mockito.when(tokenUtil.getUsernameFromToken(TOKEN)).thenReturn(playerO.getId());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(GAME_ONE_PATH).accept(MediaType.APPLICATION_JSON)
                .header(AUTH_TOKEN, TOKEN).queryParam(PLAYER, TOKEN).contentType(MediaType.APPLICATION_JSON)
                .content(DEFAULT_TEST_CONTENT);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Map<String, Object> attributes = mapper.readValue(response.getContentAsString(), MAP_TYPE);
        Assert.assertEquals(attributes.get(MESSAGE), testExceptionMessage);
        Assert.assertEquals(attributes.get(EXCEPTION), AnotherPlayersTurnException.class.getSimpleName());
        Assert.assertEquals(attributes.get(STATUS), HttpStatus.CONFLICT.value());
    }

    /**
     * The <code>testGameOverException</code> method tests the {@link GameOverException} and related
     * {@link ExceptionAdvisor} and checks is exception functionality implemented correctly.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGameOverException() throws Exception {
        String testExceptionMessage = TEST_EXCEPTION_MESSAGE;
        Game mockGame = getGameMock();
        Player playerO = Player.builder().build();
        mockGame.getPlayers().put(playerO, 'O');
        Mockito.when(gamesRepository.getGameById(1)).thenReturn(mockGame);
        Mockito.when(gamePlayService.playGame(1, "A1", TOKEN)).thenThrow(new GameOverException(testExceptionMessage));
        Mockito.when(tokenUtil.validateToken(TOKEN, mockGame)).thenReturn(true);
        Mockito.when(tokenUtil.getUsernameFromToken(TOKEN)).thenReturn(playerO.getId());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(GAME_ONE_PATH).accept(MediaType.APPLICATION_JSON)
                .header(AUTH_TOKEN, TOKEN).contentType(MediaType.APPLICATION_JSON).content(DEFAULT_TEST_CONTENT);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Map<String, Object> attributes = mapper.readValue(response.getContentAsString(), MAP_TYPE);
        Assert.assertEquals(attributes.get(MESSAGE), testExceptionMessage);
        Assert.assertEquals(attributes.get(EXCEPTION), GameOverException.class.getSimpleName());
        Assert.assertEquals(attributes.get(STATUS), HttpStatus.CONFLICT.value());
    }

    /**
     * The <code>testAuthenticationException</code> method tests the {@link AuthenticationException} and related
     * {@link ExceptionAdvisor} and checks is exception functionality implemented correctly.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAuthenticationException() throws Exception {
        String testExceptionMessage = TEST_EXCEPTION_MESSAGE;
        Game mockGame = getGameMock();
        Player playerO = Player.builder().build();
        mockGame.getPlayers().put(playerO, 'O');
        Mockito.when(gamesRepository.getGameById(1)).thenReturn(mockGame);
        Mockito.when(gamePlayService.playGame(1, "A1", TOKEN))
                .thenThrow(new AuthenticationException(testExceptionMessage));
        Mockito.when(tokenUtil.validateToken(TOKEN, mockGame)).thenReturn(true);
        Mockito.when(tokenUtil.getUsernameFromToken(TOKEN)).thenReturn(playerO.getId());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(GAME_ONE_PATH).accept(MediaType.APPLICATION_JSON)
                .header(AUTH_TOKEN, TOKEN).queryParam(PLAYER, TOKEN).contentType(MediaType.APPLICATION_JSON)
                .content(DEFAULT_TEST_CONTENT);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Map<String, Object> attributes = mapper.readValue(response.getContentAsString(), MAP_TYPE);
        Assert.assertEquals(attributes.get(MESSAGE), testExceptionMessage);
        Assert.assertEquals(attributes.get(EXCEPTION), AuthenticationException.class.getSimpleName());
        Assert.assertEquals(attributes.get(STATUS), HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * The <code>testAuthenticationException</code> method tests the {@link AuthenticationException} and related
     * {@link ExceptionAdvisor} and checks is exception functionality implemented correctly.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGeneralException() throws Exception {
        String testExceptionMessage = TEST_EXCEPTION_MESSAGE;
        Game mockGame = getGameMock();
        Player playerO = Player.builder().build();
        mockGame.getPlayers().put(playerO, 'O');
        Mockito.when(gamesRepository.getGameById(1)).thenReturn(mockGame);
        Mockito.when(gamePlayService.playGame(1, "A1", TOKEN)).thenThrow(new JwtException(testExceptionMessage));
        Mockito.when(tokenUtil.validateToken(TOKEN, mockGame)).thenReturn(true);
        Mockito.when(tokenUtil.getUsernameFromToken(TOKEN)).thenReturn(playerO.getId());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(GAME_ONE_PATH).accept(MediaType.APPLICATION_JSON)
                .header(AUTH_TOKEN, TOKEN).queryParam(PLAYER, TOKEN).contentType(MediaType.APPLICATION_JSON)
                .content(DEFAULT_TEST_CONTENT);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Map<String, Object> attributes = mapper.readValue(response.getContentAsString(), MAP_TYPE);
        Assert.assertEquals(attributes.get(MESSAGE), testExceptionMessage);
        Assert.assertEquals(attributes.get(EXCEPTION), JwtException.class.getSimpleName());
        Assert.assertEquals(response.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR.value());
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
