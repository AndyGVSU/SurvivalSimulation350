package gui;

import simulation.Entity;

import javax.swing.*;
import java.awt.*;

public class EntityViewPanel extends TypedPanel{

    private JLabel entityImage;
    private JLabel entityInfo;

    EntityViewPanel(MainGUI par) {
        super(par);
    }

    void initGUI() {
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));

        Font infoFont = new Font("Times New Roman",Font.BOLD,18);

        entityImage = new JLabel("x");
        entityImage.setMinimumSize(new Dimension(50,50));
        entityImage.setBorder(parent.getGeneralBorder());
        entityImage.setFont(infoFont);

        entityInfo = new JLabel("NO ENTITY SELECTED");
        entityInfo.setBorder(parent.getGeneralBorder());
        entityInfo.setFont(infoFont);

        setBorder(parent.getGeneralBorder());

        add(Box.createRigidArea(new Dimension(20,1)));
        add(entityImage);
        add(Box.createRigidArea(new Dimension(20,1)));
        add(entityInfo);
    }

    public void updateText() {
        entityImage.setText(Character.toString(parent.getCurrentEntity().getSymbol()));
        entityInfo.setText(parent.getCurrentEntity().toString());
    }
}
