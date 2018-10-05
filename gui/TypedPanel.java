package gui;

import javax.swing.*;

abstract class TypedPanel extends JPanel {
    MainGUI parent;

    TypedPanel(){
        //do nothing
    }
    TypedPanel(MainGUI par){
        parent = par;
        initGUI();
    }

    abstract void initGUI();
}
