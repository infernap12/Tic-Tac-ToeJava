package tictactoe;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    private static final int[][] boardArray = new int[3][3];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        Scanner scanner = new Scanner("2 2\r\n2 2\r\ntwo two\r\n1 4\r\n1 1\r\n3 3\r\n2 1\r\n3 1\r\n2 3\r\n3 2"); //testing data
        char player = 'X';
        int[] coords;

        for (int j = 0; j < 3; j++) { //initial fill of the board array with spaces
            Arrays.fill(boardArray[j],' ');//board array is just the slots for gameplay
        }
        System.out.println(getBoardString()); //draw the blank board


        while (getGameState().equals("Game not finished")) {
            coords = getValidMove(scanner); //        get the next valid location to play a piece
            boardArray[coords[0]][coords[1]] = player; //play the move into the boardArray
            System.out.println(getBoardString()); //print updated board
            player = player == 'X' ? 'O': 'X'; // toggle player to the other
        }
        System.out.println(getGameState()); //victory line

    }

    private static int[] getValidMove(Scanner scanner) {
        String input;
        while(true) {
            input = scanner.next() + scanner.next();
            System.out.println(input);

            if (!Character.isDigit(input.charAt(0)) || !Character.isDigit(input.charAt(1))) {
                System.out.println("You should enter numbers!");
                continue;
            }
            int x = Character.getNumericValue(input.charAt(1)) - 1;
            int y = Character.getNumericValue(input.charAt(0)) - 1;
            if (x > 2 || x < 0 || y > 2 || y < 0) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            if (boardArray[y][x] != (int) ' ') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }return new int[]{y, x};
        }
    }

    private static String getBoardString() {
        char a = (char) boardArray[0][0];
        char b = (char) boardArray[0][1];
        char c = (char) boardArray[0][2];
        char d = (char) boardArray[1][0];
        char e = (char) boardArray[1][1];
        char f = (char) boardArray[1][2];
        char g = (char) boardArray[2][0];
        char h = (char) boardArray[2][1];
        char i = (char) boardArray[2][2];

        return """
                ---------
                | %c %c %c |
                | %c %c %c |
                | %c %c %c |
                ---------""".formatted(a,b,c,d,e,f,g,h,i);
    }

    private static String getGameState() {
        int[][] lineArray = getLineArray();
        boolean xTrip = false;
        boolean oTrip = false;
        int xCount = 0;
        int oCount = 0;
        for (int[] line : lineArray) {
            if (IntStream.of(line).sum() == 264) {
                xTrip = true;
            }
            if (IntStream.of(line).sum() == 237) {
                oTrip = true;
            }
        }
        for (int[] line : boardArray) {
            for (int cell : line) {
                if (cell == 88) {
                    xCount++;
                } else if (cell == 79) {
                    oCount++;
                }

            }

        }
        String gameState;

        if ((xTrip && oTrip) || Math.abs(xCount - oCount) > 1) {
            gameState = "Impossible";
        } else if (!xTrip && !oTrip && (xCount + oCount) == 9) {
            gameState = "Draw";
        } else if (!xTrip && !oTrip && (xCount + oCount) != 9) {
            gameState = "Game not finished";
        } else if (xTrip) {
            gameState = "X wins";
        } else if (oTrip) {
            gameState = "O wins";
        } else {
            gameState = "Undefined game state.";
        }

        return gameState;

    }

    private static int[][] getLineArray() {
        int[][] lineArray = new int [8][3];
        System.arraycopy(boardArray, 0, lineArray, 0, 3); //horizontal lines
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                lineArray[j+3][i] = boardArray[i][j]; //vertical lines
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
}
//not real proud of this, but I guess it's done