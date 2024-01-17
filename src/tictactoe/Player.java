package tictactoe;

import java.util.Random;

import static tictactoe.Game.current;
import static tictactoe.Game.getGameState;

public class Player {
    char token;
    PlayerType level;

    public Player(char token, PlayerType level) {
        this.token = token;
        this.level = level;
    }

    public Player(char token, String StringLevel) {
        PlayerType level = PlayerType.valueOf(StringLevel.toUpperCase());
        this.token = token;
        this.level = level;
    }

    public Player(char token) {
        this.token = token;
        this.level = PlayerType.USER;
    }

    public static int[] aiMove(PlayerType level, Board gameBoard) {
        switch (level) {
            case EASY -> {
                return getEasyMove(gameBoard);
            }
            case MEDIUM -> {
                return getMediumMove(gameBoard);
            }
            case HARD -> {
                return getHardMove(gameBoard);
            }
        }
        return new int[0];
    }

    private static int[] getHardMove(Board gameBoard) {
        int bestScore = Integer.MIN_VALUE;
        int[] move = new int[2];
        char symbol = current.token;
        char otherSymbol = symbol == 'X' ? 'O' : 'X';
        for (int j = 0; j < gameBoard.boardArray.length; j++) {
            for (int i = 0; i < gameBoard.boardArray[j].length; i++) {
                if (gameBoard.boardArray[j][i].symbol == ' ') {
                    gameBoard.boardArray[j][i].symbol = symbol;
                    int score = minimax(gameBoard, false);
                    gameBoard.boardArray[j][i].symbol = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        move = new int[]{j, i};
                    }
                }

            }

        }
        return move;
    }

    private static int minimax(Board board, boolean isMaximising) {
        Game.GameState result = getGameState(board);
        char symbol = isMaximising ? current.token : current.token == 'X' ? 'O' : 'X';
        int bestScore = isMaximising ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        switch (result) {
            case IMPOSSIBLE, GNF -> {
                for (int j = 0; j < board.boardArray.length; j++) {
                    for (int i = 0; i < board.boardArray[j].length; i++) {
                        if (board.boardArray[j][i].symbol == ' ') {
                            board.boardArray[j][i].symbol = symbol;
                            int score = minimax(board, !isMaximising);
                            board.boardArray[j][i].symbol = ' ';
                            bestScore = isMaximising ? Math.max(score, bestScore) : Math.min(score, bestScore);
                        }

                    }
                }
            }
            case DRAW -> {
                return 0;
            }
            case X_WINS -> {
                return current.token == 'X' ? 10 : -10;
            }
            case O_WINS -> {
                return current.token == 'O' ? 10 : -10;
            }
        }


        return bestScore;
    }

    private static int[] getMediumMove(Board gameBoard) {
        char symbol = current.token;
        char otherSymbol = symbol == 'X' ? 'O' : 'X';
        int[] blank = new int[2];
        Board.Cell[][] lineArray = gameBoard.getLineArray();
        boolean currentDub = false, otherDub = false;
        for (Board.Cell[] line : lineArray) {
            int sum = 0;
            for (Board.Cell cell : line) {
                if (cell.symbol == ' ') {
                    blank = cell.coords;
                }
                sum += cell.symbol;
            }
            if ((sum == ((int) symbol * 2) + (int) ' ')) {
                return blank;
            }

        }
        for (Board.Cell[] line : lineArray) {
            int sum = 0;
            for (Board.Cell cell : line) {
                if (cell.symbol == ' ') {
                    blank = cell.coords;
                }
                sum += cell.symbol;
            }
            if ((sum == ((int) otherSymbol * 2) + (int) ' ')) {
                return blank;
            }

        }
        return getEasyMove(gameBoard);
    }
    //currentDub |= (sum == ((int)symbol * 2) + (int)' '); // 88 * 3 for 'X'
    //                otherDub |= (sum == ((int)otherSymbol * 2) + (int)' '); // 79 * 3 for 'O'

    private static int[] getEasyMove(Board gameBoard) {
        Random r = new Random();
        int y;
        int x;
        do {
            y = r.nextInt(3);
            x = r.nextInt(3);
        } while (gameBoard.boardArray[y][x].symbol != ' ');

        return new int[]{y, x};
    }

    public boolean isAi() {
        return this.level != PlayerType.USER;
    }

    public enum PlayerType {
        EASY,
        MEDIUM,
        HARD,
        USER

    }
}
