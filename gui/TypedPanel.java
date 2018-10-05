package gui;

import javax.swing.*;

abstract class TypedPanel extends JPanel {
    MainGUI parent;

    TypedPanel(MainGUI par){
        parent = par;
        initGUI();
    }

    abstract void initGUI();
}
