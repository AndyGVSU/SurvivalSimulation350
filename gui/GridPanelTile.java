package gui;

import javax.swing.*;

public class GridPanelTile extends JButton {

    private int row;
    private int column;

    public GridPanelTile(int r, int c){
        row = r;
        column = c;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
}
