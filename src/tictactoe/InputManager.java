package tictactoe;

import java.util.LinkedList;
import java.util.Queue;

public class InputManager extends com.infernap12.inputUtils.InputManager {
    public InputManager(boolean loggerEnabled) {
        super(loggerEnabled);
    }

    Queue<Character> getQueue() {
        String input = scanner.nextLine().replace('_', ' ');
        Queue<Character> q = new LinkedList<>();
        for (char c : input.toCharArray()) {
            q.add(c);
        }

        return q;
    }

    int[] getValidMove(Board gameBoard) {
        String input;

        while (true) {
            input = scanner.nextLine();
            if (!input.matches("[0-9]+\\s[0-9]+")) {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (!input.matches("[1-3] [1-3]")) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            int y = Integer.parseInt(String.valueOf(input.trim().charAt(0))) - 1;
            int x = Integer.parseInt(String.valueOf(input.trim().charAt(2))) - 1;
            if (gameBoard.boardArray[y][x] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            return new int[]{y, x};
        }
    }

    String askLine() {
        String input = scanner.nextLine();
        if (this.loggerEnabled) {
            logger.info(input);
        }
        return input;
    }

    String askCommand() {
        while (true) {
            System.out.print("Input command: ");
            String input = this.askLine();
            if (input.matches("exit")) {
                return "exit";
            }
            if (input.matches("start\\s[A-Za-z]+\\s[A-Za-z]+")) {
                String[] arr = input.split(" ");
                boolean validP1 = isValidPlayer(arr[1].toUpperCase());
                boolean validP2 = isValidPlayer(arr[2].toUpperCase());
                if (validP1 && validP2) {
                    return input;
                }
            } else {
                System.out.println("Bad parameters!");
            }
        }
    }

    boolean isValidPlayer(String player) {
        try {
            Player.PlayerType.valueOf(player); // This will throw IllegalArgumentException if not valid
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
