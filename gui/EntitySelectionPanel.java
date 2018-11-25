package gui;

import simulation.Air;
import simulation.Dirt;
import simulation.Grass;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**********************************************************************
 * Entity Selection Panel for SurvivalSimulation350 GUI.
 * Allows the user to select an entity to add.
 *
 * @author Anderson Hudson
 *********************************************************************/
public class EntitySelectionPanel extends JPanel implements TypedPanel {
    /** The controlling GUI component. */
    private MainGUI parent;
    /** Number of rows on selection grid. (to limit size) */
    private final int rows = 2;
    /** Number of columns on selection grid. */
    private final int columns = 3;
    /** Size of selection buttons. */
    private final Dimension smallSpace = new Dimension(1, 30);
    /** Large layout space. */
    private final Dimension largeSpace = new Dimension(1, 80);
    /** Toggle add button */
    private JButton toggleAddButton;
    /** Grass add button */
    private JButton grassButton;
    /** Dirt add button */
    private JButton dirtButton;
    /** Air add button */
    private JButton airButton;

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

        JLabel nameLabel = new JLabel("Add Entities:");
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);

        toggleAddButton = new JButton();
        toggleAddButton.setAlignmentX(CENTER_ALIGNMENT);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, columns));

        Grass grassReference = new Grass(null,null,-1,-1,-1);
        Dirt dirtReference = new Dirt(null,null,-1,-1,-1);
        Air airReference = new Air(null,null,-1,-1,-1);

        grassButton = new JButton(String.valueOf(grassReference.getSymbol()));
        dirtButton = new JButton(String.valueOf(dirtReference.getSymbol()));
        airButton = new JButton(String.valueOf(airReference.getSymbol()));

        gridPanel.add(grassButton);
        gridPanel.add(dirtButton);
        gridPanel.add(airButton);

        add(nameLabel);
        add(Box.createRigidArea(smallSpace));
        add(gridPanel);
        add(Box.createRigidArea(largeSpace));
        add(toggleAddButton);

        updateToggleButton(false);
        resetAddButtons(null);

        AddListener listener = new AddListener();
        for (Component c : gridPanel.getComponents())
            if (c instanceof JButton)
                ((JButton) c).addActionListener(listener);
        toggleAddButton.addActionListener(listener);
    }

    public void lockGUI(boolean lock) {
        dirtButton.setEnabled(!lock);
        airButton.setEnabled(!lock);
        grassButton.setEnabled(!lock);
        toggleAddButton.setEnabled(!lock);
        updateToggleButton(false);
        resetAddButtons(null);
        parent.setClickEntity(null);
    }

    private void updateToggleButton(boolean adds) {
        JButton source = toggleAddButton;
        parent.setClickAdds(adds);
        if (parent.getClickAdds()) {
            source.setText("  ADDING ENTITIES");
            source.setBackground(Color.GREEN);
        }
        else {
            source.setText("VIEWING ENTITIES");
            source.setBackground(Color.LIGHT_GRAY);
        }
    }

    private void resetAddButtons(JButton source) {
        //ignore toggle button
        if (source == toggleAddButton)
            return;

        Color unselected = Color.LIGHT_GRAY;
        grassButton.setBackground(unselected);
        dirtButton.setBackground(unselected);
        airButton.setBackground(unselected);

        if (source != null)
            source.setBackground(Color.GREEN);
    }

    private class AddListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            JButton source = (JButton) e.getSource();

            resetAddButtons(source);
            if (source == grassButton) {
                parent.setClickEntity(new Grass(null,null,0,0,0));
            }
            if (source == dirtButton) {
                parent.setClickEntity(new Dirt(null,null,0,0,0));
            }
            if (source == airButton) {
                parent.setClickEntity(new Air(null,null,0,0,0));
            }
            if (source == toggleAddButton) {
                updateToggleButton(!parent.getClickAdds());
            }
        }
    }
}
