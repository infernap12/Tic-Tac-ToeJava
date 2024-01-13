package tictactoe;

import java.util.Random;

import static tictactoe.Game.current;

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
            }
        }
        return new int[0];
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
