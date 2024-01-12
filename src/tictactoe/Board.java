package tictactoe;

import java.util.Arrays;

public class Board {
    final int[][] boardArray;


    public Board() {
        this.boardArray = new int[3][3];
        for (int[] ints : boardArray) { //initial fill of the board array with spaces
            Arrays.fill(ints, ' ');//board array is just the slots for gameplay
        }
    }

    void print() {
        System.out.println("---------");
        for (int[] ints : boardArray) {// for each line
            System.out.print("| ");
            for (int anInt : ints) {
                System.out.print((char) anInt + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    int[][] getLineArray() {
        int[][] lineArray = new int[8][3];
        System.arraycopy(boardArray, 0, lineArray, 0, 3); //horizontal lines
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                lineArray[j + 3][i] = boardArray[i][j]; //vertical lines
            }
        }
        for (int i = 0; i < 3; i++) {
            lineArray[6][i] = boardArray[i][i]; //diagonal top left to bottom right
        }
        for (int i = 0; i < 3; i++) {
            lineArray[7][i] = boardArray[i][2 - i];//diagonal top right to bottom left
        }
        return lineArray;
    }

    void play(int[] coords, char player) {
        this.boardArray[coords[0]][coords[1]] = player;
    }

}