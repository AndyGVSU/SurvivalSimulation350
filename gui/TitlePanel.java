package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
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
    /** Title font. **/
    private final Font titleFont =
            new Font("Times New Roman", Font.ITALIC, 64);
    /** Start button. **/
    private final Font startFont =
            new Font("System", Font.PLAIN, 64);
    /** Medium vertical spacing for layout. */
    private final Dimension medSpace = new Dimension(1, 50);
    /** Large vertical spacing for layout. */
    private final Dimension largeSpace = new Dimension(1, 100);
    /** Start button size. **/
    private final Dimension startButtonSize = new Dimension(300, 200);

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
        setBackground(Color.BLACK);

        titleLabel = new JLabel("<html> <pre class=\"tab\">     PLANT SURVIVAL"
                + "<br>     SIMULATION 350</pre></html>");
        startButton = new JButton();

        titleLabel.setFont(titleFont);

        titleLabel.setForeground(Color.GREEN);
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        startButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        startButton.addActionListener(new StartClick());
        startButton.setText("START");
        startButton.setFont(startFont);
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.GREEN);
        startButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));

        add(Box.createRigidArea(medSpace));
        add(titleLabel);
        add(Box.createRigidArea(largeSpace));
        add(startButton);
    }

    /** Handles button clicks. */
    private class StartClick implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            parent.changeWindow(PanelType.SIMULATION_PANEL);
        }
    }
}
