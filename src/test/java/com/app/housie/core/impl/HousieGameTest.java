package com.app.housie.core.impl;

import com.app.housie.core.generator.impl.NumberGenerator;
import com.app.housie.core.generator.impl.TicketGenerator;
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
        NumberGenerator numberGenerator = Mockito.mock(NumberGenerator.class);
        HousieGameState housieGameState = Mockito.mock(HousieGameState.class);

        HousieGame housieGame = Mockito.spy(new HousieGame());
        when(housieGame.getGameConfig()).thenReturn(consoleInputGameConfig);
        when(housieGame.getTicketGenerator()).thenReturn(ticketGenerator);
        when(housieGame.getGameState()).thenReturn(housieGameState);
        doReturn(numberGenerator).when(housieGame).getRandomNumberGeneratorInstance();

        housieGame.init();
        Assert.assertFalse(housieGame.isToQuit());

        InputStream inputStream = new ByteArrayInputStream("Q".getBytes());
        try (MockedStatic<HousieGame> dummy = Mockito.mockStatic(HousieGame.class)) {
            dummy.when(HousieGame::getConsoleInputScanner).thenReturn(new Scanner(inputStream));
            housieGame.play();
            Assert.assertTrue(housieGame.isToQuit());
            Mockito.verify(housieGameState, times(0)).printSummary();
            Mockito.verify(housieGameState, times(0)).updateState(any());
            Mockito.verify(numberGenerator, times(0)).generate();
        }
    }

    @Test
    public void testHousieGame_successInitializationWinningGame() {
        HousieParams housieParams = HousieParams.builder().numPerRow(3).ticketSize(new int[]{3, 5}).numOfPlayers(2).maxNumRange(100).build();

        ConsoleInputGameConfig consoleInputGameConfig = Mockito.mock(ConsoleInputGameConfig.class);
        when(consoleInputGameConfig.getParams()).thenReturn(housieParams);
        when(consoleInputGameConfig.isInterrupted()).thenReturn(false);

        TicketGenerator ticketGenerator = Mockito.mock(TicketGenerator.class);
        NumberGenerator numberGenerator = Mockito.mock(NumberGenerator.class);
        HousieGameState housieGameState = Mockito.mock(HousieGameState.class);

        HousieGame housieGame = Mockito.spy(new HousieGame());
        when(housieGame.getGameConfig()).thenReturn(consoleInputGameConfig);
        when(housieGame.getTicketGenerator()).thenReturn(ticketGenerator);
        when(housieGame.getGameState()).thenReturn(housieGameState);
        doReturn(numberGenerator).when(housieGame).getRandomNumberGeneratorInstance();

        housieGame.init();
        Assert.assertFalse(housieGame.isToQuit());

        when(numberGenerator.generate()).thenReturn(10).thenReturn(11).thenReturn(12);
        when(housieGameState.isCompleted()).thenReturn(false).thenReturn(false).thenReturn(true);
        InputStream inputStream = new ByteArrayInputStream("A\nN\nN\nN\n".getBytes());
        try (MockedStatic<HousieGame> dummy = Mockito.mockStatic(HousieGame.class)) {
            dummy.when(HousieGame::getConsoleInputScanner).thenReturn(new Scanner(inputStream));
            housieGame.play();
            Assert.assertTrue(housieGame.isToQuit());
            Mockito.verify(housieGameState, times(1)).printSummary();
            Mockito.verify(housieGameState, times(3)).updateState(any());
            Mockito.verify(numberGenerator, times(3)).generate();
        }
    }

    @Test
    public void testHousieGame_successInitializationQuitMidGame() {
        HousieParams housieParams = HousieParams.builder().numPerRow(3).ticketSize(new int[]{3, 5}).numOfPlayers(2).maxNumRange(100).build();

        ConsoleInputGameConfig consoleInputGameConfig = Mockito.mock(ConsoleInputGameConfig.class);
        when(consoleInputGameConfig.getParams()).thenReturn(housieParams);
        when(consoleInputGameConfig.isInterrupted()).thenReturn(false);

        TicketGenerator ticketGenerator = Mockito.mock(TicketGenerator.class);
        NumberGenerator numberGenerator = Mockito.mock(NumberGenerator.class);
        HousieGameState housieGameState = Mockito.mock(HousieGameState.class);

        HousieGame housieGame = Mockito.spy(new HousieGame());
        when(housieGame.getGameConfig()).thenReturn(consoleInputGameConfig);
        when(housieGame.getTicketGenerator()).thenReturn(ticketGenerator);
        when(housieGame.getGameState()).thenReturn(housieGameState);
        doReturn(numberGenerator).when(housieGame).getRandomNumberGeneratorInstance();

        housieGame.init();
        Assert.assertFalse(housieGame.isToQuit());

        when(numberGenerator.generate()).thenReturn(10).thenReturn(11).thenReturn(12);
        when(housieGameState.isCompleted()).thenReturn(false).thenReturn(false).thenReturn(false);
        InputStream inputStream = new ByteArrayInputStream("N\nN\nN\nQ".getBytes());
        try (MockedStatic<HousieGame> dummy = Mockito.mockStatic(HousieGame.class)) {
            dummy.when(HousieGame::getConsoleInputScanner).thenReturn(new Scanner(inputStream));
            housieGame.play();
            Assert.assertTrue(housieGame.isToQuit());
            Mockito.verify(housieGameState, times(0)).printSummary();
            Mockito.verify(housieGameState, times(3)).updateState(any());
            Mockito.verify(numberGenerator, times(3)).generate();
        }
    }


}
