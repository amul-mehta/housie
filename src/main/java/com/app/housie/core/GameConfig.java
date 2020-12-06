package com.app.housie.core;

import com.app.housie.model.GameParams;

import java.util.Scanner;

public interface GameConfig<V extends GameParams> {
    V getParams();
    void init(Scanner scanner);
    boolean isInterrupted();
}
