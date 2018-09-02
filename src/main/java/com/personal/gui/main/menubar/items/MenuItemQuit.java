package com.personal.gui.main.menubar.items;

import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuItemQuit extends JMenuItem
{
    public MenuItemQuit()
    {
        super();

        setText("Quit");

        addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
    }
}
