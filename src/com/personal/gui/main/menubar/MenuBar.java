package com.personal.gui.main.menubar;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
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
    public MenuBar()
    {
        super();

        JMenu menuFile = new JMenu("File");
        JMenu menuEdit = new JMenu("Edit");
        JMenu menuHelp = new JMenu("Help");

        menuFile.add(new MenuItemAbout());
        menuFile.add(new MenuItemPreferences());
        menuFile.add(new MenuItemQuit());

        menuEdit.add(new MenuItemCut());
        menuEdit.add(new MenuItemCopy());
        menuEdit.add(new MenuItemPaste());
        menuEdit.add(new MenuItemSelectAll());

        menuHelp.add(new MenuItemHelp());

        add(menuFile);
        add(menuEdit);
        add(menuHelp);
    }
}
