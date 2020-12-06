package com.app.housie.core.impl;

import com.app.housie.commons.Constants;
import com.app.housie.commons.Utils;
import com.app.housie.core.Game;
import com.app.housie.core.combination.EarlyFive;
import com.app.housie.core.combination.TopLine;
import com.app.housie.core.combination.WinningCombination;
import com.app.housie.core.generator.impl.GeneratorFactory;
import com.app.housie.core.generator.impl.NumberGenerator;
import com.app.housie.core.generator.impl.TicketGenerator;
import com.app.housie.model.Player;
import com.app.housie.model.Ticket;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class HousieGame implements Game {
    private final ConsoleInputGameConfig gameConfig;
    private TicketGenerator ticketGenerator;
    private HousieGameState gameState;
    private boolean toQuit;

    public HousieGame() {
        this.gameConfig = new ConsoleInputGameConfig();
        this.gameConfig.init();
        this.toQuit = gameConfig.isInterrupted();

        if (!toQuit) {
            this.ticketGenerator = GeneratorFactory.getTicketGenerator(gameConfig);

            List<Ticket> tickets =
                    IntStream.range(0, gameConfig.getNumOfPlayers())
                            .mapToObj(this::generateTicket)
                            .collect(Collectors.toList());

            log.info("**** Tickets Created Successfully ****");

            List<WinningCombination> winningCombinations = Arrays.asList(new TopLine(), new EarlyFive());

            this.gameState = new HousieGameState(winningCombinations, tickets);
        }
    }

    private Ticket generateTicket(int playerCount) {
        Player player =
                Player.builder()
                        .name("Player#" + playerCount)
                        .build();

        return ticketGenerator.generate(player);
    }

    @Override
    public void play() {
        boolean gameFinished = false;
        NumberGenerator valueGenerator = GeneratorFactory.getNumberGenerator(0, gameConfig.getEndRange());

        while (!this.toQuit) {
            log.info("Press 'N' generate a new Number");
            String input = Utils.getLineFromConsole();
            switch (input) {
                case Constants.OPTION_QUIT:
                    this.toQuit = true;
                    break;
                case Constants.OPTION_NEW_NUMBER:
                    int currentNumber = valueGenerator.generate();
                    log.info("Next number is: {}", currentNumber);
                    this.toQuit = gameFinished = gameState.updateState(currentNumber);
                    break;
                default:
                    log.error("Un-recognized option, please try again");
                    break;
            }
        }
        if (gameFinished)
            gameState.printSummary();
    }


}

