import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class SimPanel extends TypedPanel {
    private JPanel topPanel;
    private JPanel detailPanel;
    private JPanel gridPanel;
    private JPanel optionPanel;

    public SimPanel(MainGUI par){
        super(par);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        int gridSize = 32;

        int topHeight = 480;
        int bottomHeight = 240;

        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setPreferredSize(new Dimension(960,topHeight));

        optionPanel = new JPanel();
        optionPanel.setPreferredSize(new Dimension(320,topHeight));
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        optionPanel.setBackground(Color.GRAY);

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(gridSize,gridSize));
        gridPanel.setPreferredSize(new Dimension(640,topHeight));
        gridPanel.setBackground(Color.green);
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++) {
                JLabel grid = new JLabel(".");
                grid.setPreferredSize(new Dimension(2,2));
                gridPanel.add(grid);
            }

        detailPanel = new JPanel();
        //detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.X_AXIS));
        detailPanel.setPreferredSize(new Dimension(960,bottomHeight));
        detailPanel.setBackground(Color.pink);

        add(topPanel);

        JButton titleButton = new JButton("TITLE");
        titleButton.addActionListener(new titleClick());
        optionPanel.add(Box.createRigidArea(new Dimension(1, 10)));
        optionPanel.add(titleButton);
        optionPanel.add(Box.createRigidArea(new Dimension(1, 10)));
        optionPanel.add(new JButton("PLAY"));
        optionPanel.add(Box.createRigidArea(new Dimension(1, 10)));
        optionPanel.add(new JButton("STEP FORWARD"));
        optionPanel.add(Box.createRigidArea(new Dimension(1, 10)));
        optionPanel.add(new JButton("SAVE"));

        topPanel.add(optionPanel);
        topPanel.add(gridPanel);
        add(detailPanel);
    }
    private class titleClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            parent.changeWindow(PanelType.TITLE_PANEL);
        }
    }
}
