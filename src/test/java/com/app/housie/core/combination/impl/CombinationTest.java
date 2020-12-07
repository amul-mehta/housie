package com.app.housie.core.combination.impl;

import com.app.housie.TestUtils;
import com.app.housie.commons.Constants;
import com.app.housie.core.combination.WinningCombination;
import com.app.housie.model.Block;
import com.app.housie.model.HousieTicket;
import com.app.housie.model.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

public class CombinationTest {


    @Test
    public void topLineTest_correctName() {
        WinningCombination winningCombination = new TopLine();
       Assert.assertEquals(winningCombination.getName(), Constants.TOP_LINE_NAME);
    }

    @Test
    public void earlyFiveTest_correctName() {
        WinningCombination winningCombination = new EarlyFive();
        Assert.assertEquals(winningCombination.getName(), Constants.EARLY_FIVE_NAME);
    }

    @Test
    public void FullHouseTest_correctName() {
        WinningCombination winningCombination = new FullHouse();
        Assert.assertEquals(winningCombination.getName(), Constants.FULL_HOUSE_NAME);
    }

    @Test
    public void topLineTest_winningTicket() {
        WinningCombination topLineWinningCombination = new TopLine();
        Block[][] sampleTicket = TestUtils.getSampleTicket();
        for (Block block : sampleTicket[0]) {
            if (Objects.nonNull(block))
                block.setSelected(true);
        }
        HousieTicket housieTicket = HousieTicket.builder().ticket(sampleTicket).player(Player.builder().name("SamplePlayer").build()).build();
        Assert.assertTrue(topLineWinningCombination.evaluate(housieTicket));
    }

    @Test
    public void topLineTest_notWinningTicket() {
        WinningCombination topLineWinningCombination = new TopLine();
        Block[][] sampleTicket2 = TestUtils.getSampleTicket();
        HousieTicket housieTicket = HousieTicket.builder().ticket(sampleTicket2).player(Player.builder().name("SamplePlayer").build()).build();
        Assert.assertFalse(topLineWinningCombination.evaluate(housieTicket));
    }

    @Test
    public void FullHouseTest_winningTicket() {
        WinningCombination fullHouseWinningCombination = new FullHouse();
        Block[][] sampleTicket = TestUtils.getSampleTicket();
        for (Block[] blocks : sampleTicket) {
            for (Block block : blocks) {
                if (Objects.nonNull(block))
                    block.setSelected(true);
            }
        }
        HousieTicket housieTicket = HousieTicket.builder().ticket(sampleTicket).player(Player.builder().name("SamplePlayer").build()).build();
        Assert.assertTrue(fullHouseWinningCombination.evaluate(housieTicket));
    }

    @Test
    public void FullHouseTest_notWinningTicket1() {
        WinningCombination fullHouseWinningCombination = new FullHouse();
        Block[][] sampleTicket = TestUtils.getSampleTicket();
        for (Block block : sampleTicket[0]) {
            if (Objects.nonNull(block))
                block.setSelected(true);
        }
        HousieTicket housieTicket = HousieTicket.builder().ticket(sampleTicket).player(Player.builder().name("SamplePlayer").build()).build();
        Assert.assertFalse(fullHouseWinningCombination.evaluate(housieTicket));
    }

    @Test
    public void FullHouseTest_notWinningTicket2() {
        WinningCombination fullHouseWinningCombination = new FullHouse();
        Block[][] sampleTicket = TestUtils.getSampleTicket();
        HousieTicket housieTicket = HousieTicket.builder().ticket(sampleTicket).player(Player.builder().name("SamplePlayer").build()).build();
        Assert.assertFalse(fullHouseWinningCombination.evaluate(housieTicket));
    }


    @Test
    public void EarlyFiveTest_winningTicket() {
        WinningCombination earlyFiveWinningCombination = new EarlyFive();
        Block[][] sampleTicket = TestUtils.getSampleTicket();
        int count = 0;
        for (Block[] blocks : sampleTicket) {
            for (Block block : blocks) {
                if (Objects.nonNull(block)) {
                    block.setSelected(true);
                    if (++count == Constants.EARLY_FIVE_THRESHOLD)
                        break;
                }
            }
            if (count == Constants.EARLY_FIVE_THRESHOLD)
                break;
        }
        HousieTicket housieTicket = HousieTicket.builder().ticket(sampleTicket).player(Player.builder().name("SamplePlayer").build()).build();
        Assert.assertTrue(earlyFiveWinningCombination.evaluate(housieTicket));
    }

    @Test
    public void EarlyFiveTest_notWinningTicket1() {
        WinningCombination earlyFiveWinningCombination = new EarlyFive();
        Block[][] sampleTicket = TestUtils.getSampleTicket();
        HousieTicket housieTicket = HousieTicket.builder().ticket(sampleTicket).player(Player.builder().name("SamplePlayer").build()).build();
        Assert.assertFalse(earlyFiveWinningCombination.evaluate(housieTicket));
    }

    @Test
    public void EarlyFiveTest_notWinningTicket2() {
        WinningCombination earlyFiveWinningCombination = new EarlyFive();
        Block[][] sampleTicket = TestUtils.getSampleTicket();
        int count = 0;
        for (Block[] blocks : sampleTicket) {
            for (Block block : blocks) {
                if (Objects.nonNull(block)) {
                    block.setSelected(true);
                    if (++count == 4)
                        break;
                }
            }
            if (count == 4)
                break;
        }
        HousieTicket housieTicket = HousieTicket.builder().ticket(sampleTicket).player(Player.builder().name("SamplePlayer").build()).build();
        Assert.assertFalse(earlyFiveWinningCombination.evaluate(housieTicket));
    }


}
