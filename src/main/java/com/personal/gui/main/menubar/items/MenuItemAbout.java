package com.personal.gui.main.menubar.items;

import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class MenuItemAbout extends JMenuItem
{
    public MenuItemAbout()
    {
        super();

        setText("About");

        addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "Snippet Manager 0.3 (Beta)");
            }
        });
    }
}
