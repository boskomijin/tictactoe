package com.tictactoe.game.web.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The <code>PlayGameRequestBody</code> class holds the values obtained from request body as game placing mark.
 *
 * @author Bosko Mijin
 */
@NoArgsConstructor
@Getter
public class PlayGameRequestBody {

    /** The position. */
    private String position;

    /**
     * Instantiates a new play game request body with the required data.
     *
     * @param position - The game id which has to be integrated into the invitation URL.
     */
    public PlayGameRequestBody(String position) {
        this.position = position;
    }
}
