/*
 * Created by JFormDesigner on Tue Mar 12 00:22:25 ACDT 2024
 */
// use a swing worker for the model
// lock all buttons until, the swing worker says it's your turn

package tictactoe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.Arrays;

/**
 * @author james
 */
public class TicTacToe extends JFrame {
    JButton[][] buttons = new JButton[3][3];
    Game game;

    static boolean unTested = false;

    public TicTacToe() {
//        game = new Game("user", "user");
        initComponents();
        initButtons();
//        updateFromModel();
//        LabelStatus.setText(Game.GameState.GNS.msg);
        this.setVisible(true);
        buttonPanel.setEnabled(false);
    }

    public TicTacToe(boolean unTested) {
        this();
        TicTacToe.unTested = unTested;
        if (unTested) {
            ButtonPlayer1.setText("User");
            ButtonPlayer2.setText("User");
        }
    }

    private void initButtons() {
        for (int y = buttons.length - 1; y >= 0; y--) {
            for (int x = 0; x < buttons[1].length; x++) {
                JButton button = new JButton();
                buttons[y][x] = button;
                button.setFocusPainted(false);
                button.setName("Button" + Board.toAlgebraic(y, x));
                button.setFont(new Font("Tahoma", Font.PLAIN, 75));
                button.setText(" ");
                button.addActionListener(this::buttonPress);
                buttonPanel.add(button);
            }

        }

    }

    private void buttonPress(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (!(button.getText().isEmpty() || button.getText().matches("\\s")) || (game.state != Game.GameState.GNF && game.state != Game.GameState.GNS)) {
            return;
        }
        if (unTested) {
            buttonPanel.setEnabled(false);
        }
        String coordString = button.getName().substring(button.getName().length() - 2);
        new CustomWorker(game, Board.fromAlgebraic(coordString)).execute();

    }

    private void buttonPanelPropertyChange(PropertyChangeEvent e) {
        Container source = (Container) e.getSource();
        for (Component cp : source.getComponents()) {
            cp.setEnabled(source.isEnabled());
        }
    }

    private void reset() {
        buttonPanel.setEnabled(false);
        ButtonPlayer1.setEnabled(true);
        ButtonPlayer2.setEnabled(true);
        ButtonStartReset.setText("Start");
        LabelStatus.setText(Game.GameState.GNS.msg);
        Arrays.stream(buttonPanel.getComponents())
                .map(x -> (JButton) x)
                .forEach(x -> x.setText(" "));
    }

    private void Break(ActionEvent e) {
        System.out.println("Im just here because my creator doesnt know how to use a debugger properly");
    }

    private void playerButtonToggle(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (unTested) {
            Player.PlayerType current = Player.PlayerType.valueOf(button.getText().toUpperCase()); // Temp disable other difficulties for HS
            Player.PlayerType next = current.next();
            button.setText(next.toString());
        } else {
            button.setText(button.getText().equals("Human") ? "Robot" : "Human");
        }
    }

    private void startReset(ActionEvent e) {
        JButton button = ButtonStartReset;
        switch (button.getText()) {
            case "Start" -> start();

            case "Reset" -> reset();
        }
    }

    private void start() {
        game = new Game(ButtonPlayer1.getText(), ButtonPlayer2.getText());
        ButtonPlayer1.setEnabled(false);
        ButtonPlayer2.setEnabled(false);
        ButtonStartReset.setText("Reset");
        updateFromModel();
        aiPlay();
        if (unTested) {
            buttonPanel.setEnabled(!game.current.isAi());
        } else {
            buttonPanel.setEnabled(true);
        }
    }

    private void MenuQuickGame(ActionEvent e) {
        JMenuItem item = (JMenuItem) e.getSource();
        String[] text = item.getText().split(" ");
        ButtonPlayer1.setText(text[0].trim());
        ButtonPlayer2.setText(text[2].trim());
        reset();
        start();

    }

    private void aiPlay() {
        if (game.current.isAi()) {
            new CustomWorker(game, new int[]{-1, -1}).execute();
        }
    }

    private void updateFromModel() {
        Board.Cell[][] boardArray = game.gameBoard.boardArray;
        for (int y = 0; y < boardArray.length; y++) {
            for (int x = 0; x < boardArray.length; x++) {
                Board.Cell cell = boardArray[y][x];
                buttons[y][x].setText(String.valueOf(cell));
            }
        }
        updateStatus();
    }

    private void updateStatus() {
        Player.PlayerType playerType = game.current.level;
        String level;
        if (!unTested) {
            level = game.current.isAi() ? "Robot" : "Human";
        } else {
            level = playerType.toString();
        }
        LabelStatus.setText(game.state.msg.formatted(level, game.current.token));
    }

    class CustomWorker extends SwingWorker<Game.GameState, Object> {

        Game game;
        int[] coords;

        public CustomWorker(Game game, int[] coords) {
            this.game = game;
            this.coords = coords;
        }

        @Override
        protected Game.GameState doInBackground() {
            System.out.println("hello I am a new swing worker starting my name is: " + this);
            game.turn(coords);
            return game.state;
        }

        @Override
        protected void done() {
            System.out.println("Boy howdy. Hello i am the done method of: " + this);
            updateFromModel();
            if (unTested) {
                buttonPanel.setEnabled(!game.current.isAi());
            }
            //ai move
            if (game.state == Game.GameState.GNF) {
                aiPlay();
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - James Taylour
        menuBar = new JMenuBar();
        MenuGame = new JMenu();
        MenuHumanHuman = new JMenuItem();
        MenuHumanRobot = new JMenuItem();
        MenuRobotHuman = new JMenuItem();
        MenuRobotRobot = new JMenuItem();
        MenuExit = new JMenuItem();
        panel1 = new JPanel();
        controlPanel = new JPanel();
        ButtonPlayer1 = new JButton();
        ButtonStartReset = new JButton();
        ButtonPlayer2 = new JButton();
        buttonPanel = new JPanel();
        statusPanel = new JPanel();
        LabelStatus = new JLabel();
        Break = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe"); //NON-NLS
        setMinimumSize(new Dimension(450, 0));
        setPreferredSize(null);
        setName("this"); //NON-NLS
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar ========
        {
            menuBar.setName("menuBar"); //NON-NLS

            //======== MenuGame ========
            {
                MenuGame.setText("Game"); //NON-NLS
                MenuGame.setName("MenuGame"); //NON-NLS

                //---- MenuHumanHuman ----
                MenuHumanHuman.setText("Human vs Human"); //NON-NLS
                MenuHumanHuman.setName("MenuHumanHuman"); //NON-NLS
                MenuHumanHuman.addActionListener(e -> MenuQuickGame(e));
                MenuGame.add(MenuHumanHuman);

                //---- MenuHumanRobot ----
                MenuHumanRobot.setText("Human vs Robot"); //NON-NLS
                MenuHumanRobot.setName("MenuHumanRobot"); //NON-NLS
                MenuHumanRobot.addActionListener(e -> MenuQuickGame(e));
                MenuGame.add(MenuHumanRobot);

                //---- MenuRobotHuman ----
                MenuRobotHuman.setText("Robot vs Human"); //NON-NLS
                MenuRobotHuman.setName("MenuRobotHuman"); //NON-NLS
                MenuRobotHuman.addActionListener(e -> MenuQuickGame(e));
                MenuGame.add(MenuRobotHuman);

                //---- MenuRobotRobot ----
                MenuRobotRobot.setText("Robot vs Robot"); //NON-NLS
                MenuRobotRobot.setName("MenuRobotRobot"); //NON-NLS
                MenuRobotRobot.addActionListener(e -> MenuQuickGame(e));
                MenuGame.add(MenuRobotRobot);
                MenuGame.addSeparator();

                //---- MenuExit ----
                MenuExit.setText("Exit"); //NON-NLS
                MenuExit.setName("MenuExit"); //NON-NLS
                MenuGame.add(MenuExit);
            }
            menuBar.add(MenuGame);
        }
        contentPane.add(menuBar, BorderLayout.NORTH);

        //======== panel1 ========
        {
            panel1.setBorder(new EmptyBorder(0, 5, 5, 5));
            panel1.setName("panel1"); //NON-NLS
            panel1.setLayout(new BorderLayout(0, 5));

            //======== controlPanel ========
            {
                controlPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
                controlPanel.setName("controlPanel"); //NON-NLS
                controlPanel.setLayout(new GridLayout(1, 3, 25, 0));

                //---- ButtonPlayer1 ----
                ButtonPlayer1.setText("Human"); //NON-NLS
                ButtonPlayer1.setName("ButtonPlayer1"); //NON-NLS
                ButtonPlayer1.addActionListener(e -> playerButtonToggle(e));
                controlPanel.add(ButtonPlayer1);

                //---- ButtonStartReset ----
                ButtonStartReset.setText("Start"); //NON-NLS
                ButtonStartReset.setName("ButtonStartReset"); //NON-NLS
                ButtonStartReset.addActionListener(e -> startReset(e));
                controlPanel.add(ButtonStartReset);

                //---- ButtonPlayer2 ----
                ButtonPlayer2.setText("Human"); //NON-NLS
                ButtonPlayer2.setName("ButtonPlayer2"); //NON-NLS
                ButtonPlayer2.addActionListener(e -> playerButtonToggle(e));
                controlPanel.add(ButtonPlayer2);
            }
            panel1.add(controlPanel, BorderLayout.NORTH);

            //======== buttonPanel ========
            {
                buttonPanel.setMinimumSize(new Dimension(450, 450));
                buttonPanel.setPreferredSize(new Dimension(450, 450));
                buttonPanel.setName("buttonPanel"); //NON-NLS
                buttonPanel.addPropertyChangeListener("enabled", e -> buttonPanelPropertyChange(e)); //NON-NLS
                buttonPanel.setLayout(new GridLayout(3, 3, 5, 5));
            }
            panel1.add(buttonPanel, BorderLayout.CENTER);

            //======== statusPanel ========
            {
                statusPanel.setName("statusPanel"); //NON-NLS
                statusPanel.setLayout(new BorderLayout(5, 0));

                //---- LabelStatus ----
                LabelStatus.setText("Game is not started"); //NON-NLS
                LabelStatus.setHorizontalAlignment(SwingConstants.LEFT);
                LabelStatus.setName("LabelStatus"); //NON-NLS
                statusPanel.add(LabelStatus, BorderLayout.WEST);

                //---- Break ----
                Break.setText("Break"); //NON-NLS
                Break.setVisible(false);
                Break.setName("Break"); //NON-NLS
                Break.addActionListener(e -> Break(e));
                statusPanel.add(Break, BorderLayout.CENTER);
            }
            panel1.add(statusPanel, BorderLayout.SOUTH);
        }
        contentPane.add(panel1, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - James Taylour
    private JMenuBar menuBar;
    private JMenu MenuGame;
    private JMenuItem MenuHumanHuman;
    private JMenuItem MenuHumanRobot;
    private JMenuItem MenuRobotHuman;
    private JMenuItem MenuRobotRobot;
    private JMenuItem MenuExit;
    private JPanel panel1;
    private JPanel controlPanel;
    private JButton ButtonPlayer1;
    private JButton ButtonStartReset;
    private JButton ButtonPlayer2;
    private JPanel buttonPanel;
    private JPanel statusPanel;
    private JLabel LabelStatus;
    private JButton Break;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on


}
