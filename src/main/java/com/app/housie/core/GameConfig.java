package com.app.housie.core;

import com.app.housie.commons.Constants;
import com.app.housie.commons.Utils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class GameConfig {
    private final int numOfPlayers;
    private final int numPerRow;
    private final int endRange;
    private int[] boardSize = new int[]{Constants.DEFAULT_TICKET_ROW_SIZE, Constants.DEFAULT_TICKET_COLUMN_SIZE};

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
            String[] splits = ticketSize.split(Constants.TICKET_SIZE_SPLIT_STR);
            boardSize = new int[Constants.BOARD_DIMENSION_SIZE];
            boardSize[0] = Integer.parseInt(splits[0].trim());
            boardSize[1] = Integer.parseInt(splits[1].trim());
        }
    }
}
