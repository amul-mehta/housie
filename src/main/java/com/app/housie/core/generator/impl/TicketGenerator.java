package com.app.housie.core.generator.impl;

import com.app.housie.commons.Constants;
import com.app.housie.core.generator.Generator;
import com.app.housie.model.Block;
import com.app.housie.model.HousieParams;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@Getter(AccessLevel.PRIVATE)
public class TicketGenerator implements Generator<Block[][]> {
    private final HousieParams gameParams;

    TicketGenerator(HousieParams gameConfig) {
        this.gameParams = gameConfig;
    }

    public Block[][] generate() {
        int[] boardSize = getGameParams().getTicketSize();
        int rowCount = boardSize[0];
        int columnCount = boardSize[1];
        Block[][] board = new Block[rowCount][columnCount];
        Generator<Integer> valueGenerator =
                new NumberGenerator(Constants.DEFAULT_MIN_NUM_RANGE, getGameParams().getMaxNumRange());

        for (int i = 0; i < board.length; i++) {
            Block[] row = board[i];
            Arrays.fill(row, Block.builder().build());
            int rowSize = row.length;
            Generator<Integer> positionGenerator = GeneratorFactory.getNumberGenerator(0, rowSize - 1);

            for (int j = 0; j < getGameParams().getNumPerRow(); j++) {
                int column = positionGenerator.generate();
                Block block =
                        Block.builder()
                                .number(valueGenerator.generate())
                                .build();
                board[i][column] = block;
            }
        }
        return board;
    }

}
