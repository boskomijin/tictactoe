package com.tictactoe.game.web.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The <code>DefaultResponseBody</code> class holds the values required in response body as part of response entity.
 *
 * @author Bosko Mijin
 */
@NoArgsConstructor
@Getter
public class DefaultResponseBody {

    /** The status. */
    private String status;

    /**
     * Instantiates a new game creation response body with the required data.
     *
     * @param status - The message which has to be populated in status field.
     */
    public DefaultResponseBody(String status) {
        this.status = status;
    }

}
