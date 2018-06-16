package com.personal;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame
{
    public MainWindow()
    {
        super();

        setSize(800, 600);
        setTitle("Snippet Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new MainPanel();
        getContentPane().add(mainPanel);
    }

}

