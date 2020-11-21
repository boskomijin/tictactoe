package com.tictactoe.game.model;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;

/**
 * The <code>Board</code> class represents an game board which holds the current positions on the playing table, move
 * calculations and provide calculation functionality.
 *
 * @author Bosko Mijin.
 */
public class Board implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = new SecureRandom().nextLong();

    /** The playing board. */
    @Getter
    private Map<Pair<Integer, Integer>, Character> playingBoard;

    /** The calculated sum row by row. */
    private int[] sumByRows;

    /** The calculated sum column by column. */
    private int[] sumByColumns;

    /** The calculated values in major diagonal. */
    private int sumMajorDiagonal;

    /** The calculated values in minor diagonal. */
    private int sumMinorDiagonal;

    /**
     * The <code>Board</code> parameterized constructor initializes the playing table map.
     */
    public Board(int boardSize) {
        playingBoard = new HashMap<>();
        this.sumByRows = new int[boardSize];
        this.sumByColumns = new int[boardSize];
    }

    /**
     * The <code>addValueToSumByRows</code> method adds the value to row sum count by index.
     *
     * @param rowIndex - The row index of the element which has to be processed.
     * @param moveValue - The value which has to be added to element.
     */
    public void addValueToSumByRows(int rowIndex, int moveValue) {
        sumByRows[rowIndex] += moveValue;
    }

    /**
     * The <code>addValueToSumByColumns</code> method adds the value to column sum count by index.
     *
     * @param columnIndex - The column index of the element which has to be processed.
     * @param moveValue - The value which has to be added to element.
     */
    public void addValueToSumByColumns(int columnIndex, int moveValue) {
        sumByColumns[columnIndex] += moveValue;
    }

    /**
     * The <code>addValueToSumMajorDiagonal</code> method adds the value to sum major diagonal by index.
     *
     * @param moveValue - The value which has to be added to element.
     */
    public void addValueToSumMajorDiagonal(int moveValue) {
        sumMajorDiagonal += moveValue;
    }

    /**
     * The <code>addValueToSumMinorDiagonal</code> method adds the value to sum minor diagonal by index.
     *
     * @param moveValue - The value which has to be added to element.
     */
    public void addValueToSumMinorDiagonal(int moveValue) {
        sumMinorDiagonal += moveValue;
    }

    /**
     * The <code>getCalculatedMoveValues</code> method returns all the current calculations for requested coordinates.
     *
     * @param rowIndex - The requested row index.
     * @param columnIndex - The requested column index.
     * @return int[] - calculated move values at the current time.
     */
    public int[] getCalculatedMoveValues(int rowIndex, int columnIndex) {
        return new int[] { sumByRows[rowIndex], sumByColumns[columnIndex], sumMajorDiagonal, sumMinorDiagonal };

    }
}
