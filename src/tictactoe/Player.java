package tictactoe;

import java.util.Random;

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
                Random r = new Random();
                int y;
                int x;
                do {
                    y = r.nextInt(3);
                    x = r.nextInt(3);
                } while (gameBoard.boardArray[y][x] != ' ');

                return new int[]{y, x};
            }
        }
        return new int[0];
    }

    public boolean isAi() {
        return this.level != PlayerType.USER;
    }

    enum PlayerType {
        EASY,
        MEDIUM,
        HARD,
        USER

    }
}
