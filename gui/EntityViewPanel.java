package gui;

import javax.swing.*;
import java.awt.*;

/**********************************************************************
 * Entity Viewing Panel for SurvivalSimulation350 GUI.
 * Allows the user to view a selected entity from the GridPanel.
 *
 * @author Anderson Hudson
 *********************************************************************/
public class EntityViewPanel extends TypedPanel {

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

    /** Defer to TypedPanel constructor.
     * @param par The controlling GUI object.
     */
    EntityViewPanel(final MainGUI par) {
        super(par);
    }

    /** Initialize GUI components. */
    void initGUI() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        entityImage = new JLabel("x");
        entityImage.setMinimumSize(entityImageSize);
        entityImage.setBorder(parent.getGeneralBorder());
        entityImage.setFont(viewPanelFont);

        entityInfo = new JLabel("NO ENTITY SELECTED");
        entityInfo.setBorder(parent.getGeneralBorder());
        entityInfo.setFont(viewPanelFont);

        setBorder(parent.getGeneralBorder());

        add(Box.createRigidArea(blankSpace));
        add(entityImage);
        add(Box.createRigidArea(blankSpace));
        add(entityInfo);
    }

    /** Update text based on current selected entity. */
    public void updateText() {
        entityImage.setText(
                Character.toString(parent.getCurrentEntity().getSymbol()));
        entityInfo.setText(parent.getCurrentEntity().toString());
    }
}
