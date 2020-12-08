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

/**
 * This class implements a way to set the input parameters by getting input user from console
 * User will be promp
 */
@Slf4j
@Getter
@Setter(AccessLevel.PRIVATE)
public class ConsoleInputGameConfig implements GameConfig<HousieParams> {
    private boolean interrupted;
    private HousieParams params;
    @Getter(AccessLevel.PRIVATE)
    private Scanner inputScanner;

    public ConsoleInputGameConfig(Scanner inputScanner){
        this.inputScanner = inputScanner;
    }



    /**
     * this function populates the parameters value by taking input from user
     * the function will keep trying to get input from the user until either
     *  - a valid input combination is received
     *  - user interrupts by entering quit option
     *  In case of user chooses to quit, {@link isInterrupted()} will return true
     */
    @Override
    public void init() {
        boolean toQuit = false;
        while (!toQuit) {
            log.info("Enter the number range (1-n)");
            int maxNumRange = getIntegerValue();
            if (isInterrupted())
                return;
            log.info("Enter Number of Players playing the game");
            int maxPlayers = getIntegerValue();
            if (isInterrupted())
                return;
            log.info("Enter the ticket size in  \"<ROW_SIZE>X<COLUMN_SIZE>\" format (defaults to 3 X 10, press return to keep default value)");
            int[] ticketSize = getTicketSize();
            if (isInterrupted())
                return;
            log.info("Enter Numbers per Row");
            int maxNumsPerRow = getIntegerValue();
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


    /**
     * This function gets the input from the scanner until a valid integer value or
     * the Quit option is returned by the scanner.
     * In case the quit option is received, {@link isInteruppted()} will return true
     * and the returned value should not be used by the caller
     * @return integer value received from scanner
     */
    private int getIntegerValue() {
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

    /**
     * This function gets the input from the scanner until a properly formatted ticket size is received or
     * the Quit option is returned by the scanner.
     * In case the quit option is received, {@link interrupted} will return true
     * and the returned value should not be used by the caller
     * @return size of ticket received from scanner
     */
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
