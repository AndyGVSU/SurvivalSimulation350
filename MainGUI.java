import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainGUI extends JFrame
    {
    private TypedPanel currentPanel;

    public MainGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(960, 720));
        changeWindow(PanelType.TITLE_PANEL);
    }
    public void changeWindow(PanelType newType){
        //remove previous panel (the panel is always the first component added)

        switch(newType) {
            case TITLE_PANEL: {
                currentPanel = new TitlePanel(this);
                break;
            }
            case SIMULATION_PANEL: {
                currentPanel = new SimPanel(this);
                break;
            }
        }

        //pack GUI frame
        setContentPane(currentPanel);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        MainGUI gui = new MainGUI();
    }
    }


