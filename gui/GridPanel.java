package gui;

import simulation.MainSimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**********************************************************************
 * Grid Panel for SurvivalSimulation350 GUI.
 * Allows the user to view and select entities in the simulation
 * grid.
 *
 * @author Anderson Hudson
 *********************************************************************/
public class GridPanel extends TypedPanel {
    /** Number of rows in the grid. */
    private int rows;
    /** Number of columns in the grid. */
    private int columns;
    /** The simulation logic object that the GUI displays. */
    private MainSimulation simulation;
    /** Font used by the grid buttons. */
    private final Font gridFont = new Font("Times New Roman", Font.PLAIN, 9);

    /** Defer to TypedPanel constructor.
     * @param par The controlling GUI object.
     */
    GridPanel(final MainGUI par) {
        super(par);
    }

    /** Initializes GUI components. */
    public void initGUI() {
        simulation = parent.getSimulation();
        rows = simulation.getRows();
        columns = simulation.getColumns();

        setLayout(new GridLayout(rows, columns));
        setBackground(Color.WHITE);
        setBorder(parent.getGeneralBorder());

        GridListener buttonListen = new GridListener();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                GridPanelTile grid = new GridPanelTile(i, j);
                grid.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                grid.setPreferredSize(new Dimension(2, 2));
                grid.addActionListener(buttonListen);
                grid.setText(Character.toString(
                        parent.getSimulation().getEntity(i, j).getSymbol()));
                grid.setFont(gridFont);
                add(grid);
            }
        }
    }
    /** Listens for user clicks on grid. */
    private class GridListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            GridPanelTile button = (GridPanelTile) e.getSource();

            //set the currently selected entity to the tile you just pressed
            parent.setCurrentEntity(
                    parent.getSimulation().getEntity(
                            button.getRow(), button.getColumn()));
        }
    }
}
