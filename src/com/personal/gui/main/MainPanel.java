package com.personal.gui.main;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import com.personal.gui.main.TextEditor;

public class MainPanel extends JPanel
{
    public MainPanel()
    {
        super();

        TextEditor textEditor = new TextEditor();
        setLayout(new BorderLayout());
        add(textEditor, BorderLayout.CENTER);
    }
}

