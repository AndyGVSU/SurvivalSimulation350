package gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.Dimension;
import java.awt.GridLayout;

/**********************************************************************
 * Entity Selection Panel for SurvivalSimulation350 GUI.
 * Allows the user to select an entity from a grid to add.
 *
 * @author Anderson Hudson
 *********************************************************************/
public class EntitySelectionPanel extends JPanel implements TypedPanel {
    /** The controlling GUI component. */
    private MainGUI parent;
    /** Number of rows on selection grid. */
    private final int rows = 2;
    /** Number of columns on selection grid. */
    private final int columns = 4;
    /** Size of selection buttons. */
    private final Dimension buttonSize = new Dimension(20, 20);
    /** Small layout space. */
    private final Dimension smallSpace = new Dimension(1, 30);
    /** Large layout space. */
    private final Dimension largeSpace = new Dimension(1, 80);

    /** Constructor.
     * @param par The controlling GUI object.
     */
    EntitySelectionPanel(final MainGUI par) {
        parent = par;
        initGUI();
    }

    /** Initialize GUI components. */
    public void initGUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(parent.getGeneralBorder());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, columns));

        for (int i = 0; i < rows * columns; i++) {
            JButton toAdd = new JButton("P");
            toAdd.setSize(buttonSize);
            gridPanel.add(toAdd);
        }

        JLabel nameLabel = new JLabel("Entities");
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);

        add(nameLabel);
        add(Box.createRigidArea(smallSpace));
        add(gridPanel);
        add(Box.createRigidArea(largeSpace));
    }
}
