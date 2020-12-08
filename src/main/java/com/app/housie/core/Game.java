package com.app.housie.core;

/**
 * Implementation of this interface will be responsible for initializing game parameters and ultimately
 * controlling the gameplay
 */
public interface Game {

    /**
     * this method initializes all the parameters needed to play the game
     * @return if the initialization was interrupted before completion
     */
    boolean init();

    /**
     * this method controls the game play
     */
    void play();
}
