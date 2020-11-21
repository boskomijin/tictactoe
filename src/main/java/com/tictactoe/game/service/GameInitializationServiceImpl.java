package com.tictactoe.game.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tictactoe.game.model.Board;
import com.tictactoe.game.model.Game;
import com.tictactoe.game.model.Player;
import com.tictactoe.game.repository.GamesRepository;
import com.tictactoe.game.util.TokenUtil;
import com.tictactoe.game.web.exception.AlreadyJoinedException;
import com.tictactoe.game.web.exception.MissingGameException;
import com.tictactoe.game.web.exception.MissingPlayerException;

import lombok.extern.slf4j.Slf4j;

/**
 * The <code>GameserviceImpl</code> class provides the all the required activities for the game creation.
 *
 * @author Bosko Mijin
 */
@Slf4j
@Service
public class GameInitializationServiceImpl implements GameInitializationService {

    /** The games repository. */
    @Autowired
    private GamesRepository gamesRepository;

    /** The player service. */
    @Autowired
    private PlayerService playerService;

    /** The token util. */
    @Autowired
    private TokenUtil tokenUtil;

    /** The table size - defined in the properties as spring profile. */
    @Value("${game.config.table-size}")
    private int tableSize;

    /**
     * The <code>createGame</code> method creates a new game, populates it with player x which is initiated the game,
     * initializes the board for the game, persists game in aggregator and returns data for further process.
     *
     * @return Map - The map which contains game id and token of the user which is initialized game.
     */
    @Override
    public Map<String, String> createGame() {
        log.debug("--> createGame");
        Map<Player, Character> players = new HashMap<>();
        Player player = playerService.createPlayer();
        players.put(player, 'X');
        Game game = gamesRepository
                .persistNewGameInAggregator(Game.builder().players(players).board(new Board(tableSize)).build());
        Map<String, String> data = new HashMap<>();
        data.put("gameId", String.valueOf(game.getId()));
        log.debug("gameId: %s", game.getId());
        String token = tokenUtil.generateToken(player.getId());
        log.debug("token: %s", token);
        data.put("token", token);
        log.debug("<-- createGame");
        return data;
    }

    /**
     * The <code>joinGame</code> method obtains the required game, populates it with player o which is joining the game,
     * and returns the game for further process.
     *
     * @param gameId - The id of the requested game.
     * @return game - The updated and prepared game.
     * @throws AlreadyJoinedException - The case when somebody already joined the game.
     * @throws MissingGameException - The case when requested game doesn't exists.
     * @throws MissingPlayerException - The case when creator of the requested game doesn't exists.
     */
    @Override
    public String joinGame(int gameId) throws AlreadyJoinedException, MissingGameException, MissingPlayerException {
        Game game = gamesRepository.getGameById(gameId);
        Map<Player, Character> players = game.getPlayers();
        if (players.size() == 2) {
            throw new AlreadyJoinedException("Somebody already joined into this game.");
        } else if (players.isEmpty()) {
            throw new MissingPlayerException("Creator of the game is missing.");
        } else {
            Player player = playerService.createPlayer();
            players.put(player, 'O');
            return tokenUtil.generateToken(player.getId());
        }
    }
}
