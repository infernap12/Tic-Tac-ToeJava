package tictactoe;

public class Main {
    static InputManager input = new InputManager(false);

    public static void main(String[] args) {
        while (true) {
            String a = input.askCommand();
            if (a.equalsIgnoreCase("exit")) {
                System.exit(0);
            } else {
                String[] arr = a.split(" ");
                Game.run(arr[1], arr[2]);
            }


        }
    }


}

