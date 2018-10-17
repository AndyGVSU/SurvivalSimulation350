package gui;

import javax.swing.JButton;

/**********************************************************************
 * Grid Panel Tile for SurvivalSimulation350 GUI.
 * Used in GridPanel to keep track of the selected entity.
 *
 * @author Anderson Hudson
 *********************************************************************/
public class GridPanelTile extends JButton {

    /** The row this button is contained in the grid. */
    private int row;
    /** The column this button is contained in the grid. */
    private int column;

    /** Basic constructor for GridPanelTile.
     * @param r The row of the tile.
     * @param c The column of the tile.
     * */
    public GridPanelTile(final int r, final int c) {
        row = r;
        column = c;
    }

    /** @return this tile's row. */
    public int getRow() {
        return row;
    }
    /** @return this tile's column. */
    public int getColumn() {
        return column;
    }
}
