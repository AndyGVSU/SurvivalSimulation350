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
        generalBorder = BorderFactory.createLineBorder(Color.black, 2);
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
        ((SimPanel) currentPanel).getDetailPanel().updateText();
        return currentEntity;
    }
    /** Sets whether the GUI is playing.
     * @param play */
    public void setPlaying(final boolean play) {
        playing = play;
    }
    /** @return whether the GUI is playing. */
    public boolean getPlaying() {
        return playing;
    }

    /** Instantiate the GUI.
     * @param args Command-line arguments. */
    public static void main(final String[] args) {
        MainGUI gui = new MainGUI(new MainSimulation(SIM_SIZE, SIM_SIZE));
    }
}


