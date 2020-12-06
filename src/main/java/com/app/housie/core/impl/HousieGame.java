package com.app.housie.core.impl;

import com.app.housie.commons.Constants;
import com.app.housie.commons.Utils;
import com.app.housie.core.Game;
import com.app.housie.core.GameConfig;
import com.app.housie.core.GameState;
import com.app.housie.core.combination.WinningCombination;
import com.app.housie.core.combination.impl.EarlyFive;
import com.app.housie.core.combination.impl.FullHouse;
import com.app.housie.core.combination.impl.TopLine;
import com.app.housie.core.generator.Generator;
import com.app.housie.core.generator.impl.GeneratorFactory;
import com.app.housie.model.Block;
import com.app.housie.model.HousieParams;
import com.app.housie.model.HousieTicket;
import com.app.housie.model.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class HousieGame implements Game {
    private static final List<WinningCombination> WINNING_COMBINATIONS = Arrays.asList(new TopLine(), new EarlyFive(), new FullHouse());
    private static final Scanner CONSOLE_INPUT_SCANNER = new Scanner(System.in);

    private final GameConfig<HousieParams> gameConfig;
    private Generator<Block[][]> ticketGenerator;
    private GameState<Integer> gameState;
    private boolean toQuit;

    public HousieGame() {
        this.gameConfig = new ConsoleInputGameConfig();
        this.gameConfig.init(CONSOLE_INPUT_SCANNER);
        this.toQuit = gameConfig.isInterrupted();

        if (!toQuit) {
            HousieParams housieParams = gameConfig.getParams();
            this.ticketGenerator = GeneratorFactory.getTicketGenerator(housieParams);

            List<HousieTicket> housieTickets =
                    IntStream.range(0, housieParams.getNumOfPlayers())
                            .mapToObj(this::generateHousieTicket)
                            .collect(Collectors.toList());

            log.info("**** Tickets Created Successfully ****");

            this.gameState = new HousieGameState(WINNING_COMBINATIONS, housieTickets);
        }
    }

    private HousieTicket generateHousieTicket(int playerCount) {
        Player player =
                Player.builder()
                        .name(Constants.PLAYER_NAME_PREFIX + playerCount)
                        .build();
        Block[][] ticket = ticketGenerator.generate();
        log.info("Player {},Ticket {}", player.getName(), ticket);
        return HousieTicket.builder()
                .player(player)
                .ticket(ticket)
                .build();
    }

    @Override
    public void play() {
        boolean gameFinished = false;
        Generator<Integer> ticketRandomNumberGenerator =
                GeneratorFactory.getNumberGenerator(1, gameConfig.getParams().getMaxNumRange());

        while (!this.toQuit) {
            log.info("Press 'N' generate a new Number");
            String input = Utils.getLineFromConsole(CONSOLE_INPUT_SCANNER);
            switch (input) {
                case Constants.OPTION_QUIT:
                    this.toQuit = true;
                    break;
                case Constants.OPTION_NEW_NUMBER:
                    int currentNumber = ticketRandomNumberGenerator.generate();
                    log.info("Next number is: {}", currentNumber);
                    gameState.updateState(currentNumber);
                    this.toQuit = gameFinished = gameState.isCompleted();
                    break;
                default:
                    log.error("Un-recognized option, please try again");
                    break;
            }
        }
        if (gameFinished) {
            log.info("**** Game Over ****");
            gameState.printSummary();
        }
    }


}

