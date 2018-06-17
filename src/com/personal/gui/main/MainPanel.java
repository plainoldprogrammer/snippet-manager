package com.personal.gui.main;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class MainPanel extends JPanel
{
    public MainPanel()
    {
        super();

        JPanel categoriesOptionsPanel = new JPanel();
        categoriesOptionsPanel.setLayout(new BorderLayout());
        categoriesOptionsPanel.setBackground(Color.GRAY);
        JButton addCategorieButton = new JButton("+");
        JButton removeCategorieButton = new JButton("-");
        categoriesOptionsPanel.add(addCategorieButton, BorderLayout.NORTH);
        categoriesOptionsPanel.add(removeCategorieButton, BorderLayout.SOUTH);

        JPanel panelCategories = new JPanel();
        panelCategories.setLayout(new BorderLayout());
        String[] listLanguagesData = {"C++", "Java", "JavaScript"};
        JList<String> languagesList = new JList<String>(listLanguagesData);
        languagesList.setBackground(Color.GRAY);
        panelCategories.add(languagesList, BorderLayout.CENTER);
        panelCategories.add(categoriesOptionsPanel, BorderLayout.SOUTH);


        JPanel snippetsOptionPanel = new JPanel();
        snippetsOptionPanel.setLayout(new BorderLayout());
        snippetsOptionPanel.setBackground(Color.LIGHT_GRAY);
        JButton addSnippetButton = new JButton("s");
        JButton removeSnippetButton = new JButton("s");
        snippetsOptionPanel.add(addSnippetButton, BorderLayout.NORTH);
        snippetsOptionPanel.add(removeSnippetButton, BorderLayout.SOUTH);

        JPanel panelSnippets = new JPanel();
        panelSnippets.setLayout(new BorderLayout());
        String[] listSnippetsData = {"Hello World In Java", "Loop in C++", "Print JavaScript log"};
        JList<String> snippetsList = new JList<String>(listSnippetsData);
        snippetsList.setBackground(Color.LIGHT_GRAY);
        panelSnippets.add(snippetsList, BorderLayout.CENTER);
        panelSnippets.add(snippetsOptionPanel, BorderLayout.SOUTH);


        JPanel categoriesAndSnippetsPanel = new JPanel();
        categoriesAndSnippetsPanel.setLayout(new BorderLayout());
        categoriesAndSnippetsPanel.add(panelCategories, BorderLayout.WEST);
        categoriesAndSnippetsPanel.add(panelSnippets, BorderLayout.EAST);

        TextEditor textEditor = new TextEditor();
        setLayout(new BorderLayout());
        add(categoriesAndSnippetsPanel, BorderLayout.WEST);
        add(new JScrollPane(textEditor), BorderLayout.CENTER);
    }
}

