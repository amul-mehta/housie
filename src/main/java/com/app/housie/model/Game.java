package com.app.housie.model;

import com.app.housie.Utils;
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
    List<Combination> winningCombinations;
    GameState gameState;

    public Game() {
        this.gameConfig = new GameConfig();
        this.winningCombinations = Arrays.asList(new TopLine());
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
                                int rowSize = row.length;
                                NumberGenerator positionGenerator = new NumberGenerator(0, rowSize - 1);
                                for (int j = 0; j < gameConfig.getNumPerRow(); j++) {
                                    int column = positionGenerator.getRandomInt();
                                    Block block = Block.builder().number(valueGenerator.getRandomInt()).build();
                                    board[i][column] = block;
                                }
                            }
                            log.info("Completed Creating ticket for player {}", t);
                            return Ticket.builder().content(board).player(pl).build();
                        }).collect(Collectors.toList());

        this.gameState = new GameState(winningCombinations, this.tickets);
    }

    public void play() {
        log.debug("Starting the game!! ");
        boolean toQuit = false;
        NumberGenerator valueGenerator = new NumberGenerator(0, gameConfig.getEndRange());
        while (!gameState.isCompleted() || toQuit) {
            log.info("Enter somehing");
            String input = Utils.getLineFromConsole();

            switch (input) {
                case "Q":
                    toQuit = true;
                    break;
                case "N":
                    int currentNumber = valueGenerator.getRandomInt();
                    gameState.updateState(currentNumber);
                    break;
                default:
                    break;
            }


        }
    }


}

