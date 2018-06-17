package com.personal.gui.main.menubar.items;

import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuItemSelectAll extends JMenuItem
{
    private JTextArea textEditor = null;

    public MenuItemSelectAll(JTextArea textEditor)
    {
        super();

        this.textEditor = textEditor;

        setText("Select All");

        addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                textEditor.selectAll();
                textEditor.grabFocus();
            }
        });
    }

    public void setEditor(JTextArea textEditor)
    {
        this.textEditor = textEditor;
    }
}

