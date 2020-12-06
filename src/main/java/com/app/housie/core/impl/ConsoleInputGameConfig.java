package com.app.housie.core.impl;

import com.app.housie.commons.Constants;
import com.app.housie.commons.Utils;
import com.app.housie.core.GameConfig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.InputMismatchException;
import java.util.function.Consumer;

@Slf4j
@Getter
@Setter(AccessLevel.PRIVATE)
public class ConsoleInputGameConfig implements GameConfig {
    private int numOfPlayers;
    private int numPerRow;
    private int endRange;
    private boolean interrupted;
    private int[] boardSize = new int[]{Constants.DEFAULT_TICKET_ROW_SIZE, Constants.DEFAULT_TICKET_COLUMN_SIZE};


    @Override
    public void init() {
        log.info("Enter the number range (1-n)");
        setIntegerValueFromConsole(this::setEndRange);
        if (interrupted)
            return;
        log.info("Enter Number of Players playing the game");
        setIntegerValueFromConsole(this::setNumOfPlayers);
        if (interrupted)
            return;
        log.info("Enter the ticket size in \"ROW X COLUMN\" format (defaults to 3 X 10, press return to keep default value)");
        setBoardSize();
        if (interrupted)
            return;
        log.info("Enter Numbers per Row");
        setIntegerValueFromConsole(this::setNumPerRow);
    }

    public void setIntegerValueFromConsole(Consumer<Integer> setter) {
        boolean toQuit = false;
        while (!toQuit) {
            String rangeStr = Utils.getLineFromConsole();
            try {
                if (rangeStr.equals(Constants.OPTION_QUIT)) {
                    toQuit = true;
                    this.interrupted = true;
                } else {
                    setter.accept(Integer.parseInt(rangeStr));
                    toQuit = true;
                }
            } catch (NumberFormatException exception) {
                log.debug("Exception in getting input from console", exception);
                log.warn("Input not formatted Correctly, please try again");
            }
        }
    }

    private void setBoardSize() {
        boolean toQuit = false;
        while (!toQuit) {
            String boardSizeStr = Utils.getLineFromConsole();
            try {
                if (boardSizeStr.equals(Constants.OPTION_QUIT)) {
                    toQuit = true;
                    this.interrupted = true;
                } else {
                    String[] splits = boardSizeStr.split(Constants.TICKET_SIZE_SPLIT_STR);
                    if (splits.length != 2) {
                        throw new InputMismatchException("Input String is not properly formatted");
                    }
                    boardSize = new int[Constants.TICKET_DIMENSION_SIZE];
                    boardSize[0] = Integer.parseInt(splits[0].trim());
                    boardSize[1] = Integer.parseInt(splits[1].trim());
                    toQuit = true;
                }
            } catch (NumberFormatException numberFormatException) {
                log.debug("Exception in getting input from console ", numberFormatException);
                log.warn("Input does not have integers formatted Correctly, please try again");
            } catch (InputMismatchException inputMismatchException) {
                log.debug("Exception in getting input from console ", inputMismatchException);
                log.warn("Input does not have size String formatted Correctly, please try again");
            }
        }
    }

}
