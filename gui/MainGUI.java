package gui;

import simulation.MainSimulation;
import simulation.Entity;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import java.awt.Dimension;
import java.awt.Color;

/**********************************************************************
 * MainGUI for SurvivalSimulation350 GUI.
 * Keeps track of all vital sub-panels:
 * the title panel, and
 * the simulation panel.
 *
 * @author Anderson Hudson
 *********************************************************************/
public class MainGUI extends JFrame {
    /** The fixed simulation size. */
    private static final int SIM_SIZE = 32;
    /** The GUI's window height. */
    static final int WINDOW_HEIGHT = 720;
    /** The GUI's window width. */
    static final int WINDOW_WIDTH = 960;
    /** The current panel the GUI is displaying. */
    private JPanel currentPanel;
    /** The simulation logic object, used by every GUI sub-panel. */
    private MainSimulation simulation;
    /** The decorative border used by all panels. */
    private Border generalBorder;
    /** The current entity selected in the simulation. */
    private Entity currentEntity;
    /** Whether the simulation object is playing or not. */
    private boolean playing;

    /** Constructor.
     * @param sim The simulation logic object. */
    public MainGUI(final MainSimulation sim) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setResizable(false);
        changeWindow(PanelType.TITLE_PANEL);

        simulation = sim;
        currentEntity = simulation.getEntity(0,0);
        generalBorder = BorderFactory.createLineBorder(Color.black, 2);
        playing = false;
    }
    /** Change to a new sub-panel.
     * @param newType the new PanelType to switch to.*/
    public void changeWindow(final PanelType newType) {
        //remove previous panel (the panel is always the first component added)

        switch (newType) {
            case TITLE_PANEL:
                currentPanel = new TitlePanel(this);
                break;
            case SIMULATION_PANEL:
                currentPanel = new SimPanel(this);
                ((SimPanel) currentPanel).startGridThread(); // Sets the running thread.
                break;
            default:
                throw new IllegalArgumentException("INVALID PANEL TYPE");
        }

        //pack GUI frame
        setContentPane(currentPanel);
        pack();
        setVisible(true);
    }
    /** @return the parent simulation object. */
    public MainSimulation getSimulation() {
        return simulation;
    }
    /** @return the general border. */
    public Border getGeneralBorder() {
        return generalBorder;
        }
    /** @return the current entity. */
    public Entity getCurrentEntity() {
        return currentEntity;
    }
    /** Sets the current entity.
     * @param e The new entity to set as the GUI's current entity.
     * @return The newly selected current entity.*/
    public Entity setCurrentEntity(final Entity e) {
        currentEntity = e;
        updateDisplay();
        return currentEntity;
    }
    public void updateCurrentEntity() {
        int r = getCurrentEntity().getRow();
        int c = getCurrentEntity().getColumn();
        setCurrentEntity(simulation.getEntity(r,c));
    }
    /** Sets whether the GUI is playing.
     * @param play */
    public void setPlaying(final boolean play) {
        playing = play;
        lockGUI(playing);
    }
    /** @return whether the GUI is playing. */
    public boolean getPlaying() {
        return playing;
    }

    public void updateDisplay() {
        ((SimPanel) currentPanel).updateDisplay();
    }

    public void lockGUI(boolean lock) {
        ((SimPanel) currentPanel).lockGUI(lock);
    }
    /** Instantiate the GUI.
     * @param args Command-line arguments. */
    public static void main(final String[] args) {
        MainGUI gui = new MainGUI(new MainSimulation(SIM_SIZE, SIM_SIZE));
    }
}


