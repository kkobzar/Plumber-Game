package com.kyrylkobzar.consoleui;

import com.kyrylkobzar.core.Field;
import com.kyrylkobzar.core.GameState;

import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.kyrylkobzar.core.PipeState.*;

public class ConsoleUI {



    private Field field;

    public void run(){
        chooseLevel();
        while (!field.isSolved()){
            printField();
            readValue();
        }
        if (field.isSolved())
            field.setState(GameState.SOLVED);
    }

    private void printField(){
        for (int i = 0; i < field.getRowCount(); i++) {
            for (int j = 0; j < field.getColumnCount(); j++) {
                if (field.getTile(i,j).getHaveWater()){
                    switch (field.getTile(i,j).getState()){
                        case SIMPLE:
                            System.out.print("= ");
                            break;
                        case SIMPLE1:
                            System.out.print("║ ");
                            break;
                        case TWISTED:
                            System.out.print("╗ ");
                            break;
                        case TWISTED1:
                            System.out.print("╝ ");
                            break;
                        case TWISTED2:
                            System.out.print("╚ ");
                            break;
                        case TWISTED3:
                            System.out.print("╔ ");
                            break;
                        case THREEPLE:
                            System.out.print("╦ ");
                            break;
                        case THREEPLE1:
                            System.out.print("╣ ");
                            break;
                        case THREEPLE2:
                            System.out.print("╩ ");
                            break;
                        case THREEPLE3:
                            System.out.print("╠ ");
                            break;
                        case CROSED:
                            System.out.print("╬ ");
                            break;
                    }
                    continue;
                }
                switch (field.getTile(i,j).getState()){
                    case SIMPLE:
                        System.out.print("− ");
                        break;
                    case SIMPLE1:
                        System.out.print("┃ ");
                        break;
                    case TWISTED:
                        System.out.print("┓ ");
                        break;
                    case TWISTED1:
                        System.out.print("┛ ");
                        break;
                    case TWISTED2:
                        System.out.print("┗ ");
                        break;
                    case TWISTED3:
                        System.out.print("┏ ");
                        break;
                    case THREEPLE:
                        System.out.print("┯ ");
                        break;
                    case THREEPLE1:
                        System.out.print("┫ ");
                        break;
                    case THREEPLE2:
                        System.out.print("┷ ");
                        break;
                    case THREEPLE3:
                        System.out.print("┣ ");
                        break;
                    case CROSED:
                        System.out.print("┼ ");
                        break;
                }
            }
            System.out.println();
        }
    }
    private void chooseLevel(){
        String input;
        int level = 0;
        Scanner sc = new Scanner(System.in);
        while (level != 2 && level != 1){
            System.out.print("Choose level (1 or 2): ");
            input = sc.next();
            try{
                level = Integer.parseInt(input);
            }catch (NumberFormatException e){
                System.out.println("Please, enter valid input");
            }
        }
                /*try {
                    while (true) {
                        System.out.print("Choose level (1 or 2): ");
                        level = sc.nextInt();
                        if (level == 2 || level == 1)
                            break;
                        else {
                            System.out.println("Wrong value");
                        }
                    }
                }catch (Exception e){
                    System.out.println("Please, enter numbers");
                }*/
            switch (level){
                case 1: field = new Field("C:\\Users\\d33kei\\Documents\\java\\Plumber\\src\\com\\kyrylkobzar\\core\\Levels\\Level1.txt");
                    break;
                case 2: field = new Field("C:\\Users\\d33kei\\Documents\\java\\Plumber\\src\\com\\kyrylkobzar\\core\\Levels\\Level2.txt");
                    break;
            }
    }

    private void readValue(){
        Scanner sc = new Scanner(System.in);
        int row = 0;
        int column = 0;
        while (column < field.getRowCount() && row < field.getRowCount()){
            try {
                    while (row == 0 && column == 0) {
                        System.out.print("Enter row and column of pipe to rotate: ");
                        try {
                            row = sc.nextInt();
                        }catch (InputMismatchException e){}}
                        try {
                            column = sc.nextInt();
                        }catch (NumberFormatException e){}
                field.getTile(row-1, column-1).rotate();
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Some of the values are not valid, please try again");
            }
        }
    }
}
