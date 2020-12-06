package com.app.housie.core.impl;

import com.app.housie.core.combination.WinningCombination;
import com.app.housie.core.combination.impl.EarlyFive;
import com.app.housie.core.combination.impl.FullHouse;
import com.app.housie.core.combination.impl.TopLine;
import com.app.housie.model.Block;
import com.app.housie.model.HousieTicket;
import com.app.housie.model.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class HousieGameStateTest {

    private static final List<WinningCombination> TEST_WINNING_COMBINATION_LIST = Arrays.asList(new TopLine(), new EarlyFive(), new FullHouse());


    public List<HousieTicket> initTicketsForTwoPlayers() {
        Block[][] testTicket1 = new Block[][]{
                {Block.builder().number(11).selected(false).build(), Block.builder().selected(false).build(), Block.builder().number(12).selected(false).build(), Block.builder().selected(false).build(), Block.builder().number(13).selected(false).build()},
                {Block.builder().number(21).selected(false).build(), Block.builder().selected(false).build(), Block.builder().number(22).selected(false).build(), Block.builder().selected(false).build(), Block.builder().number(23).selected(false).build()},
                {Block.builder().number(31).selected(false).build(), Block.builder().selected(false).build(), Block.builder().number(32).selected(false).build(), Block.builder().selected(false).build(), Block.builder().number(33).selected(false).build()},
        };
        HousieTicket housieTicket1 = HousieTicket.builder().player(Player.builder().name("TestPlayer1").build()).ticket(testTicket1).build();
        Block[][] testTicket2 = new Block[][]{
                {Block.builder().number(11).selected(false).build(), Block.builder().selected(false).build(), Block.builder().number(3213).selected(false).build(), Block.builder().selected(false).build(), Block.builder().number(434).selected(false).build()},
                {Block.builder().number(21).selected(false).build(), Block.builder().selected(false).build(), Block.builder().number(12).selected(false).build(), Block.builder().selected(false).build(), Block.builder().number(13).selected(false).build()},
                {Block.builder().number(333).selected(false).build(), Block.builder().selected(false).build(), Block.builder().number(6666).selected(false).build(), Block.builder().selected(false).build(), Block.builder().number(555).selected(false).build()},
        };
        HousieTicket housieTicket2 = HousieTicket.builder().player(Player.builder().name("TestPlayer2").build()).ticket(testTicket2).build();
        return Arrays.asList(housieTicket1, housieTicket2);

    }

    @Test
    public void housieGameStateTest_PlayerOneWinsAllCombinations(){
        List<HousieTicket> housieTickets = initTicketsForTwoPlayers();
        HousieGameState testState = new HousieGameState(TEST_WINNING_COMBINATION_LIST,housieTickets);
        testState.updateState(11);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(0, testState.getCurrentState().size());
        testState.updateState(12);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(0, testState.getCurrentState().size());
        testState.updateState(13);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(1, testState.getCurrentState().size());
        Assert.assertTrue(testState.getCurrentState().containsKey(TEST_WINNING_COMBINATION_LIST.get(0)));
        Assert.assertEquals(housieTickets.get(0).getPlayer(), testState.getCurrentState().get(TEST_WINNING_COMBINATION_LIST.get(0)));
        testState.updateState(21);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(1, testState.getCurrentState().size());
        testState.updateState(22);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(2, testState.getCurrentState().size());
        Assert.assertTrue(testState.getCurrentState().containsKey(TEST_WINNING_COMBINATION_LIST.get(1)));
        Assert.assertEquals(housieTickets.get(0).getPlayer(), testState.getCurrentState().get(TEST_WINNING_COMBINATION_LIST.get(1)));
        testState.updateState(23);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(2, testState.getCurrentState().size());
        testState.updateState(31);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(2, testState.getCurrentState().size());
        testState.updateState(32);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(2, testState.getCurrentState().size());
        testState.updateState(33);
        Assert.assertTrue(testState.isCompleted());
        Assert.assertEquals(3, testState.getCurrentState().size());
        Assert.assertTrue(testState.getCurrentState().containsKey(TEST_WINNING_COMBINATION_LIST.get(2)));
        Assert.assertEquals(housieTickets.get(0).getPlayer(), testState.getCurrentState().get(TEST_WINNING_COMBINATION_LIST.get(2)));
        testState.printSummary();
    }

    @Test
    public void housieGameStateTest_PlayerOneWinsOneAndOtherWinsTwo(){
        List<HousieTicket> housieTickets = initTicketsForTwoPlayers();
        HousieGameState testState = new HousieGameState(TEST_WINNING_COMBINATION_LIST,housieTickets);
        testState.updateState(11);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(0, testState.getCurrentState().size());
        testState.updateState(12);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(0, testState.getCurrentState().size());
        testState.updateState(13);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(1, testState.getCurrentState().size());
        Assert.assertTrue(testState.getCurrentState().containsKey(TEST_WINNING_COMBINATION_LIST.get(0)));
        Assert.assertEquals(housieTickets.get(0).getPlayer(), testState.getCurrentState().get(TEST_WINNING_COMBINATION_LIST.get(0)));
        testState.updateState(3213);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(1, testState.getCurrentState().size());
        testState.updateState(434);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(2, testState.getCurrentState().size());
        Assert.assertTrue(testState.getCurrentState().containsKey(TEST_WINNING_COMBINATION_LIST.get(1)));
        Assert.assertEquals(housieTickets.get(1).getPlayer(), testState.getCurrentState().get(TEST_WINNING_COMBINATION_LIST.get(1)));
        testState.updateState(333);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(2, testState.getCurrentState().size());
        testState.updateState(6666);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(2, testState.getCurrentState().size());
        testState.updateState(21);
        Assert.assertFalse(testState.isCompleted());
        Assert.assertEquals(2, testState.getCurrentState().size());
        testState.updateState(555);
        Assert.assertTrue(testState.isCompleted());
        Assert.assertEquals(3, testState.getCurrentState().size());
        Assert.assertTrue(testState.getCurrentState().containsKey(TEST_WINNING_COMBINATION_LIST.get(2)));
        Assert.assertEquals(housieTickets.get(1).getPlayer(), testState.getCurrentState().get(TEST_WINNING_COMBINATION_LIST.get(2)));
        testState.printSummary();
    }


}
