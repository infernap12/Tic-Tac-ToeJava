package tictactoe;

import java.util.stream.IntStream;

public class Game {
    static final Board gameBoard = new Board();
    static GameState state = GameState.GNF;
    static Player current;

    static InputManager input = Main.input;

    static void run(String p1Type, String p2Type) {
        Player player1 = new Player('X',p1Type);
        Player player2 = new Player('O', p2Type);
        Game.gameBoard.print();
        Game.current = player1;
        int[] coords;


        while (Game.state.equals(Game.GameState.GNF)) {
            Game.turn(player1, player2);
        }
        System.out.println(Game.state.msg); //victory line
    }

    static void turn(Player player1, Player player2) {
        int[] coords;
        if (current.isAi()) {
            coords = Player.aiMove(current.level, gameBoard);
            System.out.printf("Making move level \"%s\"%n", current.level.toString().toLowerCase());
//                Thread.sleep(300);
        } else {
            System.out.print("Enter the coordinates: ");
            coords = input.getValidMove(gameBoard); //get the next valid location to play a piece
        }
        gameBoard.play(coords, current.token); //play the move into the Board
        gameBoard.print();//print updated board
        current = current == player1 ? player2 : player1; // toggle player to the other
        updateGameState();
    }

    static void updateGameState() {
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
