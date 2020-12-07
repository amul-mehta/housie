package com.app.housie.core;

/**
 * @param <V>
 */
public interface GameState<V> {
    /**
     * @return
     */
    boolean isCompleted();

    /**
     * @param input
     */
    void updateState(V input);

    /**
     *
     */
    void printSummary();
}
