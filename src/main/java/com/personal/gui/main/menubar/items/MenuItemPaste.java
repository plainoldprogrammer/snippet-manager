package com.personal.gui.main.menubar.items;

import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuItemPaste extends JMenuItem
{
    private JTextArea textEditor = null;

    public MenuItemPaste(JTextArea textEditor)
    {
        super();
        this.textEditor = textEditor;
        setText("Paste");

        addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textEditor.paste();
            }
        });
    }
}
