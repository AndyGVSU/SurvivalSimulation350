package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DetailPanel extends TypedPanel {

    DetailPanel(MainGUI par) {
        super(par);
    }

    public void initGUI(){
        setLayout(new BorderLayout());
        setBackground(Color.pink);
        setBorder(parent.getGeneralBorder());

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel,BoxLayout.Y_AXIS));

        Dimension blankSpace = new Dimension(1, 10);

        sliderPanel.add(Box.createRigidArea(blankSpace));
        sliderPanel.add(new SliderPanel(parent,SliderType.SPEED,
                parent.getSimulation().getSpeedRange(),"Speed:",5,"ds"));
        sliderPanel.add(Box.createRigidArea(blankSpace));
        sliderPanel.add(new SliderPanel(parent,SliderType.TEMPERATURE,
                parent.getSimulation().getEnvironment().getTemperatureRange(),"Temperature:",4,"°F"));
        sliderPanel.add(Box.createRigidArea(blankSpace));
        sliderPanel.add(new SliderPanel(parent,SliderType.SUNLIGHT,
                parent.getSimulation().getEnvironment().getSunlightRange(),"Sunlight:",5,"%"));
        sliderPanel.add(Box.createRigidArea(blankSpace));
        sliderPanel.add(new SliderPanel(parent,SliderType.WEATHER,
                parent.getSimulation().getEnvironment().getWeatherRange(),"Weather Freq:",5,"%"));

        add(sliderPanel,BorderLayout.WEST);
    }

}
