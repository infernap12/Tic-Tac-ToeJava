package com.infernap12.inputUtils;

import java.util.Scanner;
import java.util.logging.Logger;

public class InputManager {
    protected Logger logger;
    protected Scanner scanner;
    protected boolean loggerEnabled;

    public InputManager() {
        this.logger = Logger.getLogger(this.getClass().getName());
        this.scanner = new Scanner(System.in);
        this.loggerEnabled = false;
    }
    public InputManager(boolean loggerEnabled) {
        this.logger = Logger.getLogger(this.getClass().getName());
        this.scanner = new Scanner(System.in);
        this.loggerEnabled = loggerEnabled;
    }

    protected int requestIntRange(int min, int max) {
        int intput;
        String input = scanner.nextLine();
        try {
            intput = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("You should enter numbers!");
        }
        if (!(intput >= min && intput <= max)){
            throw new NumberFormatException("Coordinates should be from 1 to 3!");
        }
        return intput;

    }
}
