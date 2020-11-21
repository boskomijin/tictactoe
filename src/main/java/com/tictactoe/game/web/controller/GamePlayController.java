package com.tictactoe.game.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tictactoe.game.service.GamePlayService;
import com.tictactoe.game.web.exception.AuthenticationException;
import com.tictactoe.game.web.request.PlayGameRequestBody;
import com.tictactoe.game.web.response.DefaultResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * The <code>GamePlayController</code> class is a RESTful web service controller, and through each
 * {@link RequestMapping} method returns a <code>@ResponseBody</code> which, by default, contains a ResponseEntity
 * converted into JSON with an associated HTTP status code.
 *
 * @author Bosko Mijin
 */
@RestController
@Slf4j
public class GamePlayController {

    /** The game play service. */
    @Autowired
    private GamePlayService gamePlayService;

    /**
     * The <code>playGame</code> method exposes the web service endpoint to play the already initialized game and
     * returns the status in the body with HTTP response status code 200.
     *
     * @param gameId - The id of the requested game.
     * @param requestBody - The body in JSON format which brings the information about placing mark.
     * @param token - The token of the user which was performed playing action.
     * @return A ResponseEntity containing a single PlayingGameResponseBody object and a HTTP status code as described
     *         in the method comment.
     * @throws AuthenticationException - The Authentication Exception if user is not authorized.
     */
    @RequestMapping(value = "${api.paths.game-play-path}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponseBody> playGame(@PathVariable("id") int gameId,
            @RequestBody PlayGameRequestBody requestBody, @RequestHeader("Auth-Token") String token)
            throws AuthenticationException {
        log.info("--> playGame");
        boolean sucess = gamePlayService.playGame(gameId, requestBody.getPosition(), token);
        log.info("<-- playGame");
        return ResponseEntity.ok().body(new DefaultResponseBody(sucess ? "OK" : "SPACE_TAKEN"));
    }

    /**
     * The <code>obtainGameStatus</code> method exposes the web service endpoint to obtain game status and returns the
     * status in the body with HTTP response status code 200.
     *
     * @param gameId - The id of the requested game.
     * @param token - The token of the user which was performed playing action.
     * @return A ResponseEntity containing a single GameStatusResponseBody object and a HTTP status code as described in
     *         the method comment.
     * @throws AuthenticationException - The Authentication Exception if user is not authorized.
     */
    @RequestMapping(value = "${api.paths.game-play-path}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponseBody> obtainGameStatus(@PathVariable("id") int gameId,
            @RequestHeader("Auth-Token") String token) throws AuthenticationException {
        log.info("--> obtainGameStatus");
        String message = gamePlayService.obtainGameStatus(gameId, token);
        log.info("<-- obtainGameStatus");
        return ResponseEntity.ok().body(new DefaultResponseBody(message));
    }
}
