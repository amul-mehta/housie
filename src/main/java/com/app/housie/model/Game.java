package com.app.housie.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
public class Game {
    GameConfig gameConfig;
    List<Ticket> tickets;
    List<Combination> winningCombinations;
    GameState gameState;

    public Game() {
        this.gameConfig = new GameConfig();
        this.winningCombinations = Arrays.asList(new TopLine());
        this.gameState = new GameState();
        initialize();
    }

    private void initialize() {
        int numOfPlayers = gameConfig.getNumOfPlayers();
        this.tickets = IntStream.range(0, numOfPlayers)
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
                    return Ticket.builder().content(board).player(pl).build();
                }).collect(Collectors.toList());
    }

    public void play(){

        while (!gameState.isCompleted()){
         String input = "";

         switch (input){
             case "Q":
                 break;
             case "N":
                 break;
             default:
                 break;
         }


        }
    }


}

