package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Frame extends JFrame {

    private JPanel currentPanel;

    public Frame(String title) {
        super(title);
        this.setLayout(new BorderLayout());
        currentPanel = null;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //switching the displayed panel
    public void switchPanel(JPanel newPanel) {
        if (currentPanel != null) {
            getContentPane().remove(currentPanel);
        }
        currentPanel = newPanel;
        getContentPane().add(currentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void setIcon(String path){
        if (!path.endsWith(".png")){
            return;
        }

        if (!new File(path).isFile()){
            return;
        }

        ImageIcon img = new ImageIcon(path);
        this.setIconImage(img.getImage());
    }
}
