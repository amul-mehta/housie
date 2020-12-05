package com.app.housie.core;

import com.app.housie.commons.Constants;
import com.app.housie.commons.Utils;
import com.app.housie.core.combination.EarlyFive;
import com.app.housie.core.combination.TopLine;
import com.app.housie.core.combination.WinningCombination;
import com.app.housie.generator.NumberGenerator;
import com.app.housie.model.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
@Slf4j
public class Game {

    GameConfig gameConfig;
    List<Ticket> tickets;
    List<WinningCombination> winningWinningCombinations;
    GameState gameState;

    public Game() {
        this.gameConfig = new GameConfig();
        this.winningWinningCombinations = Arrays.asList(new TopLine(), new EarlyFive());
        initialize();
    }

    private void initialize() {
        int numOfPlayers = gameConfig.getNumOfPlayers();
        this.tickets =
                IntStream.range(0, numOfPlayers)
                        .parallel()
                        .mapToObj(t -> {
                            Player pl = Player.builder().name("Player#" + t).build();
                            Block[][] board = new Block[gameConfig.getBoardSize()[0]][gameConfig.getBoardSize()[1]];
                            NumberGenerator valueGenerator = new NumberGenerator(1, gameConfig.getEndRange());
                            for (int i = 0; i < board.length; i++) {
                                Block[] row = board[i];
                                Arrays.fill(row, Block.builder().build());
                                int rowSize = row.length;
                                NumberGenerator positionGenerator = new NumberGenerator(0, rowSize - 1);
                                for (int j = 0; j < gameConfig.getNumPerRow(); j++) {
                                    int column = positionGenerator.getRandomInt();
                                    Block block = Block.builder().number(valueGenerator.getRandomInt()).build();
                                    board[i][column] = block;
                                }
                            }
                            log.debug("Completed Creating ticket for player {}", t);
                            return Ticket.builder().content(board).player(pl).build();
                        }).collect(Collectors.toList());
        log.info("**** Tickets Created Successfully ****");
        this.gameState = new GameState(winningWinningCombinations, this.tickets);
    }

    public void play() {
        log.debug("Starting the game!!");
        boolean toQuit = false;
        NumberGenerator valueGenerator = new NumberGenerator(0, gameConfig.getEndRange());
        while (!toQuit) {
            log.info("Enter One Option : Valid Options are : N -> to get a new Number and Q -> to Quit");
            String input = Utils.getLineFromConsole();
            switch (input) {
                // TODO: Make this quittable at all the time not just when playing
                case Constants.QUIT:
                    toQuit = true;
                    break;
                case Constants.NEW_NUMBER:
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

