package com.app.housie.commons;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class Utils {
    public static String getLineFromConsole(Scanner scanner) {
        return scanner.nextLine();
    }
}
