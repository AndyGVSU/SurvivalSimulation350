package gui;

//import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Component;

/**********************************************************************
 * Bottom panel for SurvivalSimulation350 GUI.
 * Holds slider panels, the entity viewing panel, and the
 * entity add panel.
 *
 * @author Anderson Hudson
 *********************************************************************/
public final class DetailPanel extends JPanel implements TypedPanel {
    /** The controlling GUI component. */
    private MainGUI parent;
    /** The entity viewing panel. */
    private EntityViewPanel viewPanel;
    /** Number of tick marks the sliders have. */
    private final int tickMarks = 5;
    /** Small spacing for layout. */
    private final int smallBoxSpace = 10;
    /** Large spacing for layout. */
    private final int largeBoxSpace = 30;
    /** The slider panel. **/
    private JPanel sliderPanel;
    /** The step panel. **/
    private JLabel stepLabel;
    /** The entity adding panel. **/
    private EntitySelectionPanel entityAddPanel;

    /** Constructor.
     * @param par The GUI controlling object
     * */
    DetailPanel(final MainGUI par) {
        parent = par;
        initGUI();
    }

    /** Initialize all GUI components. */
    public void initGUI() {
        viewPanel = new EntityViewPanel(parent);

        setLayout(new BorderLayout());
        setBackground(Color.pink);
        setBorder(parent.getGeneralBorder());

        sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));

        Dimension blankSpace = new Dimension(1, smallBoxSpace);

        sliderPanel.add(Box.createRigidArea(blankSpace));
        sliderPanel.add(new SliderPanel(parent, SliderType.SPEED,
                parent.getSimulation().getSpeedRange(),
                "Speed:", tickMarks, "ds"));
        sliderPanel.add(Box.createRigidArea(blankSpace));
        sliderPanel.add(new SliderPanel(parent, SliderType.TEMPERATURE,
                parent.getSimulation().getEnvironment().getTemperatureRange(),
                "Temperature:", tickMarks, "°F"));
        sliderPanel.add(Box.createRigidArea(blankSpace));
        sliderPanel.add(new SliderPanel(parent, SliderType.SUNLIGHT,
                parent.getSimulation().getEnvironment().getSunlightRange(),
                "Sunlight:", tickMarks, "%"));
        sliderPanel.add(Box.createRigidArea(blankSpace));
        sliderPanel.add(new SliderPanel(parent, SliderType.WEATHER,
                parent.getSimulation().getEnvironment().getWeatherRange(),
                "Weather Freq:", tickMarks, "%"));

        Component[] comp = sliderPanel.getComponents();

        for (Component c : comp) {
            if (c instanceof SliderPanel) {
                ((SliderPanel) c).setAlignmentX(Box.LEFT_ALIGNMENT);
            }
        }

        stepLabel = new JLabel();
        stepLabel.setText("Step 0 / 0");

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        entityAddPanel = new EntitySelectionPanel(parent);

        add(sliderPanel, BorderLayout.WEST);
        add(Box.createRigidArea(new Dimension(largeBoxSpace, 1)));
        add(centerPanel);
        centerPanel.add(stepLabel);
        centerPanel.add(viewPanel);
        add(entityAddPanel, BorderLayout.EAST);
    }

    /** Update the text of the entity viewing panel and step panel. */
    public void updateText() {
        viewPanel.updateText();
        stepLabel.setText("Step: " + parent.getSimulation().getCurrentStep()
                + " / " + parent.getSimulation().getTotalSteps());
    }
    /** Calls manualUpdate for the slider panel. */
    public void updateSliders() {
        for (Component p : sliderPanel.getComponents()) {
            if (p instanceof SliderPanel) {
                ((SliderPanel) p).manualUpdate();
            }
        }
    }
    /** @param lock Whether to disable the slider panel's components. */
    public void lockGUI(final boolean lock) {
        for (Component c : sliderPanel.getComponents()) {
            if (c instanceof SliderPanel) {
                ((SliderPanel) c).lockGUI(lock);
            }
        }
        lockEntityAdd(lock);
    }
    /** @param lock Whether to lock the entity add panel's components. */
    public void lockEntityAdd(final boolean lock) {
        entityAddPanel.lockGUI(lock);
    }
}
