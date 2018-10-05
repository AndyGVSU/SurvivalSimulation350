package gui;

import simulation.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GridPanel extends TypedPanel {
    private int rows;
    private int columns;
    private MainSimulation simulation;

    GridPanel(MainGUI par) {
        super(par);
    }

    public void initGUI() {
        simulation = parent.getSimulation();
        rows = simulation.getRows();
        columns = simulation.getColumns();

        setLayout(new GridLayout(rows,columns));
        setBackground(Color.WHITE);
        setBorder(parent.getGeneralBorder());

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                JLabel grid = new JLabel();
                grid.setBorder(BorderFactory.createLineBorder(Color.black,1));
                grid.setPreferredSize(new Dimension(2, 2));
                add(grid);
            }
    }
}
