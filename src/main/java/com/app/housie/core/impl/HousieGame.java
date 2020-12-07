package com.app.housie.core.impl;

import com.app.housie.commons.Constants;
import com.app.housie.core.Game;
import com.app.housie.core.GameConfig;
import com.app.housie.core.GameState;
import com.app.housie.core.combination.WinningCombination;
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

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
@Slf4j
@Getter(AccessLevel.PACKAGE)
public class HousieGame implements Game {
    private static final List<WinningCombination> WINNING_COMBINATIONS = Arrays.asList(new TopLine(), new EarlyFive(), new FullHouse());
    private static final Scanner CONSOLE_INPUT_SCANNER = new Scanner(System.in);

    private GameConfig<HousieParams> gameConfig;
    private Generator<Block[][]> ticketGenerator;
    private GameState<Integer> gameState;
    private Caller caller;
    private boolean toQuit;

    /**
     *
     */
    @Override
    public boolean init() {
        this.gameConfig = new ConsoleInputGameConfig();
        this.getGameConfig().init(getConsoleInputScanner());
        this.toQuit = getGameConfig().isInterrupted();

        if (!toQuit) {
            this.ticketGenerator = GeneratorFactory.getTicketGenerator(getGameConfig().getParams());

            List<HousieTicket> housieTickets =
                    IntStream.range(0, getGameConfig().getParams().getNumOfPlayers())
                            .mapToObj(this::generateHousieTicket)
                            .collect(Collectors.toList());

            log.info("**** Tickets Created Successfully ****");

            this.gameState = new HousieGameState(WINNING_COMBINATIONS, housieTickets);

            NumberGenerator randomNumberGenerator =
                    GeneratorFactory.getNumberGenerator(1, getGameConfig().getParams().getMaxNumRange());
            this.caller =
                    Caller.builder()
                            .name("Caller")
                            .randomNumberGenerator(randomNumberGenerator)
                            .build();
        }
        return !toQuit;
    }


    /**
     *
     */
    @Override
    public void play() {
        boolean gameFinished = false;
        while (!isToQuit()) {
            log.info("Press 'N' generate a new Number");
            String input = getConsoleInputScanner().nextLine();
            switch (input) {
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
        }
        if (gameFinished) {
            log.info("**** Game Over ****");
            getGameState().printSummary();
        }
    }

    /**
     * @param playerCount
     * @return
     */
    private HousieTicket generateHousieTicket(int playerCount) {
        Player player =
                Player.builder()
                        .name(Constants.PLAYER_NAME_PREFIX + playerCount)
                        .build();
        Block[][] ticket = getTicketGenerator().generate();
        log.debug("Player {},Ticket {}", player.getName(), ticket);
        return HousieTicket.builder()
                .player(player)
                .ticket(ticket)
                .build();
    }

    public static Scanner getConsoleInputScanner() {
        return CONSOLE_INPUT_SCANNER;
    }


}

