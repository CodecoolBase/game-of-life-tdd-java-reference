package com.codecool.gameoflife.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {

    private Game game;

    @BeforeEach
    public void init() {
        game = new Game(new Rules(), new BoardReader());
    }

    @Test
    public void sanityCheck() {
        assertThat(game).isNotNull();
        assertThat(game.getRules()).isNotNull();
    }

    @Test
    public void invalidBoardThrowsGameOfLifeException() {
        assertThrows(GameOfLifeException.class, () -> game.initializeBoard("___\n_______*_"));
    }

    @Test
    public void uninitializedGameThrowsGameOfLifeException() {
        assertThrows(GameOfLifeException.class, () -> game.updateState());
        assertThrows(GameOfLifeException.class, () -> game.getBoardStatAsString());
    }

    @Test
    public void initializeGameShouldCalculateItsWidthAndHeight() throws GameOfLifeException {
        game.initializeBoard("**_\n___");
        assertThat(game.getWidth()).isEqualTo(3);
        assertThat(game.getHeigth()).isEqualTo(2);
        assertThat(game.getState()).isNotEmpty();
    }

    @Test
    public void emptyBoardStaysEmptyAfterOneGeneration() throws GameOfLifeException {
        game.initializeBoard("");
        Set<Cell> newCells = game.updateState();
        assertThat(newCells).isEmpty();
    }

    /**
     * http://pi.math.cornell.edu/~lipa/mec/lifep.png
     */
    @Test
    public void caseOne() throws GameOfLifeException {
        game.initializeBoard("_*\n*_\n*_");
        Set<Cell> cells = game.updateState();
        assertThat(cells).containsExactlyInAnyOrder(
                new Cell(1, 0),
                new Cell(1, 1)
        );
        cells = game.updateState();
        assertThat(cells).isEmpty();
    }

    /**
     * http://pi.math.cornell.edu/~lipa/mec/lifep.png
     */
    @Test
    public void caseTwo() throws GameOfLifeException {
        game.initializeBoard("_*_\n_*_\n_*_");
        Set<Cell> cells = game.updateState();
        assertThat(cells).containsExactlyInAnyOrder(
                new Cell(1, 0),
                new Cell(1, 1),
                new Cell(1, 2)
        );
        cells = game.updateState();
        assertThat(cells).containsExactlyInAnyOrder(
                new Cell(0, 1),
                new Cell(1, 1),
                new Cell(2, 1)
        );
    }

    @Test
    public void boardStateAsString() throws GameOfLifeException {
        game.initializeBoard("__*\n_*_\n*__");
        game.updateState();

        assertThat(game.getBoardStatAsString()).isEqualTo("___\n_*_\n___");
    }

}
