package gui;
import simulation.Entity;

import javax.swing.*;
import java.awt.*;

public class EntitySelectionPanel extends TypedPanel {

    private Entity displayEntity;

    EntitySelectionPanel(MainGUI par) {super(par); }

    void initGUI() {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBorder(parent.getGeneralBorder());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(2,4));

        for (int i = 0; i < 8; i++) {
            JButton toAdd = new JButton("P");
            toAdd.setSize(new Dimension(20,20));
            gridPanel.add(toAdd);
        }

        JLabel nameLabel = new JLabel("Entities");
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);

        add(nameLabel);
        add(Box.createRigidArea(new Dimension(1,30)));
        add(gridPanel);
        add(Box.createRigidArea(new Dimension(1,80)));
    }
}
