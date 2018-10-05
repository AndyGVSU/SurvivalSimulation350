package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Hashtable;

public class SliderPanel extends TypedPanel {
    SliderType type;
    private int[] statRange;
    private int tickCount;
    private String labelName, sliderUnit;
    private JLabel identifierLabel, valueLabel;

    SliderPanel(MainGUI par, SliderType t, int[] range, String name, int tickMarks, String tickUnit) {
        parent = par;
        type = t;
        statRange = range;
        labelName = name;
        tickCount = tickMarks;
        sliderUnit = tickUnit;
        initGUI();
    }

    void initGUI() {
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(350,1));

        int minRange = statRange[0];
        int maxRange = statRange[1];
        JSlider slider = new JSlider(SwingConstants.HORIZONTAL,minRange,maxRange,0);
        slider.setLabelTable(makeSliderLabels(minRange,maxRange, tickCount,sliderUnit));
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new SliderListener());

        identifierLabel = new JLabel(labelName);
        valueLabel = new JLabel("0");

        add(slider);
        add(Box.createRigidArea(new Dimension(10,1)));
        add(identifierLabel);
        add(Box.createRigidArea(new Dimension(10,1)));
        add(valueLabel);
        add(Box.createRigidArea(new Dimension(20,1)));
    }
    private Hashtable<Integer, JLabel> makeSliderLabels(int lowBound, int highBound, int tickMarks, String extra){
        int ticks = tickMarks;
        int increment = (highBound - lowBound) / tickMarks;
        int labelNumber;

        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        for (int i = 0; i <= ticks; i++) {
            labelNumber = lowBound + (i * increment);
            labels.put(labelNumber,new JLabel(Integer.toString(labelNumber) + extra));
        }

        return labels;
    }
    private class SliderListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            if (source.getValueIsAdjusting()){
                switch(type) {
                    case TEMPERATURE: {
                        int value = parent.getSimulation().getEnvironment().setTemperature(source.getValue());
                        valueLabel.setText(Integer.toString(value) + sliderUnit);
                        break;
                    }
                    case WEATHER: {
                        int value = parent.getSimulation().getEnvironment().setWeatherFreq(source.getValue());
                        valueLabel.setText(Integer.toString(value) + sliderUnit);
                        break;
                    }
                    case SUNLIGHT: {
                        int value = parent.getSimulation().getEnvironment().setSunlight(source.getValue());
                        valueLabel.setText(Integer.toString(value) + sliderUnit);
                        break;
                    }
                    case SPEED: {
                        int value = parent.getSimulation().setSpeed(source.getValue());
                        valueLabel.setText(Integer.toString(value) + sliderUnit);
                        break;
                    }
                }
            }

        }
    }
}
