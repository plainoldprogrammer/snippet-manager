package com.personal;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextArea;

public class MainPanel extends JPanel
{
    TextEditor privateTextEditor = null;

    public MainPanel()
    {
        super();

        setLayout(new BorderLayout());
        TextEditor textEditor = new TextEditor();
        add(textEditor, BorderLayout.CENTER);
    }
}

