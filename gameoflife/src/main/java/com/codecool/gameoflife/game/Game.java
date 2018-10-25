package com.codecool.gameoflife.game;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Game {

    private Rules rules;
    private BoardReader reader;

    private Set<Cell> state;
    private int width, heigth;

    public Game(Rules rules, BoardReader reader) {
        this.rules = rules;
        this.reader = reader;
    }

    public void initializeBoard(String board) throws GameOfLifeException {
        this.state = reader.read(board);
        String[] rows = board.split("\n");
        this.width = rows[0].length();
        this.heigth = rows.length;
    }

    public Set<Cell> updateState() throws GameOfLifeException {
        checkState();

        Set<Cell> newState = new HashSet<>();

        for (int i = 0; i < heigth; i++) {
            for (int j = 0; j < width; j++) {
                int liveNeighbors = liveNeighbors(i, j);
                boolean alive = this.state.contains(new Cell(i, j));
                if (rules.nextState(alive, liveNeighbors)) {
                    newState.add(new Cell(i, j));
                }
            }
        }

        this.state = newState;
        return this.state;
    }

    private int liveNeighbors(int row, int column) {
        int sum = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0)
                    continue;

                if (this.state.contains(new Cell(row + i, column + j)))
                    sum += 1;
            }
        }

        return sum;
    }

    private void checkState() throws GameOfLifeException {
        if (this.state == null)
            throw new GameOfLifeException("Game is not initialized");
    }

    public String getBoardStatAsString() throws GameOfLifeException {
        checkState();

        char[][] stateChars = new char[heigth][width];

        for (char[] row: stateChars) {
            Arrays.fill(row, '_');
        }

        this.state.forEach(cell -> stateChars[cell.getX()][cell.getY()] = '*');

        return Arrays.stream(stateChars)
                .map(String::new)
                .collect(Collectors.joining("\n"));
    }
}
