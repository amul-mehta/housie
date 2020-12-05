package com.app.housie.core;


import com.app.housie.commons.Utils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class GameConfig {
    int[] boardSize = new int[]{3, 10};
    int numOfPlayers;
    int numPerRow;
    int endRange;

    public GameConfig() {
        log.info("Enter the number range (1-n)");
        this.endRange = Utils.getIntFromConsole();
        log.info("Enter Number of Players playing the game");
        this.numOfPlayers = Utils.getIntFromConsole();
        log.info("Enter the ticket size in \"ROW X COLUMN\" format (defaults to 3 X 10, press return to keep default value)");
        String ticketSize = Utils.getLineFromConsole();
        setBoardSize(ticketSize);
        log.info("Enter Numbers per Row");
        this.numPerRow = Utils.getIntFromConsole();
    }

    private void setBoardSize(String ticketSize) {
        if (ticketSize.length() > 0) {
            String[] splits = ticketSize.split("X");
            boardSize = new int[2];
            boardSize[0] = Integer.parseInt(splits[0]);
            boardSize[1] = Integer.parseInt(splits[1]);
        }
    }
}
