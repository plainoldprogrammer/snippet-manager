package com.personal.gui.main.menubar.items;

import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuItemCopy extends JMenuItem
{
    private JTextArea textEditor = null;

    public MenuItemCopy(JTextArea textEditor)
    {
        super();
        this.textEditor = textEditor;
        setText("Copy");

        addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textEditor.copy();
            }
        });
    }
}

