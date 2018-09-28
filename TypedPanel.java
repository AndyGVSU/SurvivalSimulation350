import javax.swing.*;
import java.awt.*;

public class TypedPanel extends JPanel {
    protected PanelType panelType;
    protected MainGUI parent;

    public TypedPanel(MainGUI par){
        parent = par;
    }

    public PanelType getPanelType(){
        return panelType;
    }
}
