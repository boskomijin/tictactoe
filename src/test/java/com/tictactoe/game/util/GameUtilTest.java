package com.tictactoe.game.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tictactoe.game.model.Board;
import com.tictactoe.game.model.Game;
import com.tictactoe.game.model.Player;

/**
 * The <code>GameUtilTest</code> class contains all the JUnit tests for the {@link GameUtil} class and their
 * functionality.
 *
 * @author Bosko Mijin
 */
@SpringBootTest
public class GameUtilTest {

    /**
     * The <code>testWhoIsOnTurn</code> method tests the conditions which player has to play - who is on turn.
     */
    @Test
    public void testWhoIsOnTurn() {
        Assertions.assertEquals('O', GameUtil.whoIsOnTurn(0), "Test - 0 returns player O");
        Assertions.assertEquals('X', GameUtil.whoIsOnTurn(1), "Test - 1 returns player X");
        Assertions.assertEquals('O', GameUtil.whoIsOnTurn(2), "Test - 2 returns player O");
        Assertions.assertEquals('X', GameUtil.whoIsOnTurn(3), "Test - 3 returns player X");
        Assertions.assertEquals('O', GameUtil.whoIsOnTurn(4), "Test - 4 returns player O");
        Assertions.assertEquals('X', GameUtil.whoIsOnTurn(5), "Test - 5 returns player X");
        Assertions.assertEquals('O', GameUtil.whoIsOnTurn(6), "Test - 6 returns player O");
        Assertions.assertEquals('X', GameUtil.whoIsOnTurn(7), "Test - 7 returns player X");
        Assertions.assertEquals('O', GameUtil.whoIsOnTurn(8), "Test - 8 returns player O");
    }

    /**
     * The <code>testConvertColumnMarkToNumber</code> method tests the conversion of letters to numbers.
     */
    @Test
    public void testConvertColumnMarkToNumber() {
        Assertions.assertEquals(1, GameUtil.convertRowMarkToNumber("A"), "Test - A returns 1");
        Assertions.assertEquals(2, GameUtil.convertRowMarkToNumber("B"), "Test - B returns 2");
        Assertions.assertEquals(3, GameUtil.convertRowMarkToNumber("C"), "Test - C returns 3");
        Assertions.assertEquals(27, GameUtil.convertRowMarkToNumber("AA"), "Test - A returns 27");
        Assertions.assertEquals(703, GameUtil.convertRowMarkToNumber("AAA"), "Test - A returns 703");
        Assertions.assertEquals(703, GameUtil.convertRowMarkToNumber("AAeA"), "Test - A returns 703");
        Assertions.assertEquals(703, GameUtil.convertRowMarkToNumber("AA!A"), "Test - A returns 703");
        Assertions.assertEquals(704, GameUtil.convertRowMarkToNumber("AAB"), "Test - A returns 704");
    }

    /**
     * The <code>testGetMarkForPlayerNonExisting</code> method tests the returning value when player is not existing.
     */
    @Test
    public void testGetMarkForPlayerNonExisting() {
        Player player = Player.builder().build();
        Map<Player, Character> players = new HashMap<>();
        players.put(player, 'O');
        Assertions.assertNull(GameUtil.getMarkForPlayerId(Game.builder().players(players).build(), "X"),
                "Test non existing player in list.");
    }

    /**
     * The <code>testIsPlayingBoardFullSetWinner</code> method tests the condition if already set winner and game over.
     */
    @Test
    public void testIsPlayingBoardFullSetWinner() {
        Board board = new Board(3);
        board.getPlayingBoard().put(Pair.of(0, 0), 'O');
        board.getPlayingBoard().put(Pair.of(0, 1), 'X');
        board.getPlayingBoard().put(Pair.of(1, 0), 'O');
        board.getPlayingBoard().put(Pair.of(1, 1), 'X');
        board.getPlayingBoard().put(Pair.of(2, 1), 'O');
        board.getPlayingBoard().put(Pair.of(2, 0), 'X');
        board.getPlayingBoard().put(Pair.of(0, 2), 'O');
        board.getPlayingBoard().put(Pair.of(1, 2), 'X');
        board.getPlayingBoard().put(Pair.of(2, 2), 'O');
        Game game = Game.builder().board(board).build();
        game.setGameOver(true);
        game.setWinner('D');
        GameUtil.isPlayingBoardFull(game, 3);
        Assertions.assertEquals('D', game.getWinner().charValue());
    }
}
