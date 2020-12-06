package com.app.housie.commons;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.function.Consumer;

@Slf4j
public class Utils {

    public static String getLineFromConsole() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


}
