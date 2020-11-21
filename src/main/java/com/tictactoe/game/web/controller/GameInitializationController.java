package com.tictactoe.game.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tictactoe.game.service.GameInitializationService;
import com.tictactoe.game.util.UrlUtil;
import com.tictactoe.game.web.response.DefaultResponseBody;
import com.tictactoe.game.web.response.GameCreationResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * The <code>GameInitializationController</code> class is a RESTful web service controller, and through each
 * {@link RequestMapping} method returns a <code>@ResponseBody</code> which, by default, contains a ResponseEntity
 * converted into JSON with an associated HTTP status code.
 *
 * @author Bosko Mijin
 */
@RestController
@Slf4j
public class GameInitializationController {

    /** The game initialization service. */
    @Autowired
    private GameInitializationService gameInitializationService;

    /**
     * The <code>createGame</code> method exposes the web service endpoint to initialize the new game and returns the
     * invitation url in the body and auth token in the header with the HTTP response status code 200.
     *
     * @param request - The HpptServletRequest which provides request information required for further handling.
     * @return A ResponseEntity containing a single GameCreationResponseBody object and a HTTP status code as described
     *         in the method comment.
     */
    @RequestMapping(value = "${api.paths.game-path}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameCreationResponseBody> createGame(HttpServletRequest request) {
        log.info("--> createGame");
        Map<String, String> data = gameInitializationService.createGame();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Set-Auth-Token", data.get("token"));
        log.info("<-- createGame");
        return ResponseEntity.ok().headers(responseHeaders)
                .body(new GameCreationResponseBody(UrlUtil.prepareBaseUrl(request), data.get("gameId")));
    }

    /**
     * The <code>joinGame</code> method exposes the web service endpoint to join into the already created game and
     * returns the status in the body and the authentication token in the header with the HTTP response status code 200.
     *
     * @param gameId - The id of the requested game.
     * @param request - The HpptServletRequest which provides request information required for further handling.
     * @return A ResponseEntity containing a single JoinGameResponseBody object and a HTTP status code as described in
     *         the method comment.
     */
    @RequestMapping(value = "${api.paths.game-join-path}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponseBody> joinGame(@PathVariable("id") int gameId, HttpServletRequest request) {
        log.info("--> joinGame");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Set-Auth-Token", gameInitializationService.joinGame(gameId));
        log.info("<-- joinGame");
        return ResponseEntity.ok().headers(responseHeaders)
                .body(new DefaultResponseBody("You are successfuly joined the game."));
    }
}
