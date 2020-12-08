package com.app.housie.core;

/**
 * This is the driver class that is responsible for initializing game parameters and ultimately
 * controlling the gameplay
 */
public interface Game {

    /**
     * @return
     */
    boolean init();

    /**
     *
     */
    void play();
}
