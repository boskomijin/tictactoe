package com.tictactoe.game.web.response;

/**
 * The <code>StatusEnum</code> enum is holding all the constants which have to be used for populating status in default
 * response body.
 *
 * @author Bosko Mijin
 */
public enum StatusEnum {

    /** Defined status for the case when other player hasn't joined yet. */
    AWAITING_OTHER_PLAYER("AWAITING_OTHER_PLAYER"),

    /** Defined status for the case when it's this player's turn. */
    YOUR_TURN("YOUR_TURN"),

    /** Defined status for the case when it's the other player's turn. */
    OTHER_PLAYER_TURN("OTHER_PLAYER_TURN"),

    /** Defined status for the case when this player won the game. */
    YOU_WON("YOU_WON"),

    /** Defined status for the case when the other player won the game. */
    YOU_LOST("YOU_LOST"),

    /** Defined status for the case when there's no more space left on the board and noone won. */
    DRAW("DRAW");

    /** The value. */
    private String value;

    /**
     * Instantiates a new Swagger Enum.
     *
     * @param value - The value
     */
    StatusEnum(String value) {
        this.value = value;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
