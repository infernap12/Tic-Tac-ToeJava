/*
 * Created by JFormDesigner on Tue Mar 12 00:22:25 ACDT 2024
 */
// use a swing worker for the model
// lock all buttons until, the swing worker says it's your turn

package tictactoe;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;

/**
 * @author james
 */
public class TicTacToe extends JFrame {
    JButton[][] buttons = new JButton[3][3];
    Game game = new Game("user", "user");

    public TicTacToe() {
        initComponents();
        initButtons();
        this.setVisible(true);
    }

    private void initButtons() {
        for (int y = buttons.length - 1; y >= 0; y--) {
            for (int x = 0; x < buttons[1].length; x++) {
                JButton button = new JButton();
                buttons[y][x] = button;
                button.setFocusPainted(false);
                button.setName("Button" + Board.toAlgebraic(y, x));
                button.setFont(new Font("Tahoma", Font.PLAIN, 75));
                button.addActionListener(this::buttonPress);
                buttonPanel.add(button);
            }

        }

    }

    private void buttonPress(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (!button.getText().isEmpty() || (game.state != Game.GameState.GNF && game.state != Game.GameState.GNS)) {
            return;
        }
        buttonPanel.setEnabled(false);
        String coordString = button.getName().substring(button.getName().length() - 2);
        new CustomWorker(game, Board.fromAlgebraic(coordString)).execute();
    }

    private void buttonPanelPropertyChange(PropertyChangeEvent e) {
        Container source = (Container) e.getSource();
        for (Component cp : source.getComponents()) {
            cp.setEnabled(source.isEnabled());
        }
    }

    private void reset(ActionEvent e) {

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - James Taylour
        buttonPanel = new JPanel();
        statusPanel = new JPanel();
        ButtonReset = new JButton();
        LabelStatus = new JLabel();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe"); //NON-NLS
        setMinimumSize(new Dimension(450, 0));
        setPreferredSize(null);
        setName("this"); //NON-NLS
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== buttonPanel ========
        {
            buttonPanel.setMinimumSize(new Dimension(450, 450));
            buttonPanel.setPreferredSize(new Dimension(450, 450));
            buttonPanel.setName("buttonPanel"); //NON-NLS
            buttonPanel.addPropertyChangeListener("enabled", e -> buttonPanelPropertyChange(e)); //NON-NLS
            buttonPanel.setLayout(new GridLayout(3, 3, 5, 5));
        }
        contentPane.add(buttonPanel, BorderLayout.CENTER);

        //======== statusPanel ========
        {
            statusPanel.setName("statusPanel"); //NON-NLS
            statusPanel.setLayout(new BorderLayout());

            //---- ButtonReset ----
            ButtonReset.setText("Reset"); //NON-NLS
            ButtonReset.setHorizontalAlignment(SwingConstants.RIGHT);
            ButtonReset.setName("ButtonReset"); //NON-NLS
            ButtonReset.addActionListener(e -> reset(e));
            statusPanel.add(ButtonReset, BorderLayout.EAST);

            //---- LabelStatus ----
            LabelStatus.setText("Game is not started"); //NON-NLS
            LabelStatus.setHorizontalAlignment(SwingConstants.LEFT);
            LabelStatus.setName("LabelStatus"); //NON-NLS
            statusPanel.add(LabelStatus, BorderLayout.WEST);
        }
        contentPane.add(statusPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - James Taylour
    private JPanel buttonPanel;
    private JPanel statusPanel;
    private JButton ButtonReset;
    private JLabel LabelStatus;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    class CustomWorker extends SwingWorker<Game.GameState, Object> {

        Game game;
        int[] coords;

        public CustomWorker(Game game, int[] coords) {
            this.game = game;
            this.coords = coords;
        }

        @Override
        protected Game.GameState doInBackground() throws Exception {
            game.turn(coords);
            return game.state;
        }

        @Override
        protected void done() {
            Game.GameState state = game.state;
            LabelStatus.setText(game.state.msg);

            Board.Cell[][] boardArray = game.gameBoard.boardArray;
            for (int y = 0; y < boardArray.length; y++) {
                for (int x = 0; x < boardArray.length; x++) {
                    Board.Cell cell = boardArray[y][x];
                    buttons[y][x].setText(String.valueOf(cell));
                }

            }
            buttonPanel.setEnabled(true);
            if (game.current.isAi()) {
                new CustomWorker(game, new int[]{-1, -1});
            }

        }
    }
}
