package tictactoe;

import java.util.Arrays;

public class InputManager extends com.infernap12.inputUtils.InputManager {


    public InputManager(boolean loggerEnabled) {
        super(loggerEnabled);
    }

    int[] askCoords() {
        int[] output = new int[0];
        while (true) {
            try {
                output = new int[]{requestIntRange(1, 3), requestIntRange(1, 3)};
                if (this.loggerEnabled) {
                    logger.info(Arrays.toString(output));
                }
            } catch (NumberFormatException n) {
                if (this.loggerEnabled) {
                    logger.warning(String.valueOf(n.getLocalizedMessage()));
                }
                System.out.println(n.getLocalizedMessage());

            } catch (Exception e) {
                if (this.loggerEnabled) {
                    logger.warning(String.valueOf(e.getLocalizedMessage()));
                }
                System.out.print("Error! Indeterminate error. Try again.");
                continue;
            }
            break;
        }
        return output;
    }

    String askLine() {
        String input = scanner.nextLine();
        if (this.loggerEnabled) {
            logger.info(input);
        }
        return input;
    }
}
