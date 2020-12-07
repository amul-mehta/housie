package com.app.housie;

import com.app.housie.model.Block;

import java.util.Arrays;

public class TestUtils {
    public static synchronized Block[][] getSampleTicket() {
        return new Block[][]{
                {Block.builder().number(11).selected(false).build(), null, Block.builder().number(12).selected(false).build(), null, Block.builder().number(13).selected(false).build()},
                {Block.builder().number(21).selected(false).build(), null, Block.builder().number(22).selected(false).build(), null, Block.builder().number(23).selected(false).build()},
                {Block.builder().number(31).selected(false).build(), null, Block.builder().number(32).selected(false).build(), null, Block.builder().number(33).selected(false).build()},
        };
    }
}
