package gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**********************************************************************
 * Title Panel for SurvivalSimulation350 GUI.
 * Provides a familiar and aesthetic feel to the program,
 * as well as file management for handling default simulations.
 *
 * @author Anderson Hudson
 *********************************************************************/
public class TitlePanel extends JPanel implements TypedPanel {
    /** The controlling GUI component. */
    private MainGUI parent;
    /** The title text. */
    private JLabel titleLabel;
    /** The start button. */
    private JButton startButton;
    /** The import (?) button. */
    private JButton testButton2;
    /** The file management (?) button. */
    private JButton testButton3;
    /** Title font size. */
    private final int titleFontSize = 32;
    /** Small vertical spacing for layout. */
    private final Dimension smallSpace = new Dimension(1, 10);
    /** Medium vertical spacing for layout. */
    private final Dimension medSpace = new Dimension(1, 50);
    /** Large vertical spacing for layout. */
    private final Dimension largeSpace = new Dimension(1, 100);

    /** Constructor.
     * @param par The GUI controlling object
     * */
    TitlePanel(final MainGUI par) {
        parent = par;
        initGUI();
    }

    /** Initialize GUI components. */
    public void initGUI() {
        //sets title GUI layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //title label
        titleLabel = new JLabel("GUI TEST: SIMULATION");

        //title buttons
        startButton = new JButton("START");
        testButton2 = new JButton("IMPORT SIMULATION");
        testButton3 = new JButton("MANAGE DEFAULTS");

        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(
                new Font("Times New Roman", Font.PLAIN, titleFontSize));
        startButton.setAlignmentX(CENTER_ALIGNMENT);
        testButton2.setAlignmentX(CENTER_ALIGNMENT);
        testButton3.setAlignmentX(CENTER_ALIGNMENT);

        startButton.addActionListener(new StartClick());

        add(Box.createRigidArea(medSpace));
        add(titleLabel);
        add(Box.createRigidArea(largeSpace));
        add(startButton);
        add(Box.createRigidArea(smallSpace));
        add(testButton2);
        add(Box.createRigidArea(smallSpace));
        add(testButton3);
    }

    /** Handles button clicks. */
    private class StartClick implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            parent.changeWindow(PanelType.SIMULATION_PANEL);
        }
    }
}
