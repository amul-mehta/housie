package com.app.housie;

import com.app.housie.model.Game;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HousieApp {

    public static void main(String[] args) {
        log.info("Starting Housie Game!!");
        Game game = new Game();
        game.play();
    }
}

