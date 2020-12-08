package com.app.housie.commons;

import com.app.housie.core.combination.WinningCombination;
import com.app.housie.core.combination.impl.EarlyFive;
import com.app.housie.core.combination.impl.FullHouse;
import com.app.housie.core.combination.impl.TopLine;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Constants {
    public static final List<WinningCombination> WINNING_COMBINATIONS =
            Arrays.asList(new TopLine(), new EarlyFive(), new FullHouse());
    public static final Scanner CONSOLE_INPUT_SCANNER = new Scanner(System.in);
    public static final String CALLER = "Caller";
    public static final String OPTION_QUIT = "Q";
    public static final String OPTION_NEW_NUMBER = "N";
    public static final String TICKET_SIZE_SPLIT_STR = "X";
    public static final String TOP_LINE_NAME = "Top Line";
    public static final String EARLY_FIVE_NAME = "Early Five";
    public static final String FULL_HOUSE_NAME = "Full House";
    public static final String NOTHING_WON = "Nothing";
    public static final String COMBINATION_WON_DELIMITER = " and ";
    public static final String PLAYER_NAME_PREFIX = "Player#";
    public static final int TICKET_DIMENSION_SIZE = 2;
    public static final int DEFAULT_TICKET_ROW_SIZE = 3;
    public static final int DEFAULT_TICKET_COLUMN_SIZE = 10;
    public static final int EARLY_FIVE_THRESHOLD = 5;
    public static final int DEFAULT_MIN_NUM_RANGE = 1;

}
