package com.app.housie.core;

public interface GameState<V> {
    boolean isCompleted();
    void updateState(V input);
    void printSummary();
}
