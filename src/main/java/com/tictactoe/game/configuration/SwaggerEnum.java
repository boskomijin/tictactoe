package com.tictactoe.game.configuration;

/**
 * The <code>SwaggerEnum</code> enum is holding all the constants which have to be used with swagger configuration.
 *
 * @author Bosko Mijin
 */
public enum SwaggerEnum {

    /** The Constant TITLE - The title which has to be displayed. */
    TITLE("Tic Tac Toe Rest Application"),
    /** The Constant VERSION - The version which has to be displayed. */
    VERSION("0.0.1-SNAPSHOT"),
    /** The Constant DESCRIPTION - The short description which has to be displayed. */
    DESCRIPTION("Tic tac toe game as a demonstration."),
    /** The Constant DEFINITION_GAME - The name of definition which can be selected. */
    DEFINITION_GAME("game"),
    /** The Constant DEFAULT_PATTERN - The pattern which is used by default. */
    DEFAULT_PATTERN("/**");

    /** The value. */
    private String value;

    /**
     * Instantiates a new Swagger Enum.
     *
     * @param value - The value
     */
    SwaggerEnum(String value) {
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
