package gui;

import simulation.MainSimulation;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;

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
    /** Initial/Last time on system clock when thread starts. */
    private long lastTime;

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
        lastTime = Calendar.getInstance().get(Calendar.MILLISECOND); // For timer. #Brendon

        setLayout(new GridLayout(rows, columns));
        setBackground(Color.WHITE);
        setBorder(parent.getGeneralBorder());

        GridListener buttonListen = new GridListener();
        buttons = new GridPanelTile[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                GridPanelTile grid = new GridPanelTile(i, j);
                buttons[i][j] = grid;
                grid.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                grid.setPreferredSize(new Dimension(2, 2));
                grid.addActionListener(buttonListen);
                grid.setFont(gridFont);
                add(grid);
            }
        }
        //simulation.setPlaying(true); // DEBUGGING TEMP #Brendon
    }

    // This method allows the grid to update itself over time. #Brendon
    public void run(){
        System.out.println("Update thread running");
        long currentTime;
        int debuggingTickCount = 0;
        boolean keepRunning = true;
        // Keep this running until death.
        while(keepRunning) {
            System.out.println("Running");
            System.out.println(simulation.getPlaying());

            if(simulation.getPlaying()) {
                // Delay the ticker for debugging.
                try {
                    Thread.sleep(simulation.getSpeed() * 10);
                } catch (Exception e) { }

                // If longer than the "speed", do a tick.
                currentTime = Calendar.getInstance().get(Calendar.MILLISECOND);
                lastTime = currentTime;
                System.out.println("Tick " + debuggingTickCount);
                debuggingTickCount = debuggingTickCount + 1;
                simulation.stepForward();
                parent.updateDisplay();

                // End timer after 20 for debugging.
                /*
                if (debuggingTickCount > 20) {
                    keepRunning = false;
                    System.out.println("End of timer...");
                }
                */
            } else {
                try {
                    Thread.sleep(simulation.getSpeed() * 10);
                } catch (Exception e) {
                    System.out.println("Error on Sleep()...");
                }
            }
        }
    }
    public void updateDisplay() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buttons[i][j].setText(Character.toString(
                        parent.getSimulation().getEntity(i, j).getSymbol()));
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
