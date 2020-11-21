package com.tictactoe.game.util;

import java.util.Map;

import com.tictactoe.game.model.Game;
import com.tictactoe.game.model.Player;

/**
 * The <code>GameUtil</code> class contains all the utility methods related to the game checking and handling.
 *
 * @author Bosko Mijin
 */
public final class GameUtil {

    /**
     * The <code>GameUtil</code> no-args private constructor - prevents instantiation.
     */
    private GameUtil() {

    }

    /**
     * The <code>whoIsOnTurn</code> method returns players mark according to the provided size of the map.
     *
     * @param mapSize - the number of elements in map.
     * @return char - The char X if player is has to make a turn, otherwise char O.
     */
    public static final char whoIsOnTurn(int mapSize) {
        if (mapSize % 2 == 0) {
            return 'O';
        } else {
            return 'X';
        }
    }

    /**
     * The <code>convertRowMarkToNumber</code> method returns the number from provided string. According to the fact
     * that english alphabet has 26 letters this method is taking all consecutive letters like A, or BE, or SJNR and
     * calculating the number according to given value.
     *
     * @param rowString - The parsed column string from player's entry.
     * @return int - the integer value from string.
     */
    public static int convertRowMarkToNumber(String rowString) {
        String checkString = rowString;
        int calculatedNumber = 0;
        int exponent = 0;
        for (int i = 0; i < checkString.length(); ++i) {
            if (checkString.charAt(i) >= 'A' && checkString.charAt(i) <= 'Z') {
                calculatedNumber += (checkString.charAt(i) - 'A' + (int) Math.pow(26, exponent));
                exponent++;
            }
        }
        return calculatedNumber;
    }

    /**
     * The <code>getMarkForPlayerId</code> method extracts the player mark from players map according to provided player
     * id.
     *
     * @param game - The game which containing the player map.
     * @param playerId - The id of the user for which mark is needed.
     * @return Character - The character of the position which is related to user.
     */
    public static final Character getMarkForPlayerId(Game game, String playerId) {
        Map<Player, Character> map = game.getPlayers();
        for (Map.Entry<Player, Character> entry : map.entrySet()) {
            if (entry.getKey().getId().contentEquals(playerId)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * The <code>isPlayingBoardFUll</code> method checks is playing board full, and if it is, setting game over with no
     * winner.
     *
     * @param game - The game for which check is performed.
     */
    public static void isPlayingBoardFull(Game game, int boardSize) {
        if (game.getBoard().getPlayingBoard().size() == Math.pow(boardSize, 2)) {
            game.setGameOver(true);
            if (game.getWinner() == null) {
                game.setWinner('D');
            }
        }
    }
}
