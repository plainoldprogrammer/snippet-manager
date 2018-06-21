package com.personal.gui.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import com.personal.gui.main.menubar.MenuBar;
import com.personal.gui.main.MainPanel;

public class MainWindow extends JFrame
{
    public MainWindow()
    {
        super();

        setSize(600, 400);
        setTitle("Snippet Manager 0.2 (Alfa)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new MainPanel();

        JMenuBar menuBar = new MenuBar(((MainPanel) mainPanel).getTextEditor());
        setJMenuBar(menuBar);

        getContentPane().add(mainPanel);
    }
}

