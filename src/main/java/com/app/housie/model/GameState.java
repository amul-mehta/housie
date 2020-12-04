package com.app.housie.model;

import java.util.Map;
import java.util.Set;

public class GameState {
    // status of game, is it completed, keep track of numbers that are generated, remaining, etc.
    Integer totalCombinations;
    Map<Combination, Set<Player>> currentState;


    boolean isCompleted() {
        return currentState.size() == totalCombinations;
    }

    public boolean updateState(Player player, Combination combination, int number){
        return isCompleted();
    }
}

