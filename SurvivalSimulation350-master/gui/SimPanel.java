package gui;

import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.Dimension;

/**********************************************************************
 * Simulation Panel for SurvivalSimulation350 GUI.
 * Provides main control over the simulation logic object.
 * Uses an options panel, environment control panel, an entity
 * viewing and adding panel, and displays the simulation grid.
 *
 * @author Anderson Hudson
 *********************************************************************/
public class SimPanel extends JPanel implements TypedPanel {
    /** The controlling GUI component. */
    private MainGUI parent;
    /** Panel that contains options and simulation grid. */
    private JPanel topPanel;
    /** Panel that contains sliders, entity view/add. */
    private DetailPanel detailPanel;
    /** Panel that displays the simulation grid. */
    private GridPanel gridPanel;
    /** Panel that controls the simulation. */
    private OptionPanel optionPanel;
    /** Height of top panel. */
    private final int topHeight = 480;
    /** Height of detail panel. */
    private final int detailHeight = 240;
    /** Width of option panel. */
    private final int optionWidth =  MainGUI.WINDOW_WIDTH / 3;
    /** Width of grid panel. */
    private final int gridWidth = optionWidth * 2;

    /** Constructor.
     * @param par The controlling GUI object.
     */
    SimPanel(final MainGUI par) {
        parent = par;
        initGUI();
    }

    /** Initialize GUI components. */
    public void initGUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        int totalWidth = MainGUI.WINDOW_WIDTH;

        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setPreferredSize(new Dimension(totalWidth, topHeight));

        optionPanel = new OptionPanel(parent);
        optionPanel.setPreferredSize(new Dimension(optionWidth, topHeight));

        gridPanel = new GridPanel(parent);
        gridPanel.setPreferredSize(new Dimension(gridWidth, topHeight));

        detailPanel = new DetailPanel(parent);
        detailPanel.setPreferredSize(new Dimension(totalWidth, detailHeight));

        add(topPanel);

        topPanel.add(optionPanel);
        topPanel.add(gridPanel);
        add(detailPanel);
    }

    /** @return The detail panel. */
    public DetailPanel getDetailPanel() {
        return detailPanel;
    }
}
