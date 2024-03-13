package tictactoe;


import java.util.Arrays;

public class Game {
    private final Player player1;
    private final Player player2;
    Player current; // the current player
    final Board gameBoard; // the board state
    GameState state; // current games tate

    public Game(String p1Type, String p2Type) {
        this.player1 = new Player('X', p1Type);
        this.player2 = new Player('O', p2Type);
        this.current = this.player1;
        this.state = GameState.GNS;
        gameBoard = new Board();
    }

    /*static void run(String p1Type, String p2Type) {
        player1 = new Player('X', p1Type);
        player2 = new Player('O', p2Type);
        Game.gameBoard.print();
        Game.current = player1;


        while (Game.state.equals(Game.GameState.GNF)) {
            Game.turn(player1, player2);
        }
        System.out.println(Game.state.msg + "\n"); //victory line
    }*/

    void turn(int[] coords) {
        if (current.isAi()) {
            coords = current.aiMove(gameBoard);
            System.out.printf("Making move level \"%s\"%n", current.level.toString().toLowerCase());
//                Thread.sleep(300);
        } else {
            System.out.print("Captured button coords are: " + Arrays.toString(coords) + " " + Board.toAlgebraic(coords[0], coords[1]));
        }
        gameBoard.play(coords, current.token); //play the move into the Board
        current = current.isPlayer1 ? player2 : player1; // toggle player to the other
        setGameState(getGameState(gameBoard));
    }

    private void setGameState(GameState gameState) {
        state = gameState;
    }

    static GameState getGameState(Board gameBoard) {
        Board.Cell[][] lineArray = gameBoard.getLineArray();
        boolean xTrip = false, oTrip = false;
        int xCount = 0, oCount = 0;

        for (Board.Cell[] line : lineArray) {
            int sum = 0;
            for (Board.Cell cell : line) {
                sum += cell.symbol;
                xTrip |= (sum == 264); // 88 * 3 for 'X'
                oTrip |= (sum == 237); // 79 * 3 for 'O'
            }
        }

        for (Board.Cell[] line : gameBoard.boardArray) {
            for (Board.Cell cell : line) {
                if (cell.symbol == 88) xCount++;
                else if (cell.symbol == 79) oCount++;
            }
        }

        int totalMoves = xCount + oCount;
        if ((xTrip && oTrip) || Math.abs(xCount - oCount) > 1) {
            return GameState.IMPOSSIBLE;
        } else if (!xTrip && !oTrip && totalMoves == 9) {
            return GameState.DRAW;
        } else if (!xTrip && !oTrip) {
            return GameState.GNF;
        } else {
            return xTrip ? GameState.X_WINS : GameState.O_WINS;
        }
    }

    public enum GameState {
        IMPOSSIBLE("Impossible"),
        GNF("Game in progress"),
        GNS("Game is not started"),
        DRAW("Draw"),
        X_WINS("X wins"),
        O_WINS("O wins");

        @Override
        public String toString() {
            return msg;
        }

        final String msg;

        GameState(String msg) {
            this.msg = msg;
        }
    }
}
