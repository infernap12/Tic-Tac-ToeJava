package tictactoe;

import java.util.Random;

public class Player {
    private final boolean ai;
    char token;
    DifficultyLevel level;

    public Player(char token, DifficultyLevel level) {
        this.token = token;
        this.level = level;
        this.ai = true;
    }

    public Player(char token) {
        this.token = token;
        this.ai = false;
    }

    public static int[] aiMove(DifficultyLevel level, Board gameBoard) {
        switch (level) {
            case EASY -> {
                Random r = new Random();
                int y;
                int x;
                do {
                    y = r.nextInt(2);
                    x = r.nextInt(2);
                } while (gameBoard.boardArray[y][x] != ' ');

                return new int[]{y, x};
            }
        }
        return new int[0];
    }

    public boolean isAi() {
        return ai;
    }

    enum DifficultyLevel {
        EASY,
        MEDIUM,
        HARD

    }
}
