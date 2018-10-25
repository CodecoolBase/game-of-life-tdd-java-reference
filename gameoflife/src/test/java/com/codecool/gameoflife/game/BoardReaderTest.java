package com.codecool.gameoflife.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.mockito.internal.util.collections.Sets;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardReaderTest {

    private BoardReader reader;

    @BeforeEach
    public void init() {
        reader = new BoardReader();
    }

    @Test
    public void sanityCheck() {
        assertThat(reader).isNotNull();
    }

    @Test
    public void cellInSetBehavior() {
        Cell cell1 = new Cell(20, 34);
        Cell cell2 = new Cell(6, 12);

        Set<Cell> cells = Sets.newSet(cell1, cell2);

        assertThat(cells).hasSize(2)
                .containsExactlyInAnyOrder(new Cell(6, 12),
                                           new Cell(20, 34));
    }

    @Test
    public void shouldThrowExceptionOnNullBoard() {
        assertThrows(GameOfLifeException.class, () -> reader.read(null));
    }

    @Test
    public void emptyStringBecomesEmptySet() throws GameOfLifeException {
        Set<Cell> cells = reader.read("");
        assertThat(cells).isEmpty();
    }

    @TestFactory
    public Stream<DynamicTest> validStringBoardsToSetOfCells() {
        List<StringMapToSetOfCells> cases =
                Lists.newArrayList(
                        new StringMapToSetOfCells(" ", Sets.newSet()),
                        new StringMapToSetOfCells("*", Sets.newSet(new Cell(0,0))),
                        new StringMapToSetOfCells("__*__", Sets.newSet(new Cell(0,2))),
                        new StringMapToSetOfCells("**_**\n***__", Sets.newSet(
                                new Cell(0,0),
                                new Cell(0,1),
                                new Cell(0,3),
                                new Cell(0,4),
                                new Cell(1,0),
                                new Cell(1,1),
                                new Cell(1,2)
                        ))
                );

        return cases.stream().map(this::createDynamicTest);
    }


    @TestFactory
    public Stream<DynamicTest> invalidStringBoardsToException() {
        List<StringMapToSetOfCells> cases =
                Lists.newArrayList(
                        new StringMapToSetOfCells("**_**\n***_____"),
                        new StringMapToSetOfCells("*_*\n***\n______")
                );

        return cases.stream()
                .map(stringMapToSetOfCells ->
                        DynamicTest.dynamicTest(stringMapToSetOfCells.toString(),
                                () -> assertThrows(GameOfLifeException.class, () -> reader.read(stringMapToSetOfCells.getStringMap())))
                        );
    }

    private DynamicTest createDynamicTest(StringMapToSetOfCells stringMapToSetOfCells) {
        return DynamicTest.dynamicTest(
                    stringMapToSetOfCells.toString(),
                    () -> assertThat(stringMapToSetOfCells.getSetOfCells())
                                .containsExactlyInAnyOrderElementsOf(
                                        reader.read(stringMapToSetOfCells.getStringMap())));
    }


    @Data
    @AllArgsConstructor
    private class StringMapToSetOfCells {
        private String stringMap;
        private Set<Cell> setOfCells;

        public StringMapToSetOfCells(String stringMap) {
            this.stringMap = stringMap;
        }
    }

}
