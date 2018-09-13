package com.personal.gui.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import com.personal.gui.main.menubar.MenuBar;

public class MainWindow extends JFrame
{
    public MainWindow()
    {
        super();

        setSize(600, 400);
        setTitle("Snippet Manager 0.4 (Beta)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new MainPanel();

        JMenuBar menuBar = new MenuBar(((MainPanel) mainPanel).getTextEditor());
        setJMenuBar(menuBar);

        getContentPane().add(mainPanel);
    }
}

