package tictactoe;

import java.util.Scanner;
import java.util.stream.IntStream;
import tictactoe.Board.*;

public class Main {
    private static final Board gameBoard = new Board();
    static InputManager input = new InputManager(true);
    static GameState state = GameState.GNF;
    public static void main(String[] args) {
//        Scanner scanner = new Scanner("2 2\r\n2 2\r\ntwo two\r\n1 4\r\n1 1\r\n3 3\r\n2 1\r\n3 1\r\n2 3\r\n3 2"); //testing data

        boardChar player = boardChar.TOKEN1;
        int[] coords;


        while (state.equals(GameState.GNF)) {
            coords = getValidMove(); //        get the next valid location to play a piece
            gameBoard.play(coords,player); //play the move into the Board
            gameBoard.print();//print updated board
            player = player == boardChar.TOKEN1 ? boardChar.TOKEN2: boardChar.TOKEN1; // toggle player to the other
            updateGameState();
        }
        System.out.println(state.msg); //victory line

    }

    private static int[] getValidMove() {
        int[] coords;

        while(true) {
            coords = input.askCoords();

//            if (!Character.isDigit(input.charAt(0)) || !Character.isDigit(input.charAt(1))) {
//                System.out.println("You should enter numbers!");
//                continue;
//            }

//            int x = Character.getNumericValue(input.charAt(1)) - 1;
//            int y = Character.getNumericValue(input.charAt(0)) - 1;
//            if (x > 2 || x < 0 || y > 2 || y < 0) {
//                System.out.println("Coordinates should be from 1 to 3!");
//                continue;
//            }
            int y = coords[0] - 1;
            int x = coords[1] - 1;
            if (gameBoard.boardArray[y][x] != boardChar.SPACE.ch) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }return new int[]{y, x};
        }
    }

    private static void updateGameState() {
        int[][] lineArray = gameBoard.getLineArray();
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
        for (int[] line : gameBoard.boardArray) {
            for (int cell : line) {
                if (cell == 88) {
                    xCount++;
                } else if (cell == 79) {
                    oCount++;
                }

            }

        }

        if ((xTrip && oTrip) || Math.abs(xCount - oCount) > 1) {
            state = GameState.IMPOSSIBLE;
        } else if (!xTrip && !oTrip && (xCount + oCount) == 9) {
            state = GameState.DRAW;
        } else if (!xTrip && !oTrip && (xCount + oCount) != 9) {
            state = GameState.GNF;
        } else if (xTrip) {
            state = GameState.X_WINS;
        } else if (oTrip) {
            state = GameState.O_WINS;
        } else {
            state = null;
        }
    }

    enum GameState {
        IMPOSSIBLE("Impossible"),
        GNF("Game not finished"),
        DRAW("Draw"),
        X_WINS("X wins"),
        O_WINS("O wins");

        final String msg;
        GameState(String msg) {
            this.msg = msg;
        }
    }

}
//not real proud of this, but I guess it's done