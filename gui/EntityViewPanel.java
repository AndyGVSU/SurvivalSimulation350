package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.Dimension;
import java.awt.Font;


/**********************************************************************
 * Entity Viewing Panel for SurvivalSimulation350 GUI.
 * Allows the user to view a selected entity from the GridPanel.
 *
 * @author Anderson Hudson
 *********************************************************************/
public class EntityViewPanel extends JPanel implements TypedPanel {
    /** The controlling GUI component. */
    private MainGUI parent;
    /** Image showing what the entity looks like. (currently text) */
    private JLabel entityImage;
    /** Size of entityImage component. */
    private final Dimension entityImageSize = new Dimension(50, 50);
    /** Font used by all components. */
    private final Font viewPanelFont =
            new Font("Times New Roman", Font.BOLD, 18);
    /** Label showing entity information. */
    private JLabel entityInfo;
    /** Blank space for layout. */
    private final Dimension blankSpace = new Dimension(20, 1);

    /** Constructor.
     * @param par The controlling GUI object.
     */
    EntityViewPanel(final MainGUI par) {
        parent = par;
        initGUI();
    }

    /** Initialize GUI components. */
    public void initGUI() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        entityImage = new JLabel("x");
        entityImage.setMinimumSize(entityImageSize);
        entityImage.setBorder(parent.getGeneralBorder());
        entityImage.setFont(viewPanelFont);

        entityInfo = new JLabel("NO ENTITY SELECTED");
        entityInfo.setBorder(parent.getGeneralBorder());
        entityInfo.setFont(viewPanelFont);

        add(Box.createRigidArea(blankSpace));
        add(entityImage);
        add(Box.createRigidArea(blankSpace));
        add(entityInfo);
    }

    /** Update text based on current selected entity. */
    public void updateText() {
        entityImage.setText(
                Character.toString(parent.getCurrentEntity().getSymbol()));
        //this is needed to display new line characters correctly in JLabel
        String newText = "<html>"
                + parent.getCurrentEntity().toString() + "</html>";
        newText = newText.replaceAll("\n", "<br/>");
        entityInfo.setText(newText);
    }
}
