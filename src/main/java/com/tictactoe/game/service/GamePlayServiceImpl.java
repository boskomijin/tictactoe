package com.tictactoe.game.service;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tictactoe.game.model.Board;
import com.tictactoe.game.model.Game;
import com.tictactoe.game.repository.GamesRepository;
import com.tictactoe.game.util.GameUtil;
import com.tictactoe.game.util.TokenUtil;
import com.tictactoe.game.web.exception.AnotherPlayersTurnException;
import com.tictactoe.game.web.exception.AuthenticationException;
import com.tictactoe.game.web.exception.GameOverException;
import com.tictactoe.game.web.exception.InvalidPositionException;
import com.tictactoe.game.web.exception.MissingPlayerException;
import com.tictactoe.game.web.response.StatusEnum;

/**
 * The <code>GamePlayServiceImpl</code> interface implements all the business behaviors for operations related to game
 * playing.
 *
 * @author Bosko Mijin
 */
@Service
public class GamePlayServiceImpl implements GamePlayService {

    /** The games repository. */
    @Autowired
    private GamesRepository gamesRepository;

    /** The authentication service. */
    @Autowired
    private AuthenticationService authenticationService;

    /** The token util. */
    @Autowired
    private TokenUtil tokenUtil;

    /** The table size - defined in the properties as spring profile. */
    @Value("${game.config.table-size}")
    private int tableSize;

    /** The allowed mark format. */
    @Value("${game.config.allowed-mark-format}")
    private String allowedMarkFormat;

    /** The allowed rows mark. */
    @Value("${game.config.allowed-rows-mark}")
    private String allowedRowsMark;

    /** The allowed columns mark. */
    @Value("${game.config.allowed-columns-mark}")
    private String allowedColumnsMark;

    /**
     * The <code>playGame</code> method implements the playing game functionality.
     *
     * @param gameId - The id of the game.
     * @param playerMark - The action (placing mark) which player intends to play.
     * @return boolean - If placing mark is performed returns true, otherwise false.
     * @throws AuthenticationException - The authentication exception if the user is not authorized.
     */
    @Override
    public boolean playGame(int gameId, String playerMark, String token) throws AuthenticationException {
        authenticationService.authenticate(gameId, token);
        String playerId = tokenUtil.getUsernameFromToken(token);
        Game game = gamesRepository.getGameById(gameId);
        isGamePlayable(game);
        Character player = GameUtil.getMarkForPlayerId(game, playerId);
        Board board = game.getBoard();
        int[] coordinates = obtainBoardCoordinates(playerMark);
        if (player == GameUtil.whoIsOnTurn(board.getPlayingBoard().size())) {
            boolean successfullPlay = putMarkInTableMap(player, coordinates[0], coordinates[1], board);
            if (successfullPlay && calculateWinningState(coordinates[0], coordinates[1], board)) {
                game.setGameOver(true);
                game.setWinner(player);
            }
            GameUtil.isPlayingBoardFull(game, tableSize);
            return successfullPlay;
        } else {
            throw new AnotherPlayersTurnException("Another player has to play.");
        }
    }

    /**
     * The <code>obtainGameStatus</code> method implements game status obtaining functionality.
     *
     * @param gameId - The id of the game.
     * @param token - The token of the user which was performed playing action.
     * @return String - The message of the status.
     * @throws AuthenticationException - The authentication exception.
     */
    @Override
    public String obtainGameStatus(int gameId, String token) throws AuthenticationException {
        Game game = gamesRepository.getGameById(gameId);
        authenticationService.authenticate(gameId, token);
        String playerId = tokenUtil.getUsernameFromToken(token);
        if (game.isGameOver()) {
            if (game.getWinner().charValue() == GameUtil.getMarkForPlayerId(game, playerId).charValue()) {
                return StatusEnum.YOU_WON.getValue();
            } else if (game.getWinner().charValue() == 'D') {
                return StatusEnum.DRAW.getValue();
            } else {
                return StatusEnum.YOU_LOST.getValue();
            }
        } else if (game.getPlayers().size() < 2) {
            return StatusEnum.AWAITING_OTHER_PLAYER.getValue();
        } else {
            if (GameUtil.whoIsOnTurn(game.getBoard().getPlayingBoard().size()) == GameUtil.getMarkForPlayerId(game,
                    playerId)) {
                return StatusEnum.YOUR_TURN.toString();
            } else {
                return StatusEnum.OTHER_PLAYER_TURN.getValue();
            }
        }
    }

    /**
     * The <code>obtainBoardCoordinates</code> method parsing playerMark and calculates the coordinates (indices of
     * array).
     *
     * @param playerMark - The mark which player has entered.
     * @return int[] - populated array which carry the indices -> row index and column index respectively.
     */
    private int[] obtainBoardCoordinates(String playerMark) {
        if (playerMark.matches(allowedMarkFormat)) {
            String rowString = playerMark.replaceAll(allowedRowsMark, "");
            String columnString = playerMark.replaceAll(allowedColumnsMark, "");
            return new int[] { GameUtil.convertRowMarkToNumber(columnString) - 1, Integer.parseInt(rowString) - 1 };
        } else {
            throw new InvalidPositionException("Illegal player mark.");
        }
    }

    /**
     * The <code>isGamePlayable</code> method determines conditions for playing game. If the game is not playable it
     * will throw appropriate exception.
     *
     * @param game - The game for which is checking playable state.
     * @throws GameOverException - The exception thrown if the game is already over.
     * @throws MissingPlayerException - The exception thrown if the game missing player.
     */
    private void isGamePlayable(Game game) throws GameOverException, MissingPlayerException {
        if (game.isGameOver()) {
            throw new GameOverException("This game is already over.");
        }
        if (game.getPlayers().size() < 2) {
            throw new MissingPlayerException("The other player is missing.");
        }
    }

    /**
     * The <code>putMarkInTableMap</code> puts the player mark to the map with key pair which are row and column indices
     * if the element is null, otherwise it will be considered as taken.
     *
     * @param mark - The player mark.
     * @param rowIndex - The index of the row in the playing table map.
     * @param columnIndex - The index of the column of the playing table.
     * @param playingTable - The playing table which holds the status.
     * @return boolean - true if mark is set, otherwise false.
     */
    private boolean putMarkInTableMap(char mark, int rowIndex, int columnIndex, Board board) {
        Map<Pair<Integer, Integer>, Character> playingBoard = board.getPlayingBoard();
        if (playingBoard.get(Pair.of(columnIndex, rowIndex)) == null) {
            playingBoard.put(Pair.of(columnIndex, rowIndex), mark);
            calculateMove(rowIndex, columnIndex, mark, board);
            return true;
        } else {
            return false;
        }
    }

    /**
     * The <code>calculateMove</code> method calculates the move values.
     *
     * @param rowIndex - The row index of the current move.
     * @param columnIndex - The column index of the current move.
     * @param player - The active player.
     * @param board - The board.
     */
    public void calculateMove(int rowIndex, int columnIndex, char player, Board board) {
        int moveValue = (player == 'X' ? 1 : -1);
        board.addValueToSumByRows(rowIndex, moveValue);
        board.addValueToSumByColumns(columnIndex, moveValue);
        if (rowIndex == columnIndex) {
            board.addValueToSumMajorDiagonal(moveValue);
        }
        if (columnIndex == tableSize - rowIndex - 1) {
            board.addValueToSumMinorDiagonal(moveValue);
        }
    }

    /**
     * The <code>calculateMoveAndWinningState</code> method calculates the move values and checks winning state.
     *
     * @param rowIndex - The row index of the current move.
     * @param columnIndex - The column index of the current move.
     * @return boolean - <code>true</code> if move is winning, otherwise <code>false</code>.
     */
    private boolean calculateWinningState(int rowIndex, int columnIndex, Board board) {
        int[] moveValues = board.getCalculatedMoveValues(rowIndex, columnIndex);
        return Math.abs(moveValues[0]) == tableSize || Math.abs(moveValues[1]) == tableSize
                || Math.abs(moveValues[2]) == tableSize || Math.abs(moveValues[3]) == tableSize;
    }
}
