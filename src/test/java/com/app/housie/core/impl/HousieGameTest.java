package com.app.housie.core.impl;

import com.app.housie.commons.Constants;
import com.app.housie.core.generator.impl.TicketGenerator;
import com.app.housie.model.Caller;
import com.app.housie.model.HousieParams;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HousieGameTest {


    @Test
    public void testHousieGame_successInitializationAndQuit() {
        HousieParams housieParams = HousieParams.builder().numPerRow(3).ticketSize(new int[]{3, 5}).numOfPlayers(2).maxNumRange(100).build();

        ConsoleInputGameConfig consoleInputGameConfig = Mockito.mock(ConsoleInputGameConfig.class);
        when(consoleInputGameConfig.getParams()).thenReturn(housieParams);
        when(consoleInputGameConfig.isInterrupted()).thenReturn(false);

        TicketGenerator ticketGenerator = Mockito.mock(TicketGenerator.class);
        LocalGameState localGameState = Mockito.mock(LocalGameState.class);
        Caller caller = Mockito.mock(Caller.class);

        HousieGame housieGame = Mockito.spy(new HousieGame());
        when(housieGame.getGameConfig()).thenReturn(consoleInputGameConfig);
        when(housieGame.getTicketGenerator()).thenReturn(ticketGenerator);
        when(housieGame.getGameState()).thenReturn(localGameState);
        when(housieGame.getCaller()).thenReturn(caller);

        Assert.assertTrue(housieGame.init());

        InputStream inputStream = new ByteArrayInputStream(Constants.OPTION_QUIT.getBytes());
        try (MockedStatic<HousieGame> dummy = Mockito.mockStatic(HousieGame.class)) {
            dummy.when(HousieGame::getConsoleInputScanner).thenReturn(new Scanner(inputStream));
            housieGame.play();
            Assert.assertTrue(housieGame.isToQuit());
            Mockito.verify(localGameState, times(0)).printSummary();
            Mockito.verify(localGameState, times(0)).updateState(any());
            Mockito.verify(caller, times(0)).callNumber();
        }
    }

    @Test
    public void testHousieGame_successInitializationWinningGame() {
        HousieParams housieParams = HousieParams.builder().numPerRow(3).ticketSize(new int[]{3, 5}).numOfPlayers(2).maxNumRange(100).build();

        ConsoleInputGameConfig consoleInputGameConfig = Mockito.mock(ConsoleInputGameConfig.class);
        when(consoleInputGameConfig.getParams()).thenReturn(housieParams);
        when(consoleInputGameConfig.isInterrupted()).thenReturn(false);

        TicketGenerator ticketGenerator = Mockito.mock(TicketGenerator.class);
        LocalGameState localGameState = Mockito.mock(LocalGameState.class);
        Caller caller = Mockito.mock(Caller.class);

        HousieGame housieGame = Mockito.spy(new HousieGame());
        when(housieGame.getGameConfig()).thenReturn(consoleInputGameConfig);
        when(housieGame.getTicketGenerator()).thenReturn(ticketGenerator);
        when(housieGame.getGameState()).thenReturn(localGameState);
        when(housieGame.getCaller()).thenReturn(caller);

        Assert.assertTrue( housieGame.init());

        when(caller.callNumber()).thenReturn(10).thenReturn(11).thenReturn(12);
        when(localGameState.isCompleted()).thenReturn(false).thenReturn(false).thenReturn(true);
        InputStream inputStream = new ByteArrayInputStream("A\nN\nN\nN\n".getBytes());
        try (MockedStatic<HousieGame> dummy = Mockito.mockStatic(HousieGame.class)) {
            dummy.when(HousieGame::getConsoleInputScanner).thenReturn(new Scanner(inputStream));
            housieGame.play();
            Assert.assertTrue(housieGame.isToQuit());
            Mockito.verify(localGameState, times(1)).printSummary();
            Mockito.verify(localGameState, times(3)).updateState(any());
            Mockito.verify(caller, times(3)).callNumber();
        }
    }

    @Test
    public void testHousieGame_successInitializationQuitMidGame() {
        HousieParams housieParams = HousieParams.builder().numPerRow(3).ticketSize(new int[]{3, 5}).numOfPlayers(2).maxNumRange(100).build();

        ConsoleInputGameConfig consoleInputGameConfig = Mockito.mock(ConsoleInputGameConfig.class);
        when(consoleInputGameConfig.getParams()).thenReturn(housieParams);
        when(consoleInputGameConfig.isInterrupted()).thenReturn(false);

        TicketGenerator ticketGenerator = Mockito.mock(TicketGenerator.class);
        LocalGameState localGameState = Mockito.mock(LocalGameState.class);
        Caller caller = Mockito.mock(Caller.class);

        HousieGame housieGame = Mockito.spy(new HousieGame());
        when(housieGame.getGameConfig()).thenReturn(consoleInputGameConfig);
        when(housieGame.getTicketGenerator()).thenReturn(ticketGenerator);
        when(housieGame.getGameState()).thenReturn(localGameState);
        when(housieGame.getCaller()).thenReturn(caller);


        Assert.assertTrue(housieGame.init());

        when(caller.callNumber()).thenReturn(10).thenReturn(11).thenReturn(12);
        when(localGameState.isCompleted()).thenReturn(false).thenReturn(false).thenReturn(false);
        InputStream inputStream = new ByteArrayInputStream("N\nN\nN\nQ".getBytes());
        try (MockedStatic<HousieGame> dummy = Mockito.mockStatic(HousieGame.class)) {
            dummy.when(HousieGame::getConsoleInputScanner).thenReturn(new Scanner(inputStream));
            housieGame.play();
            Assert.assertTrue(housieGame.isToQuit());
            Mockito.verify(localGameState, times(0)).printSummary();
            Mockito.verify(localGameState, times(3)).updateState(any());
            Mockito.verify(caller, times(3)).callNumber();
        }
    }


}
