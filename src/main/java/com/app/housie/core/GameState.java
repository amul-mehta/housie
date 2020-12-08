package com.app.housie.core;

/**
 * A class implementing this interface should be responsible for keeping track of the overall game progress
 * and update the progress (state) on input.
 * @param <V> the type of value which will result in change of game state
 */
public interface GameState<V> {
    /**
     * @return if the game has reached its final state
     */
    boolean isCompleted();

    /**
     * @param input value which will result in change of game state
     */
    void updateState(V input);

    /**
     * prints the end of game summary
     */
    void printSummary();
}
