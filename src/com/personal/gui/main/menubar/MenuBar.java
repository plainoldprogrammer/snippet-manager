package com.personal.gui.main.menubar;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTextArea;
import com.personal.gui.main.menubar.items.MenuItemAbout;
import com.personal.gui.main.menubar.items.MenuItemPreferences;
import com.personal.gui.main.menubar.items.MenuItemQuit;
import com.personal.gui.main.menubar.items.MenuItemCut;
import com.personal.gui.main.menubar.items.MenuItemCopy;
import com.personal.gui.main.menubar.items.MenuItemPaste;
import com.personal.gui.main.menubar.items.MenuItemSelectAll;
import com.personal.gui.main.menubar.items.MenuItemHelp;

public class MenuBar extends JMenuBar
{
    private JTextArea textEditor = null;

    public MenuBar(JTextArea textEditor)
    {
        super();

        this.textEditor = textEditor;

        JMenu menuFile = new JMenu("File");
        JMenu menuEdit = new JMenu("Edit");
        JMenu menuHelp = new JMenu("Help");

        menuFile.add(new MenuItemAbout());
        menuFile.add(new MenuItemPreferences());
        menuFile.add(new MenuItemQuit());

        menuEdit.add(new MenuItemCut(textEditor));
        menuEdit.add(new MenuItemCopy(textEditor));
        menuEdit.add(new MenuItemPaste(textEditor));
        menuEdit.add(new MenuItemSelectAll(textEditor));

        menuHelp.add(new MenuItemHelp());

        add(menuFile);
        add(menuEdit);
        add(menuHelp);
    }
}

