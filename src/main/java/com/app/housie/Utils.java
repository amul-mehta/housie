package com.app.housie;

import java.util.Scanner;

public class Utils {
    public static String getLineFromConsole(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    public static int getIntFromConsole(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
