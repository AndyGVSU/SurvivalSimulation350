package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OptionPanel extends TypedPanel {

    OptionPanel(MainGUI par) {
        super(par);
    }

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
        JButton titleButton = new JButton("TITLE");
        JButton defaultsButton = new JButton("123");
        JButton playButton = new JButton("PLAY");
        JButton pauseButton = new JButton("PAUSE");
        JButton stepBackButton = new JButton("<<");
        JButton stepForwardButton = new JButton(">>");
        JButton saveButton = new JButton("SAVE");
        JButton loadButton = new JButton("LOAD");

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
            parent.changeWindow(PanelType.TITLE_PANEL);
        }
    }
}
