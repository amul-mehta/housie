package com.app.housie.core;

import com.app.housie.model.GameParams;

/**
 * A class implementing this interface is responsible to initialize the Parameters for a given Game
 * @param <V> type of parameters that should be initialized
 */
public interface GameConfig<V extends GameParams> {
    /**
     * @return the parameters for this game
     */
    V getParams();

    /**
     * initializes the game parameters
     */
    void init();

    /**
     * @return if the initialization was interrupted before completion
     */
    boolean isInterrupted();
}
