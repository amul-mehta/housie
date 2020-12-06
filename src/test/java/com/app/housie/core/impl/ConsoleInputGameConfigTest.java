package com.app.housie.core.impl;

import com.app.housie.commons.Constants;
import com.app.housie.model.HousieParams;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class ConsoleInputGameConfigTest {

    @Test
    public void consoleInput_allValid(){
        String maxNum = "90";
        String numPlayers = "3";
        int rowCount = 3;
        int colCount = 5;
        String ticketSizeStr = rowCount + "X" + colCount;
        String maxNumPerRow = "5";
        InputStream inputStream = new ByteArrayInputStream((maxNum + "\n" + numPlayers + "\n" + ticketSizeStr + "\n" + maxNumPerRow).getBytes());
        Scanner testScanner = new Scanner(inputStream);
        ConsoleInputGameConfig consoleInputGameConfig = new ConsoleInputGameConfig();
        consoleInputGameConfig.init(testScanner);
        assertConfigNonInterrupted(maxNum, numPlayers, maxNumPerRow, rowCount, colCount, consoleInputGameConfig);
    }

    @Test
    public void consoleInput_interrupted1(){
        InputStream inputStream = new ByteArrayInputStream(Constants.OPTION_QUIT.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        ConsoleInputGameConfig consoleInputGameConfig = new ConsoleInputGameConfig();
        consoleInputGameConfig.init(testScanner);
        Assert.assertTrue(consoleInputGameConfig.isInterrupted());
    }

    @Test
    public void consoleInput_interrupted2(){
        String maxNum = "90";
        InputStream inputStream = new ByteArrayInputStream((maxNum + "\n" + Constants.OPTION_QUIT).getBytes());
        Scanner testScanner = new Scanner(inputStream);
        ConsoleInputGameConfig consoleInputGameConfig = new ConsoleInputGameConfig();
        consoleInputGameConfig.init(testScanner);
        Assert.assertTrue(consoleInputGameConfig.isInterrupted());
    }

    @Test
    public void consoleInput_interrupted3(){
        String maxNum = "90";
        String numPlayers = "3";
        InputStream inputStream = new ByteArrayInputStream((maxNum + "\n" + numPlayers + "\n" + Constants.OPTION_QUIT).getBytes());
        Scanner testScanner = new Scanner(inputStream);
        ConsoleInputGameConfig consoleInputGameConfig = new ConsoleInputGameConfig();
        consoleInputGameConfig.init(testScanner);
        Assert.assertTrue(consoleInputGameConfig.isInterrupted());
    }

    @Test
    public void consoleInput_interrupted4(){
        String maxNum = "90";
        String numPlayers = "3";
        int rowCount = 3;
        int colCount = 5;
        String ticketSizeStr = rowCount + "X" + colCount;
        InputStream inputStream = new ByteArrayInputStream((maxNum + "\n" + numPlayers + "\n" + ticketSizeStr + "\n" + Constants.OPTION_QUIT).getBytes());
        Scanner testScanner = new Scanner(inputStream);
        ConsoleInputGameConfig consoleInputGameConfig = new ConsoleInputGameConfig();
        consoleInputGameConfig.init(testScanner);
        Assert.assertTrue(consoleInputGameConfig.isInterrupted());
    }

    @Test
    public void consoleInput_wrongFormattedTicketSize1(){
        String maxNum = "90";
        String numPlayers = "3";
        String ticketSizeStrInCorrect = "ABC";
        int rowCount = 3;
        int colCount = 5;
        String ticketSizeStr = rowCount + "X" + colCount;
        String maxNumPerRow = "5";
        InputStream inputStream = new ByteArrayInputStream((maxNum + "\n" + numPlayers +"\n" + ticketSizeStrInCorrect + "\n" + ticketSizeStr + "\n" + maxNumPerRow).getBytes());
        Scanner testScanner = new Scanner(inputStream);
        ConsoleInputGameConfig consoleInputGameConfig = new ConsoleInputGameConfig();
        consoleInputGameConfig.init(testScanner);
        assertConfigNonInterrupted(maxNum, numPlayers, maxNumPerRow, rowCount, colCount, consoleInputGameConfig);
    }

    @Test
    public void consoleInput_wrongFormattedTicketSize2(){
        String maxNum = "90";
        String numPlayers = "3";
        String ticketSizeStrInCorrect = "2XA";
        int rowCount = 3;
        int colCount = 5;
        String ticketSizeStr = rowCount + "X" + colCount;
        String maxNumPerRow = "5";
        InputStream inputStream = new ByteArrayInputStream((maxNum + "\n" + numPlayers +"\n" + ticketSizeStrInCorrect + "\n" + ticketSizeStr + "\n" + maxNumPerRow).getBytes());
        Scanner testScanner = new Scanner(inputStream);
        ConsoleInputGameConfig consoleInputGameConfig = new ConsoleInputGameConfig();
        consoleInputGameConfig.init(testScanner);
        assertConfigNonInterrupted(maxNum, numPlayers, maxNumPerRow, rowCount, colCount, consoleInputGameConfig);
    }

    @Test
    public void consoleInput_wrongFormattedNumPlayers(){
        String maxNum = "90";
        String numPlayersIncorrect = "ABC";
        String numPlayers = "3";
        int rowCount = 3;
        int colCount = 5;
        String ticketSizeStr = rowCount + "X" + colCount;
        String maxNumPerRow = "5";
        InputStream inputStream = new ByteArrayInputStream((maxNum + "\n" + numPlayersIncorrect +"\n" + numPlayers + "\n" + ticketSizeStr + "\n" + maxNumPerRow).getBytes());
        Scanner testScanner = new Scanner(inputStream);
        ConsoleInputGameConfig consoleInputGameConfig = new ConsoleInputGameConfig();
        consoleInputGameConfig.init(testScanner);
        assertConfigNonInterrupted(maxNum, numPlayers, maxNumPerRow, rowCount, colCount, consoleInputGameConfig);
    }

    @Test
    public void consoleInput_defaultTicketSize(){
        String maxNum = "90";
        String numPlayers = "3";
        String ticketSizeStr = "\n";
        String maxNumPerRow = "5";
        InputStream inputStream = new ByteArrayInputStream((maxNum + "\n" + numPlayers +"\n" + ticketSizeStr + "\n" + maxNumPerRow).getBytes());
        Scanner testScanner = new Scanner(inputStream);
        ConsoleInputGameConfig consoleInputGameConfig = new ConsoleInputGameConfig();
        consoleInputGameConfig.init(testScanner);
        assertConfigNonInterrupted(maxNum, numPlayers, maxNumPerRow, Constants.DEFAULT_TICKET_ROW_SIZE, Constants.DEFAULT_TICKET_COLUMN_SIZE, consoleInputGameConfig);
    }

    private void assertConfigNonInterrupted(String maxNum,
                                            String numPlayers,
                                            String maxNumPerRow,
                                            int ticketRow,
                                            int ticketColumn,
                                            ConsoleInputGameConfig consoleInputGameConfig) {
        HousieParams housieParams = consoleInputGameConfig.getParams();
        Assert.assertEquals(housieParams.getMaxNumRange(), Integer.parseInt(maxNum));
        Assert.assertEquals(housieParams.getNumPerRow(), Integer.parseInt(maxNumPerRow));
        Assert.assertEquals(housieParams.getNumOfPlayers(), Integer.parseInt(numPlayers));
        Assert.assertEquals(housieParams.getTicketSize().length, 2);
        Assert.assertEquals(housieParams.getTicketSize()[0], ticketRow);
        Assert.assertEquals(housieParams.getTicketSize()[1], ticketColumn);
        Assert.assertFalse(consoleInputGameConfig.isInterrupted());
    }
}
