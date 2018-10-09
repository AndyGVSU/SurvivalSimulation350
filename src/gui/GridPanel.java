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

        GridListener buttonListen = new GridListener();

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                GridPanelTile grid = new GridPanelTile(i,j);
                grid.setBorder(BorderFactory.createLineBorder(Color.black,1));
                grid.setPreferredSize(new Dimension(2, 2));
                grid.addActionListener(buttonListen);
                grid.setText(Character.toString(parent.getSimulation().getEntity(i,j).getSymbol()));
                grid.setFont(new Font("Times New Roman",Font.PLAIN,9));
                add(grid);
            }
    }
    private class GridListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GridPanelTile button = (GridPanelTile) e.getSource();

            //set the currently selected entity to the tile you just pressed
            parent.setCurrentEntity(parent.getSimulation().getEntity(button.getRow(), button.getColumn()));
        }
    }
}
