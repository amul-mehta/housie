package com.app.housie.core;

import com.app.housie.commons.Constants;
import com.app.housie.commons.Utils;
import com.app.housie.core.combination.EarlyFive;
import com.app.housie.core.combination.TopLine;
import com.app.housie.core.combination.WinningCombination;
import com.app.housie.generator.NumberGenerator;
import com.app.housie.generator.TicketGenerator;
import com.app.housie.model.Player;
import com.app.housie.model.Ticket;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class HousieGame implements Game {

    private final GameState gameState;
    private final GameConfig gameConfig;
    private final TicketGenerator ticketGenerator;

    public HousieGame() {
        this.gameConfig = new GameConfig();
        this.ticketGenerator = new TicketGenerator(gameConfig);

        List<Ticket> tickets =
                IntStream.range(0, gameConfig.getNumOfPlayers())
                        .mapToObj(this::generateTicket)
                        .collect(Collectors.toList());

        log.info("**** Tickets Created Successfully ****");

        List<WinningCombination> winningCombinations = Arrays.asList(new TopLine(), new EarlyFive());

        this.gameState = new GameState(winningCombinations, tickets);
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
        log.debug("Starting the game!!");

        boolean toQuit = false;
        NumberGenerator valueGenerator = new NumberGenerator(0, gameConfig.getEndRange());

        while (!toQuit) {
            log.info("Press 'N' generate a new Number");
            String input = Utils.getLineFromConsole();
            switch (input) {
                // TODO: Make this quittable at all the time not just when playing
                case Constants.OPTION_QUIT:
                    toQuit = true;
                    break;
                case Constants.OPTION_NEW_NUMBER:
                    int currentNumber = valueGenerator.getRandomInt();
                    log.info("Next number is: {}", currentNumber);
                    toQuit = gameState.updateState(currentNumber);
                    break;
                default:
                    log.error("Un-recognized option, please try again");
                    break;
            }
        }

        gameState.printSummary();
    }


}

