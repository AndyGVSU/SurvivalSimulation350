package gui;

import simulation.MainSimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OptionPanel extends TypedPanel {

    OptionPanel(MainGUI par) {
        super(par);
    }
    private JButton titleButton;
    private JButton defaultsButton;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stepBackButton;
    private JButton stepForwardButton;
    private JButton saveButton;
    private JButton loadButton;


    public void initGUI(){
        Color panelColor = Color.GRAY;

        //inner button panel
        JPanel innerPanel = new JPanel();
        GridLayout layout = new GridLayout(4,2);
        layout.setHgap(50);
        layout.setVgap(50);
        innerPanel.setLayout(layout);

        //main option panel
        setBorder(parent.getGeneralBorder());
        setBackground(panelColor);
        innerPanel.setBackground(panelColor);
        setLayout(new BorderLayout());

        //add buttons
        titleButton = new JButton("TITLE");
        defaultsButton = new JButton("123");
        playButton = new JButton("PLAY");
        pauseButton = new JButton("PAUSE");
        stepBackButton = new JButton("<<");
        stepForwardButton = new JButton(">>");
        saveButton = new JButton("SAVE");
        loadButton = new JButton("LOAD");

        add(Box.createRigidArea(new Dimension(1, 30)),BorderLayout.NORTH);
        add(Box.createRigidArea(new Dimension(1, 30)),BorderLayout.SOUTH);
        add(Box.createRigidArea(new Dimension(30, 1)),BorderLayout.WEST);
        add(Box.createRigidArea(new Dimension(30, 1)),BorderLayout.EAST);
        add(innerPanel,BorderLayout.CENTER);

        innerPanel.add(titleButton);
        innerPanel.add(defaultsButton);
        innerPanel.add(playButton);
        innerPanel.add(pauseButton);
        innerPanel.add(stepBackButton);
        innerPanel.add(stepForwardButton);
        innerPanel.add(saveButton);
        innerPanel.add(loadButton);

        titleButton.addActionListener(new titleClick());
    }
    private class titleClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            MainSimulation simulation = parent.getSimulation();

            if (source == titleButton)
                parent.changeWindow(PanelType.TITLE_PANEL);
            if (source == defaultsButton) {
                //immediately bring up defaults?
            }
            if (source == playButton) {
                parent.setPlaying(true);
                simulation.setPlaying(true);
            }
            if (source == pauseButton) {
                parent.setPlaying(false);
                simulation.setPlaying(false);
            }
            if (source == stepBackButton) {
                simulation.stepBackward();
            }
            if (source == stepForwardButton){
                simulation.stepForward();
            }
            if (source == saveButton) {
                simulation.save();
            }
            if (source == loadButton) {
                simulation.load();
            }
        }
    }
}