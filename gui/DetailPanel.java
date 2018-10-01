package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class DetailPanel extends JPanel {
    MainGUI parent;

    public DetailPanel(MainGUI par){
        parent = par;

        //detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.X_AXIS));
        setBackground(Color.pink);
        Border blackline = BorderFactory.createLineBorder(Color.black,2);
        setBorder(parent.getGeneralBorder());
    }
}
