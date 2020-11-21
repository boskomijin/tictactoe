package com.tictactoe.game.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tictactoe.game.model.Game;
import com.tictactoe.game.web.exception.MissingGameException;

import lombok.extern.slf4j.Slf4j;

/**
 * The <code>GamesRepository</code> class represents a repository for the games. According to the requirement, there is
 * not allowed to use database, but instead has to be used in-memory solution, and this class is intended to holds all
 * the required data for game(s).
 *
 * @author Bosko Mijin.
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GamesRepository {

    /** The game aggregator - container for all games. */
    private final List<Game> gameAggregator;

    /**
     * The <code>GamesRepository</code> default private no args constructor - prevents initialization, initializes only
     * on creation because of singleton scope.
     */
    @Autowired
    private GamesRepository() {
        gameAggregator = new ArrayList<>();
    }

    /**
     * The <code>persistNewGameInAggregator</code> method persist new game in aggregator.
     *
     * @param game - The game which has to be persisted in aggregator.
     * @return the game - The persisted game.
     */
    public Game persistNewGameInAggregator(Game game) {
        log.debug("--> persistNewGameInAggregator");
        int currentMaxId = gameAggregator.size();
        game.setId(++currentMaxId);
        gameAggregator.add(game);
        log.debug("<-- persistNewGameInAggregator");
        return game;
    }

    /**
     * The <code>getGameById</code> method gets the game by id.
     *
     * @param id - The id of the game which has to be obtained.
     * @return game - the game obtained by id.
     */
    public Game getGameById(int id) throws MissingGameException {
        log.debug("--> getGameById");
        log.debug("<-- getGameById");
        return gameAggregator.stream().filter(game -> id == game.getId()).findAny()
                .orElseThrow(() -> new MissingGameException("Required game is not created yet."));
    }
}
