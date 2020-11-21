package com.tictactoe.game.model;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The <code>Game</code> class is an entity which represents an game frame which holds the stands and players.
 *
 * @author Bosko Mijin.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = new SecureRandom().nextLong();

    /** The id. */
    private int id;

    /** A secondary unique identifier which may be used as a reference to this entity by external systems. */
    private final String referenceId = UUID.randomUUID().toString();

    /** The players - players which are participating in the game. */
    private Map<Player, Character> players;

    /** The board - game board which holds the current game position. */
    private Board board;

    /** The isGameOver - game over indicator. */
    private boolean isGameOver;

    /** The winner. */
    private Character winner;
}
