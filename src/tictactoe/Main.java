package tictactoe;

import java.util.Queue;
import java.util.stream.IntStream;

public class Main {
    private static final Board gameBoard = new Board();
    static InputManager input = new InputManager(false);
    static GameState state = GameState.GNF;
    static char player = 'X';

    public static void main(String[] args) {
//        Scanner scanner = new Scanner("2 2\r\n2 2\r\ntwo two\r\n1 4\r\n1 1\r\n3 3\r\n2 1\r\n3 1\r\n2 3\r\n3 2"); //testing data
        Queue<Character> q = input.getQueue();
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                char ch = q.remove();
                gameBoard.boardArray[j][i] = ch;
                if (ch != ' ') {
                    player = player == 'X' ? 'O' : 'X';
                }
            }
        }
        gameBoard.print();
//        char player = 'X';
        int[] coords;


        // while (state.equals(GameState.GNF)) {
        coords = input.getValidMove(gameBoard); //get the next valid location to play a piece
        gameBoard.play(coords, player); //play the move into the Board
        gameBoard.print();//print updated board
        player = player == 'X' ? 'O' : 'X'; // toggle player to the other
        updateGameState();
        //  }
        System.out.println(state.msg); //victory line

    }

    private static void updateGameState() {
        int[][] lineArray = gameBoard.getLineArray();
        boolean xTrip = false, oTrip = false;
        int xCount = 0, oCount = 0;

        for (int[] line : lineArray) {
            int sum = IntStream.of(line).sum();
            xTrip |= (sum == 264); // 88 * 3 for 'X'
            oTrip |= (sum == 237); // 79 * 3 for 'O'
        }

        for (int[] line : gameBoard.boardArray) {
            for (int cell : line) {
                if (cell == 88) xCount++;
                else if (cell == 79) oCount++;
            }
        }

        int totalMoves = xCount + oCount;
        if ((xTrip && oTrip) || Math.abs(xCount - oCount) > 1) {
            state = GameState.IMPOSSIBLE;
        } else if (!xTrip && !oTrip && totalMoves == 9) {
            state = GameState.DRAW;
        } else if (!xTrip && !oTrip) {
            state = GameState.GNF;
        } else {
            state = xTrip ? GameState.X_WINS : GameState.O_WINS;
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