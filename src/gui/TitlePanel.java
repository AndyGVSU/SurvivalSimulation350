package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TitlePanel extends TypedPanel {
    private JLabel titleLabel;
    private JButton testButton;
    private JButton testButton2;
    private JButton testButton3;

    TitlePanel(MainGUI par) {
        super(par);
    }

    public void initGUI() {
        //sets title GUI layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //title label
        titleLabel = new JLabel("GUI TEST: SIMULATION");

        //title buttons
        testButton = new JButton("START");
        testButton2 = new JButton("IMPORT SIMULATION");
        testButton3 = new JButton("MANAGE DEFAULTS");

        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        testButton.setAlignmentX(CENTER_ALIGNMENT);
        testButton2.setAlignmentX(CENTER_ALIGNMENT);
        testButton3.setAlignmentX(CENTER_ALIGNMENT);

        testButton.addActionListener(new startClick());

        add(Box.createRigidArea(new Dimension(1, 50)));
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(1, 100)));
        add(testButton);
        add(Box.createRigidArea(new Dimension(1, 10)));
        add(testButton2);
        add(Box.createRigidArea(new Dimension(1, 10)));
        add(testButton3);
    }

    //occurs when start is pressed
    private class startClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            parent.changeWindow(PanelType.SIMULATION_PANEL);
        }
    }

}
