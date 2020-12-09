package com.app.housie.core.impl;

import com.app.housie.commons.Constants;
import com.app.housie.core.Game;
import com.app.housie.core.GameConfig;
import com.app.housie.core.GameState;
import com.app.housie.core.combination.impl.EarlyFive;
import com.app.housie.core.combination.impl.FullHouse;
import com.app.housie.core.combination.impl.TopLine;
import com.app.housie.core.generator.Generator;
import com.app.housie.core.generator.impl.GeneratorFactory;
import com.app.housie.core.generator.impl.NumberGenerator;
import com.app.housie.model.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This is the driver class that is responsible for initializing the housie game parameters and ultimately
 * controlling the gameplay based on the user input
 * The game is configured to have three winning combinations one or more players can fall into :
 * {@link TopLine}, {@link EarlyFive}, {@link FullHouse}
 * The game parameters are requested from the user via the console.
 * At the end of successful game a summary of players and their winning combination is printed
 */
@Slf4j
@Getter(AccessLevel.PACKAGE)
public class HousieGame implements Game {

    private GameConfig<HousieParams> gameConfig;
    private Generator<Block[][]> ticketGenerator;
    private GameState<Integer> gameState;
    private Caller caller;
    private boolean toQuit;

    /**
     * initializes the Game by :
     *  - initializing game parameters by taking input from user via console
     *  - generating {@link HousieTicket} for each {@link Player} defined in the {@link HousieParams#getNumOfPlayers()}
     *  - initializing game state to keep track of the players and their winning combinations
     *  - initializes {@link Caller} that is responsible for generating Random Numbers
     *  User has an option to interrupt the initialization process when it is being asked for input
     *  by entering {@link Constants#OPTION_QUIT}
     * @return if the initialization was interrupted
     */
    @Override
    public boolean init() {
        initializeGameConfig();

        if (!isToQuit()) {
            List<HousieTicket> housieTickets = initializeHousieTickets();
            initializeGameState(housieTickets);
            initializeCaller();
        }
        return !isToQuit();
    }

    /**
     * this function initializes the game state by passing the allowed winning combinations
     * and the initial generated tickets
     * @param housieTickets housie tickets for each player to initialize the state
     */
    private void initializeGameState(List<HousieTicket> housieTickets) {
        this.gameState = new LocalGameState(Constants.WINNING_COMBINATIONS, housieTickets);
    }

    /**
     * this function initializes the game config making the user to input the params from console
     * and sets the {@link this#toQuit} flag if the user interrupted before completing to define the game params
     */
    private void initializeGameConfig() {
        this.gameConfig = new ConsoleInputGameConfig(getConsoleInputScanner());
        getGameConfig().init();
        this.toQuit = getGameConfig().isInterrupted();
    }

    /**
     * initializes the {@link Caller} by assigning a random number generator
     */
    private void initializeCaller() {
        NumberGenerator randomNumberGenerator =
                GeneratorFactory.getNumberGenerator(Constants.DEFAULT_MIN_NUM_RANGE,
                        getGameConfig().getParams().getMaxNumRange());
        this.caller =
                Caller.builder()
                        .name(Constants.CALLER)
                        .randomNumberGenerator(randomNumberGenerator)
                        .build();
    }

    /**
     * initializes the ticket generator with game params and generates ticket for each of the player
     * @return Housie tickets for all the players
     */
    private List<HousieTicket> initializeHousieTickets() {
        this.ticketGenerator = GeneratorFactory.getTicketGenerator(getGameConfig().getParams());

        List<HousieTicket> housieTickets =
                IntStream.rangeClosed(1, getGameConfig().getParams().getNumOfPlayers())
                        .mapToObj(this::generateHousieTicket)
                        .collect(Collectors.toList());

        log.info("**** Tickets Created Successfully ****");
        return housieTickets;
    }

    /**
     * @param playerCount number of tickets to create
     * @return housie ticket
     */
    private HousieTicket generateHousieTicket(int playerCount) {
        Player player =
                Player.builder()
                        .name(Constants.PLAYER_NAME_PREFIX + playerCount)
                        .build();
        Block[][] ticket = getTicketGenerator().generate();
        log.debug("Player {}, Ticket {}", player.getName(), ticket);
        return HousieTicket.builder()
                .player(player)
                .ticket(ticket)
                .build();
    }

    /**
     * this function controls the Gameplay based on user's input
     * In case of all the combinations have been claimed, per player game summary is printed.
     */
    @Override
    public void play() {
        boolean gameFinished = false;
        while (!isToQuit()) {
            log.info("Press '{}' generate a new Number", Constants.OPTION_NEW_NUMBER);
            String input = getConsoleInputScanner().nextLine();
            gameFinished = handleInput(input);
        }
        if (gameFinished) {
            log.info("**** Game Over ****");
            getGameState().printSummary();
        }
    }

    /**
     * if user inputs {@link Constants#OPTION_NEW_NUMBER} then random number is called by the defined {@link Caller}
     * instance and state is updated based on the number generated.
     * if the user inputs {@link Constants#OPTION_QUIT} then game is terminated
     * @param input user input
     * @return if all the winning combinations have been claimed
     */
    private boolean handleInput(String input) {
        boolean gameFinished = false;
        switch (input.toUpperCase()) {
            case Constants.OPTION_QUIT:
                this.toQuit = true;
                break;
            case Constants.OPTION_NEW_NUMBER:
                int currentNumber = getCaller().callNumber();
                log.info("Next number is: {}", currentNumber);
                getGameState().updateState(currentNumber);
                this.toQuit = gameFinished = getGameState().isCompleted();
                break;
            default:
                log.error("Un-recognized option, please try again");
                break;
        }
        return gameFinished;
    }

    /**
     * @return console scanner instance for user input
     */
    public static Scanner getConsoleInputScanner() {
        return Constants.CONSOLE_INPUT_SCANNER;
    }

}

