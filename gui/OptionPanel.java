package gui;

import simulation.MainSimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**********************************************************************
 * Option Panel for SurvivalSimulation350 GUI.
 * Allows the user to perform multiple administrative tasks for the
 * simulation using a grid of buttons.
 *
 * @author Anderson Hudson
 *********************************************************************/
public class OptionPanel extends TypedPanel {

    /** Button that sends the user to the title screen. */
    private JButton titleButton;
    /** Button that switches the simulation layout to a default. */
    private JButton defaultsButton;
    /** Button that plays the simulation. */
    private JButton playButton;
    /** Button that pauses the simulation. */
    private JButton pauseButton;
    /** Button that steps the simulation backwards one step. */
    private JButton stepBackButton;
    /** Button that steps the simulation forwards one step. */
    private JButton stepForwardButton;
    /** Button that saves the current simulation. */
    private JButton saveButton;
    /** Button that loads a simulation from a file. */
    private JButton loadButton;
    /** How many rows of buttons are used. */
    private final int rows = 4;
    /** How many columns of buttons are used. */
    private final int columns = 2;
    /** Space between buttons in the button grid. */
    private final int buttonSpacing = 50;
    /** Blank space (horizontal). */
    private final Dimension horizontalSpace = new Dimension(30, 1);
    /** Blank space (vertical). */
    private final Dimension verticalSpace = new Dimension(1, 30);

    /** Defer to TypedPanel constructor.
     * @param par The controlling GUI object.
     */
    OptionPanel(final MainGUI par) {
        super(par);
    }

    /** Initialize GUI components. */
    public void initGUI() {
        Color panelColor = Color.GRAY;

        //inner button panel
        JPanel innerPanel = new JPanel();
        GridLayout layout = new GridLayout(rows, columns);
        layout.setHgap(buttonSpacing);
        layout.setVgap(buttonSpacing);
        innerPanel.setLayout(layout);

        //main option panel
        setBorder(parent.getGeneralBorder());
        setBackground(panelColor);
        innerPanel.setBackground(panelColor);
        setLayout(new BorderLayout());

        //add buttons
        titleButton = new JButton("TITLE");
        defaultsButton = new JButton("123");
        playButton = new JButton("PLAY");
        pauseButton = new JButton("PAUSE");
        stepBackButton = new JButton("<<");
        stepForwardButton = new JButton(">>");
        saveButton = new JButton("SAVE");
        loadButton = new JButton("LOAD");

        add(Box.createRigidArea(verticalSpace), BorderLayout.NORTH);
        add(Box.createRigidArea(verticalSpace), BorderLayout.SOUTH);
        add(Box.createRigidArea(horizontalSpace), BorderLayout.WEST);
        add(Box.createRigidArea(horizontalSpace), BorderLayout.EAST);
        add(innerPanel, BorderLayout.CENTER);

        innerPanel.add(titleButton);
        innerPanel.add(defaultsButton);
        innerPanel.add(playButton);
        innerPanel.add(pauseButton);
        innerPanel.add(stepBackButton);
        innerPanel.add(stepForwardButton);
        innerPanel.add(saveButton);
        innerPanel.add(loadButton);

        titleButton.addActionListener(new ButtonClick());
    }
    /** Listener for user mouse click for all buttons. */
    private class ButtonClick implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            JButton source = (JButton) e.getSource();
            MainSimulation simulation = parent.getSimulation();

            if (source == titleButton) {
                parent.changeWindow(PanelType.TITLE_PANEL);
            }
            if (source == defaultsButton) {
                int deleteMe = 0;
                //immediately bring up defaults?
            }
            if (source == playButton) {
                parent.setPlaying(true);
                simulation.setPlaying(true);
            }
            if (source == pauseButton) {
                parent.setPlaying(false);
                simulation.setPlaying(false);
            }
            if (source == stepBackButton) {
                simulation.stepBackward();
            }
            if (source == stepForwardButton) {
                simulation.stepForward();
            }
            if (source == saveButton) {
                simulation.save();
            }
            if (source == loadButton) {
                simulation.load();
            }
        }
    }
}
