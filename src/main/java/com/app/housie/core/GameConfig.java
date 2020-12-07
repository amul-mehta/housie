package com.app.housie.core;

import com.app.housie.model.GameParams;

import java.util.Scanner;

public interface GameConfig<V extends GameParams> {
    /**
     * @return
     */
    V getParams();

    /**
     * @param scanner
     */
    void init(Scanner scanner);

    /**
     * @return
     */
    boolean isInterrupted();
}
