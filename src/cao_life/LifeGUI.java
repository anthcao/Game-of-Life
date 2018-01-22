/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cao_life;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

/**
 *
 * @author caoa5300
 */
public class LifeGUI {

    JMenuBar menuBar;
    JMenu patterns;
    JMenuItem glider, tenCellRow, exploder, spaceShip;
    JFrame frame;
    JPanel contentPane;
    JLabel[][] grid;
    JLabel title;
    JButton next, reset, start, stop;
    int[][] temp = new int[32][32];
    Life console = new Life(temp);
    Timer time;
    JSlider slider;

    public LifeGUI() {

        /* Creates and sets up the frame */
        frame = new JFrame("Conway's Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();

        patterns = new JMenu("Patterns");
        menuBar.add(patterns);

        glider = new JMenuItem("Glider");
        glider.addActionListener(new PatternsListener());
        patterns.add(glider);

        tenCellRow = new JMenuItem("Ten Cell Row");
        tenCellRow.addActionListener(new PatternsListener());
        patterns.add(tenCellRow);

        exploder = new JMenuItem("Exploder");
        exploder.addActionListener(new PatternsListener());
        patterns.add(exploder);

        spaceShip = new JMenuItem("Space Ship");
        spaceShip.addActionListener(new PatternsListener());
        patterns.add(spaceShip);

        frame.setJMenuBar(menuBar);
        
        /* Creates a content pane */
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        grid = new JLabel[32][32];
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid.length - 1; j++) {
                grid[i][j] = new JLabel("0");
                grid[i][j].setOpaque(true);
                grid[i][j].setBackground(Color.LIGHT_GRAY);
                grid[i][j].setForeground(Color.LIGHT_GRAY);
                grid[i][j].setPreferredSize(new Dimension(15, 15));
                grid[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                grid[i][j].addMouseListener(new GridClick());
                c.gridx = i - 1;
                c.gridy = j;
                contentPane.add(grid[i][j], c);
            }
        }

        next = new JButton("Next");
        next.addActionListener(new ButtonNext());
        next.setFont(new Font("Monotype Corsiva", Font.BOLD, 14));
        c.gridx = 0;
        c.gridy = 32;
        c.gridwidth = 5;
        c.insets = new Insets(5, 0, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(next, c);

        reset = new JButton("Reset");
        reset.addActionListener(new ButtonReset());
        reset.setFont(new Font("Monotype Corsiva", Font.BOLD, 14));
        c.gridx = 5;
        contentPane.add(reset, c);

        start = new JButton("Start");
        start.addActionListener(new ButtonStart());
        start.setFont(new Font("Monotype Corsiva", Font.BOLD, 14));
        c.gridx = 10;
        contentPane.add(start, c);

        stop = new JButton("Stop");
        stop.addActionListener(new ButtonStop());
        stop.setFont(new Font("Monotype Corsiva", Font.BOLD, 14));
        c.gridx = 15;
        contentPane.add(stop, c);

        slider = new JSlider(50, 750, 400);
        slider.setPreferredSize(new Dimension(0, 20));
        c.gridx = 20;
        c.gridwidth = 10;
        contentPane.add(slider, c);

        /* Adds the content pane to the frame */
        frame.setContentPane(contentPane);

        /* sets components to their preferred size and makes it visable */
        frame.pack();
        frame.setVisible(true);

        time = new Timer(100, new ButtonStart());
    }

    class PatternsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem item = (JMenuItem) e.getSource();
            resetTemp();
            switch (item.getText()) {
                case "Glider":
                    temp[15][15] = 1;
                    temp[16][16] = 1;
                    temp[14][17] = 1;
                    temp[15][17] = 1;
                    temp[16][17] = 1;
                    break;
                case "Ten Cell Row":
                    temp[11][16] = 1;
                    temp[12][16] = 1;
                    temp[13][16] = 1;
                    temp[14][16] = 1;
                    temp[15][16] = 1;
                    temp[16][16] = 1;
                    temp[17][16] = 1;
                    temp[18][16] = 1;
                    temp[19][16] = 1;
                    temp[20][16] = 1;
                    break;
                case "Exploder":
                    temp[13][13] = 1;
                    temp[15][13] = 1;
                    temp[17][13] = 1;
                    temp[13][14] = 1;
                    temp[17][14] = 1;
                    temp[13][15] = 1;
                    temp[17][15] = 1;
                    temp[13][16] = 1;
                    temp[17][16] = 1;
                    temp[13][17] = 1;
                    temp[15][17] = 1;
                    temp[17][17] = 1;
                    break;
                case "Space Ship":
                    temp[14][13] = 1;
                    temp[15][13] = 1;
                    temp[16][13] = 1;
                    temp[17][13] = 1;
                    temp[13][14] = 1;
                    temp[17][14] = 1;
                    temp[17][15] = 1;
                    temp[13][16] = 1;
                    temp[16][16] = 1;
                    break;
            }
            console.setPattern(temp);
            updateGrid();
        }
    }

    class ButtonNext implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            sendGrid();
            console.takeStep();
            updateGrid();
        }
    }

    class ButtonReset implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            console.killAllCells();
            updateGrid();
        }
    }

    class ButtonStart implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            time.setDelay(800 - slider.getValue());
            time.start();
            sendGrid();
            console.takeStep();
            updateGrid();
        }
    }

    class ButtonStop implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            time.stop();
        }
    }

    class GridClick extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();

            if (label.getText().equals("0")) {
                label.setText("1");
                label.setBackground(Color.YELLOW);
                label.setForeground(Color.YELLOW);
            } else {
                label.setText("0");
                label.setBackground(Color.LIGHT_GRAY);
                label.setForeground(Color.LIGHT_GRAY);
            }
        }
    }

    private void resetTemp() {
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid.length - 1; j++) {
                temp[i][j] = 0;
            }
        }
    }

    private void sendGrid() {
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid.length - 1; j++) {
                temp[i][j] = Integer.parseInt(grid[i][j].getText());
            }
        }
        console.setPattern(temp);
    }

    private void updateGrid() {
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid.length - 1; j++) {
                grid[i][j].setText(Integer.toString(console.getGrid(i, j)));
            }
        }

        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid.length - 1; j++) {
                if (grid[i][j].getText().equals("1")) {
                    grid[i][j].setBackground(Color.YELLOW);
                    grid[i][j].setForeground(Color.YELLOW);
                } else {
                    grid[i][j].setBackground(Color.LIGHT_GRAY);
                    grid[i][j].setForeground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    /**
     * Create and show the GUI 
     * pre: none 
     * post: GUI is created and shown
     */
    private static void runGUI() {
        LifeGUI gameOfLife = new LifeGUI();
    }

    /**
     * Runs GUI from a event dispatching thread 
     * pre: none 
     * post: GUI is ran
     *
     * @param args
     */
    public static void main(String[] args) {
        /* methods that create and show a GUI should be
         run from an event-dispatching thread */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                runGUI();
            }
        });
    }
}
