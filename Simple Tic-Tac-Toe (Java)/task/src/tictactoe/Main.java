package tictactoe;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        Scanner scanner = new Scanner("2 2\r\n2 2\r\ntwo two\r\n1 4\r\n1 1\r\n3 3\r\n2 1\r\n3 1\r\n2 3\r\n3 2"); //testing data
//        String input = scanner.next();
        char player;
        int[] coords;

        int[][] gridArray = new int[3][3];

        for (int j = 0, k = 0; j < 3; j++) { //fill grid array from input
            for (int i = 0; i < 3; i++) {
                gridArray[j][i] = input.charAt(k); // == (int)'_'?' ':input.charAt(k);
                k++;
            }
        }


        String grid = getGridString(gridArray);
        System.out.println(grid);
        System.out.println(getGameState(gridArray));
//        parse input
        coords = getCoords(scanner, gridArray);
        player = 'X';

        //update grid array
        gridArray[coords[0]][coords[1]] = player;
        grid = getGridString(gridArray);
        System.out.println(grid); //print updated grid

    }

    private static int[] getCoords(Scanner scanner, int[][] gridArray) {
        String input;
        while(true) {
            input = scanner.next() + scanner.next();

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
            if (gridArray[y][x] != (int) '_') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }return new int[]{y, x};
        }
    }

    private static String getGridString(int[][] gridArray) {
        char a = (char) gridArray[0][0];
        char b = (char) gridArray[0][1];
        char c = (char) gridArray[0][2];
        char d = (char) gridArray[1][0];
        char e = (char) gridArray[1][1];
        char f = (char) gridArray[1][2];
        char g = (char) gridArray[2][0];
        char h = (char) gridArray[2][1];
        char i = (char) gridArray[2][2];

        return """
                ---------
                | %c %c %c |
                | %c %c %c |
                | %c %c %c |
                ---------""".formatted(a,b,c,d,e,f,g,h,i);
    }

    private static String getGameState(int[][] gridArray) {
        int[][] lineArray = getLineArray(gridArray);
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
        for (int[] line : gridArray) {
            for (int cell : line) {
                if (cell == 88) {
                    xCount++;
                } else if (cell == 79) {
                    oCount++;
                }

            }

        }
        String verdict;

        if ((xTrip && oTrip) || Math.abs(xCount - oCount) > 1) {
            verdict = "Impossible";
        } else if (!xTrip && !oTrip && (xCount + oCount) == 9) {
            verdict = "Draw";
        } else if (!xTrip && !oTrip && (xCount + oCount) != 9) {
            verdict = "Game not finished";
        } else if (xTrip) {
            verdict = "X wins";
        } else if (oTrip) {
            verdict = "O wins";
        } else {
            verdict = "Undefined game state.";
        }

        return verdict;

    }

    private static int[][] getLineArray(int[][] gridArray) {
        int[][] lineArray = new int [8][3];
        System.arraycopy(gridArray, 0, lineArray, 0, 3); //horizontal lines
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                lineArray[j+3][i] = gridArray[i][j]; //vertical lines
            }
        }
        for (int i = 0; i < 3; i++) {
            lineArray[6][i] = gridArray[i][i]; //diagonal top left to bottom right
        }
        for (int i = 0; i < 3; i++) {
            lineArray[7][i] = gridArray[i][2 - i];//diagonal top right to bottom left
        }
        return lineArray;
    }
}
//not real proud of this, but I guess it's done