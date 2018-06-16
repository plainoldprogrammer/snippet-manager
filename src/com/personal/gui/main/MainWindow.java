package com.personal.gui.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import com.personal.gui.main.menubar.MenuBar;

public class MainWindow extends JFrame
{
    private JMenuBar menuBar = null;

    public MainWindow()
    {
        super();

        setSize(800, 600);
        setTitle("Snippet Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        menuBar = new MenuBar();
        setJMenuBar(menuBar);

        JPanel mainPanel = new MainPanel();
        getContentPane().add(mainPanel);
    }
}

