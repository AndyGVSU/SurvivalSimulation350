package gui;

import simulation.*;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**********************************************************************
 * Grid Panel for SurvivalSimulation350 GUI.
 * Allows the user to view and select entities in the simulation
 * grid.
 *
 * @author Anderson Hudson
 *********************************************************************/
public class GridPanel extends JPanel implements TypedPanel, Runnable {
    /** The controlling GUI component. */
    private MainGUI parent;
    /** Number of rows in the grid. */
    private int rows;
    /** Number of columns in the grid. */
    private int columns;
    /** The simulation logic object that the GUI displays. */
    private MainSimulation simulation;
    /** Font used by the grid buttons. */
    private final Font gridFont = new Font("Times New Roman", Font.PLAIN, 9);
    /** Grid colors. **/
    private Color[] entityColors;
    /** Two-dimensional array of buttons that hold entity data. */
    private GridPanelTile[][] buttons;

    /** Constructor.
     * @param par The controlling GUI object.
     */
    GridPanel(final MainGUI par) {
        parent = par;
        initGUI();  // Initialize
    }

    /** Initializes GUI components. */
    public void initGUI() {
        simulation = parent.getSimulation();
        rows = simulation.getRows();
        columns = simulation.getColumns();
        entityColors = new Color[]{Color.GREEN,Color.CYAN,Color.ORANGE,Color.green,Color.WHITE,Color.RED};

        setLayout(new GridLayout(rows, columns));
        setBackground(Color.BLACK);
        setBorder(parent.getGeneralBorder());

        GridListener buttonListen = new GridListener();
        buttons = new GridPanelTile[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                GridPanelTile grid = new GridPanelTile(i, j);
                buttons[i][j] = grid;
                grid.setBackground(Color.BLACK);
                grid.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                grid.setPreferredSize(new Dimension(2, 2));
                grid.addActionListener(buttonListen);
                grid.setFont(gridFont);
                add(grid);
            }
        }
    }

    /** This method allows the grid to update itself over time. */
    public void run() {
        boolean keepRunning = true;
        // Keep this running until death.
        while (keepRunning) {
            //makes the system work
            System.out.print("");
            if (parent.getPlaying()) {
                try {
                    Thread.sleep(simulation.getSpeed() * 10);
                }
                catch (Exception e) {}

                // If longer than the "speed", do a tick.
                simulation.stepForward();
                parent.updateCurrentEntity();
                parent.updateDisplay();
            }
        }
    }

    /** Iterates through the display-grid and updates GUI elements. */
    public void updateDisplay() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Entity e = parent.getSimulation().getEntity(i, j);
                buttons[i][j].setText(Character.toString(
                        e.getSymbol()));
                buttons[i][j].setForeground(entityColors[e.getColor()]);
            }
        }
    }

    /** Listens for user clicks on grid. */
    private class GridListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            GridPanelTile button = (GridPanelTile) e.getSource();
            int brow = button.getRow();
            int bcol = button.getColumn();

            if(parent.getClickAdds()) {
                Entity addType = parent.getClickEntity();
                Entity toAdd = null;
                Entity current = parent.getSimulation().getEntity(
                        button.getRow(), button.getColumn());

                if (addType instanceof Grass) {
                    toAdd = new Grass(parent.getSimulation(), null,0, brow, bcol);
                }
                if (addType instanceof Dirt) {
                    toAdd = new Dirt(parent.getSimulation(), null,0, brow, bcol);
                }
                if (addType instanceof Air) {
                    toAdd = new Air(parent.getSimulation(), null,0, brow, bcol);
                }
                if (toAdd != null &&
                        !(current instanceof Plant) &&
                        !(current instanceof Collector))
                    parent.getSimulation().setEntity(brow,bcol,toAdd);

            }
            //set the currently selected entity to the tile you just pressed
            parent.setCurrentEntity(
                    parent.getSimulation().getEntity(
                            button.getRow(), button.getColumn()));
        }
    }
}
