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

        entityImage = new JLabel("x");
        entityInfo = new JLabel("NO ENTITY SELECTED");

        entityImage.setMinimumSize(new Dimension(50,50));
        entityImage.setBorder(parent.getGeneralBorder());

        entityInfo.setBorder(parent.getGeneralBorder());

        setBorder(parent.getGeneralBorder());

        add(entityImage);
        add(entityInfo);
    }

    public void updateText() {
        entityImage.setText(Character.toString(parent.getCurrentEntity().getSymbol()));
        entityInfo.setText(parent.getCurrentEntity().toString());
    }
}
