package com.app.housie.model;


import com.app.housie.Utils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class GameConfig {
    int[] boardSize = new int[2];
    int numOfPlayers;
    int numPerRow;
    int endRange;

    public GameConfig(){
        log.info("Enter Number of Players");
        this.numOfPlayers = Utils.getIntFromConsole();
    }

}
