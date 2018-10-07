package gui;
import simulation.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainGUI extends JFrame
    {
    private TypedPanel currentPanel;
    private MainSimulation simulation;
    private Border generalBorder;
    private Entity currentEntity;

    public MainGUI(MainSimulation sim) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(960, 720));
        setResizable(false);
        changeWindow(PanelType.TITLE_PANEL);

        simulation = sim;
        generalBorder = BorderFactory.createLineBorder(Color.black,2);
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
    public MainSimulation getSimulation() {
        return simulation;
    }
    public Border getGeneralBorder(){
        return generalBorder;
        }

    public Entity getCurrentEntity(){
        return currentEntity;
    }
    public Entity setCurrentEntity(Entity e){
        currentEntity = e;
        ((SimPanel) currentPanel).getDetailPanel().updateText();
        return currentEntity;
    }

    public static void main(String[] args) {
        MainGUI gui = new MainGUI(new MainSimulation(32,32));
    }
    }


