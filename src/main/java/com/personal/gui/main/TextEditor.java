package com.personal.gui.main;

import javax.swing.JTextArea;
import java.awt.Font;

public class TextEditor extends JTextArea
{
    public TextEditor()
    {
        super();

        setFont(new Font("monospaced", Font.PLAIN, 12));
        setTabSize(4);
    }
}

