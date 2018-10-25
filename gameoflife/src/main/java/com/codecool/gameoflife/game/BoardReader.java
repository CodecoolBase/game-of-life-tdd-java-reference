package com.codecool.gameoflife.game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BoardReader {
    public Set<Cell> read(String board) throws GameOfLifeException {
        if (board == null)
            throw new GameOfLifeException("Board shouldn't be null");

        String[] rows  = board.split("\n");
        validateBoardRowsLength(rows);

        Set<Cell> cells = new HashSet<>();

        for (int i = 0; i < rows.length; i++) {
            String row  = rows[i];
            for (int j = 0; j < row.length(); j++) {
                if (row.charAt(j) == '*') {
                    cells.add(new Cell(i, j));
                }
            }
        }

        return cells;

    }

    private void validateBoardRowsLength(String[] rows) throws GameOfLifeException {
        int firstLength = rows[0].length();

        boolean rowsAreSameLength = Arrays.stream(rows)
                .allMatch(s -> s.length() == firstLength);

        if (!rowsAreSameLength)
            throw new GameOfLifeException("Rows are not the same size");
    }
}
