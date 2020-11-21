package com.tictactoe.game.web.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The <code>GameCreationResponseBody</code> class holds the values required in response body as part of game creation
 * response entity.
 *
 * @author Bosko Mijin
 */
@NoArgsConstructor
@Getter
public class GameCreationResponseBody {

    /** The invitation url. */
    private String invitationUrl;

    /**
     * Instantiates a new game creation response body with the required data.
     *
     * @param baseUrl - The base url which has to be integrated into the invitation URL.
     * @param gameId - The game id which has to be integrated into the invitation URL.
     */
    public GameCreationResponseBody(String baseUrl, String gameId) {
        invitationUrl = String.format("%s/game/%s/join", baseUrl, gameId);
    }

}
