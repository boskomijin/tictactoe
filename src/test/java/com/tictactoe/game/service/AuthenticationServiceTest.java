package com.tictactoe.game.service;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tictactoe.game.util.TokenUtil;
import com.tictactoe.game.web.exception.AuthenticationException;

/**
 * The <code>AuthenticationServiceTest</code> class tests behavior implemented in {@link AuthenticationServiceImpl}
 * class.
 *
 * @author Bosko Mijin.
 */
@SpringBootTest
public class AuthenticationServiceTest {

    /** The authentication service. */
    @Autowired
    private AuthenticationService authenticationService;

    /** The game initialization service. */
    @Autowired
    private GameInitializationService gameInitializationService;

    /** The token util. */
    @Autowired
    private TokenUtil tokenUtil;

    /**
     * The <code>testAuthentication</code> method tests the authentication method and ensures that authentication is
     * successfully performed.
     */
    @Test
    public void testAuthentication() {
        Map<String, String> data = gameInitializationService.createGame();
        Integer gameId = Integer.parseInt(data.get("gameId"));
        String token = data.get("token");
        AuthenticationException authenticationException = null;
        try {
            authenticationService.authenticate(gameId, token);
        } catch (AuthenticationException e) {
            authenticationException = e;
        }
        Assertions.assertNull(authenticationException, "Authentication test - not throwing exception.");
    }

    /**
     * The <code>testAuthentication</code> method tests the authentication method and ensures that authentication is
     * successfully performed.
     */
    @Test
    public void testAuthenticationExceptional() {
        Map<String, String> data = gameInitializationService.createGame();
        Integer gameId = Integer.parseInt(data.get("gameId"));
        String token = tokenUtil.generateToken("dummyPlayer");
        AuthenticationException authenticationException = null;
        try {
            authenticationService.authenticate(gameId, token);
        } catch (AuthenticationException e) {
            authenticationException = e;
        }
        Assertions.assertNotNull(authenticationException, "Authentication test - throwing exception.");
        Assertions.assertEquals("User is not authenticated.", authenticationException.getMessage(), "Authen");
    }
}
