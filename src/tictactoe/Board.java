package tictactoe;

import java.util.Arrays;

public class Board {
    final Cell[][] boardArray;


    public Board() {
        this.boardArray = new Cell[3][3];
        for (int j = 0; j < boardArray.length; j++) {
            for (int i = 0; i < boardArray[j].length; i++) {
                boardArray[j][i] = new Cell(' ', new int[]{j, i});
            }



        }
//        for (Cell[] cells : boardArray) { //initial fill of the board array with spaces
//            Arrays.fill(cells, ' ');//board array is just the slots for gameplay
//        }
    }

    void print() {
        System.out.println("---------");
        for (Cell[] ints : boardArray) {// for each line
            System.out.print("| ");
            for (Cell cell : ints) {
                System.out.print(cell.symbol + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    Cell[][] getLineArray() {
        Cell[][] lineArray = new Cell[8][3];
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

    void play(int[] coords, char playerToken) {
        this.boardArray[coords[0]][coords[1]].symbol = playerToken;
    }

    class Cell {
        char symbol;
        int[] coords = new int[2];

        public Cell(char symbol, int[] coords) {
            this.symbol = symbol;
            this.coords = coords;
        }

        public int[] getCoords() {
            return coords;
        }
    }
}