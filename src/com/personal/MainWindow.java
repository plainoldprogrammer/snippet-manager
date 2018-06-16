package com.personal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.BorderLayout;

public class MainWindow extends JFrame
{
    public MainWindow()
    {
        super();

        setSize(800, 600);
        setTitle("Snippet Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        JMenu menuHelp = new JMenu("Help");

        JMenuItem menuItemQuit = new JMenuItem("Quit");
        JMenuItem menuItemAbout = new JMenuItem("About");

        menuFile.add(menuItemQuit);
        menuHelp.add(menuItemAbout);
        
        menuBar.add(menuFile);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);

        JPanel mainPanel = new MainPanel();
        getContentPane().add(mainPanel);
    }

}

