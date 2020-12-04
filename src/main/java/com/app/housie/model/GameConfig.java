package com.app.housie.model;


import lombok.Getter;

@Getter
public class GameConfig {
    int[] boardSize = new int[2];
    int numOfPlayers;
    int numPerRow;
    int endRange;

    public void initalize(){

    }

}
