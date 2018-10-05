package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class DetailPanel extends TypedPanel {

    DetailPanel(MainGUI par) {
        super(par);
    }

    public void initGUI(){
        setLayout(new BorderLayout());
        setBackground(Color.pink);
        Border blackline = BorderFactory.createLineBorder(Color.black,2);
        setBorder(parent.getGeneralBorder());

        JPanel sliderPanel = new JPanel();

        int[] tempRange = parent.getSimulation().getTemperatureRange();
        int[] speedRange = parent.getSimulation().getSpeedRange();
        int[] weatherRange = parent.getSimulation().getWeatherRange();
        int[] sunRange = parent.getSimulation().getSunlightRange();

        int sliderOrientation = SwingConstants.HORIZONTAL;

        JSlider sliderSpeed = new JSlider(sliderOrientation,speedRange[0],speedRange[1],0);
        JSlider sliderTemp = new JSlider(sliderOrientation,tempRange[0],tempRange[1],0);
        JSlider sliderWeather = new JSlider(sliderOrientation,weatherRange[0],weatherRange[1],0);
        JSlider sliderSun = new JSlider(sliderOrientation,sunRange[0],sunRange[1],0);

        sliderSpeed.setLabelTable(makeSliderLabels(speedRange[0],speedRange[1], 5," ds"));
        initSlider(sliderSpeed);
        sliderTemp.setLabelTable(makeSliderLabels(tempRange[0],tempRange[1], 4,"°F"));
        initSlider(sliderTemp);
        sliderWeather.setLabelTable(makeSliderLabels(weatherRange[0],weatherRange[1], 5,"%"));
        initSlider(sliderWeather);
        sliderSun.setLabelTable(makeSliderLabels(sunRange[0],sunRange[1], 5,"%"));
        initSlider(sliderSun);

        Dimension blankSpace = new Dimension(1, 10);

        sliderPanel.setLayout(new BoxLayout(sliderPanel,BoxLayout.Y_AXIS));
        sliderPanel.add(Box.createRigidArea(blankSpace));
        sliderPanel.add(sliderSpeed);
        sliderPanel.add(Box.createRigidArea(blankSpace));
        sliderPanel.add(sliderTemp);
        sliderPanel.add(Box.createRigidArea(blankSpace));
        sliderPanel.add(sliderSun);
        sliderPanel.add(Box.createRigidArea(blankSpace));
        sliderPanel.add(sliderWeather);

        add(sliderPanel,BorderLayout.WEST);
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
    private void initSlider(JSlider slider){
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
    }

}
