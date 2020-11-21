package com.tictactoe.game.model;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * The <code>Player</code> class is an entity which represents an actor in game.
 *
 * @author Bosko Mijin.
 */
@Data
@AllArgsConstructor
@Builder
public class Player implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = new SecureRandom().nextLong();

    /** An unique identifier which may be used as a reference to this entity by external systems. */
    private final String id = UUID.randomUUID().toString();
}
