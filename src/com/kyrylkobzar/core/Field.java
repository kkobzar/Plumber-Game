package com.kyrylkobzar.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Field {
    private final int rowCount;
    private final int columnCount;

    private final Tile[][] field;

    private GameState state = GameState.PLAYING;

    public Field(int xCount, int yCount){
        this.columnCount = yCount;
        this.rowCount = xCount;

        this.field = new Tile[xCount][yCount];
    }

    public Field(String path){
        File level = new File(path);
        Scanner sc = null;
        try {
            sc = new Scanner(level);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
            rowCount = sc.nextInt();
            columnCount = sc.nextInt();
            this.field = new Tile[rowCount][columnCount];
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    field[i][j] = new Tile();
                    field[i][j].setState(getPipe(sc.nextInt()));
                }
            }

    }

    public void randomField(){
        int randomType;
        int x = 0;
        int y = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                field[i][j] = new Tile();
            }
        }
        do {
            randomType = (int )(Math. random() * 11 + 1);
            field[0][0].setState(getPipe(randomType));
        }while (randomType == 4);
        while (true){
            int dr = (int) (Math.random()*2 + 1);
            int rn = (int)(Math. random() * 11 + 1);
            if (rn == 4)
                continue;
            switch (dr){
                case 1:
                    if (!onRightBorder(x,y) && isConnecting(field[x][y].getState(), getPipe(rn), Direction.RIGHT) && canGoDown(getPipe(rn)) && canGoRight(getPipe(rn))){
                        y++;
                        field[x][y].setState(getPipe(rn));
                    }else {
                        continue;
                    }
                    break;
                case 2:
                    if (!onBottomBorder(x,y) && isConnecting(field[x][y].getState(), getPipe(rn), Direction.DOWN) && canGoDown(getPipe(rn)) && canGoRight(getPipe(rn))){
                        x++;
                        field[x][y].setState(getPipe(rn));
                    }else
                        continue;
                    break;
                default:
                        continue;
            }
            if (x == rowCount-1 && y == columnCount-1)
                break;
        }
        for (int i = 0; i < rowCount; i++){
            for (int j = 0; j < columnCount; j++){
                int rn = (int)(Math. random() * 11 + 1);
                if (field[i][j] == null)
                    field[i][j].setState(getPipe(rn));
            }
        }
    }

    public boolean isSolved(){
        resetWater();
        if (field[0][0].getState() != PipeState.SIMPLE && field[0][0].getState() != PipeState.TWISTED && field[0][0].getState() != PipeState.TWISTED3 && field[0][0].getState() != PipeState.THREEPLE){
            field[0][0].setHaveWater(true);
        }else {
            return false;
        }
        for (int i = 0; i < rowCount; i++){
            for (int j = 0; j < columnCount; j++){
                if (field[i][j].getHaveWater())
                    addWater(i,j);
            }
        }
        if (field[rowCount-1][columnCount-1].getState() !=  PipeState.SIMPLE && field[rowCount-1][columnCount-1].getState() !=  PipeState.TWISTED1 && field[rowCount-1][columnCount-1].getState() !=  PipeState.THREEPLE2)
            return field[rowCount-1][columnCount-1].getHaveWater();
        else
            return false;
    }
    public void rotatePipe(int x, int y){
        field[y-1][x-1].rotate();
    }

    private boolean onRightBorder(int x, int y){
        return y == columnCount-1;
    }

    private boolean onLeftBorder(int x, int y){
        return  y == 0;
    }

    private boolean onTopBorder(int x, int y){
        return x == 0;
    }

    private boolean onBottomBorder(int x, int y){
        return x == rowCount-1;
    }

    private boolean canGoLeft(PipeState t){
        return t != PipeState.SIMPLE1 && t != PipeState.TWISTED2 && t != PipeState.TWISTED3 && t != PipeState.THREEPLE3;
    }

    private boolean canGoRight(PipeState t){
        return t != PipeState.SIMPLE1 && t != PipeState.TWISTED && t != PipeState.TWISTED1 && t != PipeState.THREEPLE1;
    }

    private boolean canGoUp(PipeState t){
        return t != PipeState.SIMPLE && t != PipeState.TWISTED && t != PipeState.TWISTED3 && t != PipeState.THREEPLE;
    }

    private boolean canGoDown(PipeState t){
        return t != PipeState.SIMPLE && t != PipeState.TWISTED1 && t != PipeState.TWISTED2 && t != PipeState.THREEPLE2;
    }

    private  boolean isConnecting(PipeState k, PipeState m, Direction direction){
        switch (direction){
            case RIGHT:
                if (k == PipeState.SIMPLE || k == PipeState.CROSED || k == PipeState.TWISTED2 || k == PipeState.TWISTED3 || k == PipeState.THREEPLE || k == PipeState.THREEPLE2 || k == PipeState.THREEPLE3){
                    return m != PipeState.SIMPLE1 && m != PipeState.TWISTED2 && m != PipeState.TWISTED3 && m != PipeState.THREEPLE3;
                }else {
                    return false;
                }
            case DOWN:
                if (k == PipeState.SIMPLE1 || k == PipeState.CROSED || k == PipeState.TWISTED || k == PipeState.TWISTED3 || k == PipeState.THREEPLE || k == PipeState.THREEPLE1 || k == PipeState.THREEPLE3){
                    return m != PipeState.SIMPLE && m != PipeState.TWISTED && m != PipeState.TWISTED3 && m != PipeState.THREEPLE;
                }else {
                    return false;
                }
            case UP:
                if (k != PipeState.SIMPLE && k != PipeState.TWISTED && k != PipeState.TWISTED3 && k != PipeState.THREEPLE){
                    return m != PipeState.SIMPLE && m != PipeState.TWISTED && m != PipeState.TWISTED3 && m != PipeState.THREEPLE;
                }else {
                    return false;
                }
            case LEFT:
                if (k != PipeState.SIMPLE1 && k != PipeState.TWISTED2 && k != PipeState.TWISTED3 && k != PipeState.THREEPLE3){
                    return m != PipeState.SIMPLE1 && m != PipeState.TWISTED && m != PipeState.TWISTED1 && m != PipeState.THREEPLE1;
                }else {
                    return false;
                }
            default:
                System.out.println("Error connecting"); return false;

        }
    }

    private  Tile pipeOnRight(int x, int y){
        return field[x][y+1];
    }

    private  Tile pipeOnLeft(int x, int y){
        return field[x][y-1];
    }

    private  Tile pipeOnTop(int x, int y){
        return field[x-1][y];
    }

    private  Tile pipeOnBottom(int x, int y){
        return field[x+1][y];
    }

    private PipeState getPipe(int i){
        switch (i){
            case 1:return PipeState.SIMPLE;
            case 2:return PipeState.SIMPLE1;
            case 3:return PipeState.TWISTED;
            case 4:return PipeState.TWISTED1;
            case 5:return PipeState.TWISTED2;
            case 6:return PipeState.TWISTED3;
            case 7:return PipeState.THREEPLE;
            case 8:return PipeState.THREEPLE1;
            case 9:return PipeState.THREEPLE2;
            case 10:return PipeState.THREEPLE3;
            case 11:return PipeState.CROSED;
            default:
                System.out.println("Wrong input");
                return PipeState.SIMPLE;
        }
    }

/*    private Direction getDirections(int i){
        switch (i){
            case 1: return Direction.RIGHT;
            case 2: return Direction.DOWN;
            default:
                System.out.println("Error");
                return Direction.DOWN;
        }
    }*/

    private void addWater(int x, int y){
        if (canGoRight(field[x][y].getState()) && !onRightBorder(x, y) && isConnecting(field[x][y].getState(), pipeOnRight(x,y).getState(), Direction.RIGHT) && !pipeOnRight(x,y).getHaveWater()){
            pipeOnRight(x,y).setHaveWater(true);
        }
        if (canGoUp(field[x][y].getState()) && !onTopBorder(x, y) && isConnecting(field[x][y].getState(), pipeOnTop(x,y).getState(), Direction.UP) && !pipeOnTop(x,y).getHaveWater()){
            pipeOnTop(x,y).setHaveWater(true);
        }
        if (canGoDown(field[x][y].getState()) && !onBottomBorder(x, y) && isConnecting(field[x][y].getState(), pipeOnBottom(x,y).getState(), Direction.DOWN) && !pipeOnBottom(x,y).getHaveWater()) {
            pipeOnBottom(x,y).setHaveWater(true);
        }
        if (canGoLeft(field[x][y].getState()) && !onLeftBorder(x, y) && isConnecting(field[x][y].getState(), pipeOnLeft(x,y).getState(), Direction.LEFT) && !pipeOnLeft(x,y).getHaveWater()){
            pipeOnLeft(x,y).setHaveWater(true);
        }
    }

    private void resetWater(){
        for (int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++){
                field[i][j].setHaveWater(false);
            }
        }
    }

    public int getRowCount() { return rowCount; }

    public int getColumnCount() { return columnCount; }

    public void printField(){
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (field[i][j].getHaveWater()){
                    switch (field[i][j].getState()){
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
                            System.out.println("╬ ");
                            break;
                    }
                    continue;
                }
                switch (field[i][j].getState()){
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
                        System.out.println("┼ ");
                        break;
                }
            }
            System.out.println();
        }
    }
    public Tile getTile(int x, int y){
        return field[x][y];
    }
    public GameState getState(){return state;}
    public void setState(GameState s){this.state = s;}
}
