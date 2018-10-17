package gui;

import simulation.Environment;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;

import java.awt.Dimension;

import java.util.Hashtable;

/**********************************************************************
 * Slider Panel for SurvivalSimulation350 GUI.
 * Provides a slider and displayed value for a
 * simulation environment variable.
 *
 * @author Anderson Hudson
 *********************************************************************/
public class SliderPanel extends JPanel implements TypedPanel {
    /** The controlling GUI component. */
    private MainGUI parent;
    /** The environment variable the slider controls. */
    private SliderType type;
    /** The range of the environment variable. */
    private int[] statRange;
    /** The number of tick labels displayed on the slider. */
    private int tickCount;
    /** The tick unit spacing for all sliders. */
    private final int tickSpacing = 5;
    /** The name displayed for the environment variable. */
    private String labelName;
    /** The labels for the environment variable tick marks. */
    private String sliderUnit;
    /** The label for the environment variable name. */
    private JLabel identifierLabel;
    /** The label for the environment variable value. */
    private JLabel valueLabel;
    /** The default size of the slider component. */
    private final Dimension sliderDefaultSize = new Dimension(200, 50);
    /** The default size of the slider panel. */
    private final Dimension panelDefaultSize = new Dimension(350, 1);
    /** Blank space for layout. */
    private final Dimension blankSpace = new Dimension(10, 1);

    /** Constructor.
     * @param par The controlling GUI object.
     * @param t The name of the environment variable.
     * @param range The range of the environment variable.
     * @param name The name displayed for the environment variable.
     * @param tickMarks The number of ticks displayed on the slider.
     * @param tickUnit The unit displayed on tick mark values.
     */
    SliderPanel(final MainGUI par, final SliderType t, final int[] range,
                final String name, final int tickMarks, final String tickUnit) {
        parent = par;
        type = t;
        statRange = range;
        labelName = name;
        tickCount = tickMarks;
        sliderUnit = tickUnit;
        initGUI();
    }

    /** Initialize GUI components. */
    public void initGUI() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(panelDefaultSize);

        int minRange = statRange[0];
        int maxRange = statRange[1];
        JSlider slider = new JSlider(JSlider.HORIZONTAL,
                minRange, maxRange, 0);
        slider.setLabelTable(makeSliderLabels(
                minRange, maxRange, tickCount, sliderUnit));
        slider.setMajorTickSpacing(tickSpacing);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new SliderListener());
        slider.setMaximumSize(sliderDefaultSize);


        identifierLabel = new JLabel(labelName);
        valueLabel = new JLabel("0");
        valueLabel.setText(slider.getValue() + sliderUnit);

        /*
        //DEBUG -- VIEW BORDERS
        slider.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                slider.getBorder()));
        identifierLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                identifierLabel.getBorder()));
        valueLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                valueLabel.getBorder()));
        */

        add(slider);
        add(Box.createRigidArea(blankSpace));
        add(identifierLabel);
        add(Box.createRigidArea(blankSpace));
        add(valueLabel);
    }

    /** Sets up slider labels manually.
     * @param lowBound The minimum value for the slider.
     * @param highBound The maximum value for the slider.
     * @param tickMarks The number of tick marks for the slider.
     * @param extra The "unit" for the slider tick mark values.
     * @return the Hashtable to use for the slider labels.
     * */
    private Hashtable<Integer, JLabel> makeSliderLabels(
            final int lowBound, final int highBound,
            final int tickMarks, final String extra) {
        int ticks = tickMarks;
        int increment = (highBound - lowBound) / tickMarks;
        int labelNumber;

        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        for (int i = 0; i <= ticks; i++) {
            labelNumber = lowBound + (i * increment);
            labels.put(labelNumber, new JLabel(
                    Integer.toString(labelNumber) + extra));
        }

        return labels;
    }

    /** Listens to slider movement and sets
     * environment values in simulation accordingly. */
    private class SliderListener implements ChangeListener {
        @Override
        public void stateChanged(final ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            if (source.getValueIsAdjusting()) {
                int value;
                Environment env = parent.getSimulation().getEnvironment();
                switch (type) {
                    case TEMPERATURE:
                        value = env.setTemperature(source.getValue());
                        valueLabel.setText(Integer.toString(value)
                                + sliderUnit);
                        break;
                    case WEATHER:
                        value = env.setWeatherFreq(source.getValue());
                        valueLabel.setText(Integer.toString(value)
                                + sliderUnit);
                        break;
                    case SUNLIGHT:
                        value = env.setSunlight(source.getValue());
                        valueLabel.setText(Integer.toString(value)
                                + sliderUnit);
                        break;
                    case SPEED:
                        value = parent.getSimulation().
                                setSpeed(source.getValue());
                        valueLabel.setText(Integer.toString(value)
                                + sliderUnit);
                        break;
                    default:
                        throw new IllegalArgumentException("INVALID TYPE");
                }
            }
        }
    }
}
