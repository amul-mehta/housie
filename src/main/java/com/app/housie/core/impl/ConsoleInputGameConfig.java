package com.app.housie.core.impl;

import com.app.housie.commons.Constants;
import com.app.housie.core.GameConfig;
import com.app.housie.model.HousieParams;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
@Setter(AccessLevel.PRIVATE)
public class ConsoleInputGameConfig implements GameConfig<HousieParams> {
    @Getter(AccessLevel.PUBLIC)
    private boolean interrupted;
    @Getter(AccessLevel.PUBLIC)
    private HousieParams params;
    @Getter(AccessLevel.PRIVATE)
    private Scanner inputScanner;

    @Override
    public void init(Scanner scanner) {
        boolean toQuit = false;
        while (!toQuit) {
            setInputScanner(scanner);
            log.info("Enter the number range (1-n)");
            int maxNumRange = getIntegerValueFromConsole();
            if (isInterrupted())
                return;
            log.info("Enter Number of Players playing the game");
            int maxPlayers = getIntegerValueFromConsole();
            if (isInterrupted())
                return;
            log.info("Enter the ticket size in  \"<ROW_SIZE>X<COLUMN_SIZE>\" format (defaults to 3 X 10, press return to keep default value)");
            int[] ticketSize = getTicketSize();
            if (isInterrupted())
                return;
            log.info("Enter Numbers per Row");
            int maxNumsPerRow = getIntegerValueFromConsole();
            if (!isInterrupted()) {
                if (ticketSize.length == 0)
                    setParams(
                            HousieParams.builder()
                                    .maxNumRange(maxNumRange)
                                    .numOfPlayers(maxPlayers)
                                    .numPerRow(maxNumsPerRow)
                                    .build());
                else
                    setParams(
                            HousieParams.builder()
                                    .maxNumRange(maxNumRange)
                                    .numOfPlayers(maxPlayers)
                                    .numPerRow(maxNumsPerRow)
                                    .ticketSize(ticketSize)
                                    .build());
                toQuit = getParams().isValid();
                if (!toQuit)
                    log.warn("The entered config is not valid, cannot proceed, please try again");
            } else {
                toQuit = true;
            }
        }
    }


    private int getIntegerValueFromConsole() {
        boolean toQuit = false;
        int val = -1;
        while (!toQuit) {
            String intStr = getInputScanner().nextLine();
            try {
                if (intStr.equals(Constants.OPTION_QUIT)) {
                    toQuit = true;
                    setInterrupted(true);
                } else {
                    val = Integer.parseInt(intStr);
                    toQuit = true;
                }
            } catch (NumberFormatException exception) {
                log.debug("Exception in getting input from console", exception);
                log.warn("Input not formatted Correctly, please try again");
            }
        }
        return val;
    }

    private int[] getTicketSize() {
        boolean toQuit = false;
        int[] ticketSize = new int[]{};
        while (!toQuit) {
            String boardSizeStr = getInputScanner().nextLine();
            try {
                if (boardSizeStr.equals(Constants.OPTION_QUIT)) {
                    toQuit = true;
                    setInterrupted(true);
                } else {
                    // use default value
                    if (boardSizeStr.trim().length() == 0) {
                        toQuit = true;
                        continue;
                    }
                    String[] splits = boardSizeStr.split(Constants.TICKET_SIZE_SPLIT_STR);
                    if (splits.length != 2) {
                        throw new InputMismatchException("Input String is not properly formatted");
                    }
                    ticketSize = new int[Constants.TICKET_DIMENSION_SIZE];
                    ticketSize[0] = Integer.parseInt(splits[0].trim());
                    ticketSize[1] = Integer.parseInt(splits[1].trim());
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
        return ticketSize;
    }


}
