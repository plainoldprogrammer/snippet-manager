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
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String about =  "Snippet Manager\n" +
                                "Version: 0.5 (Beta)\n" +
                                "Release Date: 2020 April\n" +
                                "Developer: PlainOldProgrammer\n";

                JOptionPane.showMessageDialog(null, about, "About Snippet Manager", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
