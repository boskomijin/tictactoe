package com.tictactoe.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tictactoe.game.model.Game;
import com.tictactoe.game.repository.GamesRepository;
import com.tictactoe.game.util.TokenUtil;
import com.tictactoe.game.web.exception.AuthenticationException;

import lombok.extern.slf4j.Slf4j;

/**
 * The <code>AuthenticationService</code> interface implements all the business behaviors for authentication
 * functionality.
 *
 * @author Bosko Mijin
 */
@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    /** The games repository. */
    @Autowired
    private GamesRepository gamesRepository;

    /** The token util. */
    @Autowired
    private TokenUtil tokenUtil;

    /**
     * The <code>authenticate</code> method authenticates player according gameId and token.
     *
     * @param gameId - The gameId of the game which player intends to play.
     * @param token - The player's token.
     * @throws AuthenticationException - The exception if user isn't authenticated.
     */
    @Override
    public void authenticate(int gameId, String token) throws AuthenticationException {
        log.debug(String.format("--> authenticate", token));
        Game game = gamesRepository.getGameById(gameId);
        if (tokenUtil.validateToken(token, game)) {
            log.debug(String.format("Token %s is valid", token));
            log.debug(String.format("<-- authenticate", token));
        } else {
            log.debug(String.format("<-- authenticate", token));
            throw new AuthenticationException("User is not authenticated.");
        }
    }
}
