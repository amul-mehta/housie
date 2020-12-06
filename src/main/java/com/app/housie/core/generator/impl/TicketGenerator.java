package com.app.housie.core.generator.impl;

import com.app.housie.core.impl.ConsoleInputGameConfig;
import com.app.housie.model.Block;
import com.app.housie.model.Player;
import com.app.housie.model.Ticket;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class TicketGenerator {
    private final ConsoleInputGameConfig gameConfig;

    TicketGenerator(ConsoleInputGameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public Ticket generate(Player player) {
        int[] boardSize = gameConfig.getBoardSize();
        int rowCount = boardSize[0];
        int columnCount = boardSize[1];
        Block[][] board = new Block[rowCount][columnCount];
        NumberGenerator valueGenerator = new NumberGenerator(1, gameConfig.getEndRange());

        for (int i = 0; i < board.length; i++) {
            Block[] row = board[i];
            Arrays.fill(row, Block.builder().build());
            int rowSize = row.length;
            NumberGenerator positionGenerator = GeneratorFactory.getNumberGenerator(0, rowSize - 1);

            for (int j = 0; j < gameConfig.getNumPerRow(); j++) {
                int column = positionGenerator.generate();
                Block block = Block.builder().number(valueGenerator.generate()).build();
                board[i][column] = block;
            }
        }
        log.debug("Completed Creating ticket for player {}", player.getName());
        return Ticket.builder()
                .content(board)
                .player(player)
                .build();
    }

}
