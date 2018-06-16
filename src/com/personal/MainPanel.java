package com.personal;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class MainPanel extends JPanel
{
    TextEditor privateTextEditor = null;

    public MainPanel()
    {
        super();

        TextEditor textEditor = new TextEditor();
        setLayout(new BorderLayout());
        add(textEditor, BorderLayout.CENTER);
    }
}

