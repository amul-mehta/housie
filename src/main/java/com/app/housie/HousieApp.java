package com.app.housie;

import com.app.housie.core.impl.HousieGame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HousieApp {

    public static void main(String[] args) {
        log.info("**** Let's play Housie ****");
        HousieGame housieGame = new HousieGame();
        housieGame.play();
    }
}

