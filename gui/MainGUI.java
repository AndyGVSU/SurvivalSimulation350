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
public final class MainGUI extends JFrame {
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
    /** Whether a click on the grid views or
     * adds a new entity or views an entity. */
    private boolean clickAdds;
    /** A template entity to add using the entity selection panel. */
    private Entity clickEntity;

    /** Constructor.
     * @param sim The simulation logic object. */
    public MainGUI(final MainSimulation sim) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setResizable(false);
        changeWindow(PanelType.TITLE_PANEL);

        simulation = sim;
        currentEntity = simulation.getEntity(0, 0);
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
                // Sets the running thread.
                ((SimPanel) currentPanel).startGridThread();
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
    /** Refreshes the current entity for display purposes,
     * in case the entity in the selected grid button is changed.*/
    public void updateCurrentEntity() {
        int r = getCurrentEntity().getRow();
        int c = getCurrentEntity().getColumn();
        setCurrentEntity(simulation.getEntity(r, c));
    }
    /** @param play Sets whether the GUI is playing.*/
    public void setPlaying(final boolean play) {
        playing = play;
        lockGUI(playing);
    }
    /** @return whether the GUI is playing. */
    public boolean getPlaying() {
        return playing;
    }

    /** @param adds Sets whether a click on a grid button adds an entity. */
    public void setClickAdds(final boolean adds) {
        clickAdds = adds;
    }
    /** @param e The new entity to set to  */
    public void setClickEntity(final Entity e) {
        clickEntity = e;
    }
    /** @return The entity type to check against
     * when clicking on grid buttons. */
    public Entity getClickEntity() {
        return clickEntity;
    }
    /** @return Whether a mouse click on a grid button adds an entity
     * or views it. */
    public boolean getClickAdds() {
        return clickAdds;
    }
    /** Calls lockEntityAdd for the simulation panel. */
    public void lockEntityAdd() {
        ((SimPanel) currentPanel).lockEntityAdd();
    }
    /** Calls updateDisplay for the simulation panel. */
    public void updateDisplay() {
        ((SimPanel) currentPanel).updateDisplay();
    }
    /** @param lock Whether to disable the simulation panel's components. */
    public void lockGUI(final boolean lock) {
        ((SimPanel) currentPanel).lockGUI(lock);
    }
    /** Instantiate the GUI.
     * @param args Command-line arguments. */
    public static void main(final String[] args) {
        MainGUI gui = new MainGUI(new MainSimulation(SIM_SIZE, SIM_SIZE));
    }
}


