package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimPanel extends TypedPanel {
    private JPanel topPanel;
    private JPanel detailPanel;
    private JPanel gridPanel;
    private JPanel optionPanel;

    SimPanel(MainGUI par) {
        super(par);
    }

    public void initGUI(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        int topHeight = 480;
        int bottomHeight = 240;

        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setPreferredSize(new Dimension(960,topHeight));

        optionPanel = new OptionPanel(parent);
        optionPanel.setPreferredSize(new Dimension(320,topHeight));

        gridPanel = new GridPanel(parent);
        gridPanel.setPreferredSize(new Dimension(640,topHeight));

        detailPanel = new DetailPanel(parent);
        detailPanel.setPreferredSize(new Dimension(960,bottomHeight));

        add(topPanel);

        topPanel.add(optionPanel);
        topPanel.add(gridPanel);
        add(detailPanel);
    }
}
