package com.codecool.gameoflife.game;

public class Rules {

    public boolean nextState(boolean alive, int numberOfNeighbors) {
        if (numberOfNeighbors == 3 || (alive && numberOfNeighbors == 2))
            return true;
        return false;
    }
}
