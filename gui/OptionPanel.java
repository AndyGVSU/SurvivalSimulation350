package gui;

import simulation.MainSimulation;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JFileChooser;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.IOException;

/**********************************************************************
 * Option Panel for SurvivalSimulation350 GUI.
 * Allows the user to perform multiple administrative tasks for the
 * simulation using a grid of buttons.
 *
 * @author Anderson Hudson
 *********************************************************************/
public final class OptionPanel extends JPanel implements TypedPanel {
    /** The controlling GUI component. */
    private MainGUI parent;
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
    /** Button that loads a simulation from a file. */
    private JButton loadButton;
    /** Button that resets the current simulation. */
    private JButton resetButton;
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

    /** Constructor.
     * @param par The controlling GUI object.
     */
    OptionPanel(final MainGUI par) {
        parent = par;
        initGUI();
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
        loadButton = new JButton("LOAD");
        resetButton = new JButton("RESET");

        add(Box.createRigidArea(verticalSpace), BorderLayout.NORTH);
        add(Box.createRigidArea(verticalSpace), BorderLayout.SOUTH);
        add(Box.createRigidArea(horizontalSpace), BorderLayout.WEST);
        add(Box.createRigidArea(horizontalSpace), BorderLayout.EAST);
        add(innerPanel, BorderLayout.CENTER);

        playButton.setBackground(Color.LIGHT_GRAY);
        pauseButton.setBackground(Color.RED);

        innerPanel.add(titleButton);
        innerPanel.add(defaultsButton);
        innerPanel.add(playButton);
        innerPanel.add(pauseButton);
        innerPanel.add(stepBackButton);
        innerPanel.add(stepForwardButton);
        innerPanel.add(loadButton);
        innerPanel.add(resetButton);

        ButtonClick listener = new ButtonClick();
        for (Component c : innerPanel.getComponents()) {
            if (c instanceof JButton) {
                ((JButton) c).addActionListener(listener);
            }
        }
    }

    /** @param lock Whether to disable buttons. */
    public void lockGUI(final boolean lock) {
        titleButton.setEnabled(!lock);
        defaultsButton.setEnabled(!lock);
        playButton.setEnabled(!lock);
        pauseButton.setEnabled(lock);
        stepBackButton.setEnabled(!lock);
        stepForwardButton.setEnabled(!lock);
        loadButton.setEnabled(!lock);
        resetButton.setEnabled(!lock);
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
                if (!parent.getPlaying()) {
                    parent.setPlaying(true);
                    playButton.setBackground(Color.green);
                    pauseButton.setBackground(Color.LIGHT_GRAY);
                }
            }
            if (source == pauseButton) {
                if (parent.getPlaying()) {
                    parent.setPlaying(false);
                    playButton.setBackground(Color.LIGHT_GRAY);
                    pauseButton.setBackground(Color.RED);
                }
            }
            if (source == stepBackButton) {
                simulation.stepBackward();
                parent.updateDisplay();
                parent.updateCurrentEntity();
                parent.lockEntityAdd();
            }
            if (source == stepForwardButton) {
                simulation.stepForward();
                parent.updateDisplay();
                parent.updateCurrentEntity();
                parent.lockEntityAdd();
            }
            if (source == loadButton) {
                //Create a file chooser
                final JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setDialogTitle("Choose a Simulation Directory:");
                //In response to a button click:
                int returnVal = fc.showOpenDialog(parent);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        parent.getSimulation().getRecord().setDirectory(
                                fc.getSelectedFile().toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    parent.getSimulation().reset(false);
                    parent.updateDisplay();
                }
            }
            if (source == resetButton) {
                parent.getSimulation().reset(true);
                parent.updateDisplay();
                parent.lockEntityAdd();
            }
        }
    }
}
