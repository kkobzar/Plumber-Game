package com.kyrylkobzar.consoleui;

import com.kyrylkobzar.core.Field;
import com.kyrylkobzar.core.GameState;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {


    private Field field;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public void run() {
        chooseLevel();
        while (!field.isSolved() && field.MovesLeft()) {
            printField();
            readValue();
        }
        printField();
        if (field.isSolved())
            field.setState(GameState.SOLVED);
        finalMessage();

    }

    private void printField() {
        System.out.print("  ");
        for (int i = 0; i < field.getColumnCount(); i++){
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for (int i = 0; i < field.getRowCount(); i++) {
            System.out.print((char)(i + 65) + " ");
            for (int j = 0; j < field.getColumnCount(); j++) {
                if (field.getTile(i, j).getHaveWater()){
                    switch (field.getTile(i,j).getState()){
                        case SIMPLE:
                            System.out.print(ANSI_BLUE + "= " + ANSI_RESET);
                            break;
                        case SIMPLE1:
                            System.out.print(ANSI_BLUE + "║ " + ANSI_RESET);
                            break;
                        case TWISTED:
                            System.out.print(ANSI_BLUE + "╗ " + ANSI_RESET);
                            break;
                        case TWISTED1:
                            System.out.print(ANSI_BLUE + "╝ " + ANSI_RESET);
                            break;
                        case TWISTED2:
                            System.out.print(ANSI_BLUE + "╚ " + ANSI_RESET);
                            break;
                        case TWISTED3:
                            System.out.print(ANSI_BLUE + "╔ " + ANSI_RESET);
                            break;
                        case THREEPLE:
                            System.out.print(ANSI_BLUE + "╦ " + ANSI_RESET);
                            break;
                        case THREEPLE1:
                            System.out.print(ANSI_BLUE + "╣ " + ANSI_RESET);
                            break;
                        case THREEPLE2:
                            System.out.print(ANSI_BLUE + "╩ " + ANSI_RESET);
                            break;
                        case THREEPLE3:
                            System.out.print(ANSI_BLUE + "╠ " + ANSI_RESET);
                            break;
                        case CROSED:
                            System.out.print(ANSI_BLUE + "╬ " + ANSI_RESET);
                            break;
                    }
                    continue;
                }
                    switch (field.getTile(i,j).getState()){
                        case SIMPLE:
                            System.out.print(ANSI_RED + "= " + ANSI_RESET);
                            break;
                        case SIMPLE1:
                            System.out.print(ANSI_RED + "║ " + ANSI_RESET);
                            break;
                        case TWISTED:
                            System.out.print(ANSI_RED + "╗ " + ANSI_RESET);
                            break;
                        case TWISTED1:
                            System.out.print(ANSI_RED + "╝ " + ANSI_RESET);
                            break;
                        case TWISTED2:
                            System.out.print(ANSI_RED + "╚ " + ANSI_RESET);
                            break;
                        case TWISTED3:
                            System.out.print(ANSI_RED + "╔ " + ANSI_RESET);
                            break;
                        case THREEPLE:
                            System.out.print(ANSI_RED + "╦ " + ANSI_RESET);
                            break;
                        case THREEPLE1:
                            System.out.print(ANSI_RED + "╣ " + ANSI_RESET);
                            break;
                        case THREEPLE2:
                            System.out.print(ANSI_RED + "╩ " + ANSI_RESET);
                            break;
                        case THREEPLE3:
                            System.out.print(ANSI_RED + "╠ " + ANSI_RESET);
                            break;
                        case CROSED:
                            System.out.print(ANSI_RED + "╬ " + ANSI_RESET);
                            break;
                    }
                }
            System.out.println();
            }
            System.out.println("You have " + field.getMovesLeft() + " moves left");
    }

    private void chooseLevel() {
        String input;
        int level = 0;
        Scanner sc = new Scanner(System.in);
        while (level != 2 && level != 1) {
            System.out.print("Choose level (1 or 2): ");
            input = sc.next();
            try {
                level = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please, enter valid input");
            }
        }
        switch (level) {
            case 1:
                field = new Field("C:\\Users\\d33kei\\Documents\\java\\Plumber\\src\\com\\kyrylkobzar\\core\\Levels\\Level1.txt");
                break;
            case 2:
                field = new Field("C:\\Users\\d33kei\\Documents\\java\\Plumber\\src\\com\\kyrylkobzar\\core\\Levels\\Level2.txt");
                break;
        }
    }

    private void readValue() {
        Scanner sc = new Scanner(System.in);
        int row = 0;
        int column = 0;
        String input;
        Pattern pattern = Pattern.compile("[A-Z] *[0-9]{1,2}");
        Pattern removePatternt = Pattern.compile("([A-Z]| )*");
        Matcher mtch;
        Matcher rmvmtch;
        while (true){
            System.out.print("Enter row and column of pipe to rotate: ");
            input = sc.nextLine();
            input = input.toUpperCase();
            mtch = pattern.matcher(input);
            if (mtch.matches()){
                row = (int) input.charAt(0) - 'A';
                rmvmtch = removePatternt.matcher(input);
                input = rmvmtch.replaceAll("");
                column = Integer.parseInt(input) - 1;
                if (row >= 0 && row < field.getRowCount() && column >= 0 && column < field.getColumnCount()) {
                    field.rotatePipe(row, column);
                    break;
                }
                else
                    System.out.println("This values are not valid!");
            }else {
                System.out.println("Wrong input!");
            }
        }
    }

    private void finalMessage(){
        switch (field.getState()){
            case FAILED:
                System.out.println("Oh no, you lost... Try again!");break;
            case SOLVED:
                System.out.println("Congratulations! You won!");
                default:break;
        }
    }
}
