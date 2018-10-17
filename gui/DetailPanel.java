package gui;

import javax.swing.*;
import java.awt.*;

/**********************************************************************
 * Bottom panel for SurvivalSimulation350 GUI.
 * Holds slider panels, the entity viewing panel, and the
 * entity add panel.
 *
 * @author Anderson Hudson
 *********************************************************************/
public class DetailPanel extends TypedPanel {

    /** The entity viewing panel. */
    private EntityViewPanel viewPanel;
    /** Number of tick marks the sliders have. */
    private final int tickMarks = 5;
    /** Small spacing for layout. */
    private final int smallBoxSpace = 10;
    /** Large spacing for layout. */
    private final int largeBoxSpace = 30;

    /** Constructor defers to TypedPanel.
     * @param par The GUI controlling object
     * */
    DetailPanel(final MainGUI par) {
        super(par);
    }

    /** Initialize all GUI components. */
    public void initGUI() {
        viewPanel = new EntityViewPanel(parent);

        setLayout(new BorderLayout());
        setBackground(Color.pink);
        setBorder(parent.getGeneralBorder());

        JPanel sliderPanel = new JPanel();
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

        add(sliderPanel, BorderLayout.WEST);
        add(Box.createRigidArea(new Dimension(largeBoxSpace, 1)));
        add(viewPanel, BorderLayout.CENTER);
        add(new EntitySelectionPanel(parent), BorderLayout.EAST);
    }

    /** Update the text of the entity viewing panel. */
    public void updateText() {
        viewPanel.updateText();
    }
}
