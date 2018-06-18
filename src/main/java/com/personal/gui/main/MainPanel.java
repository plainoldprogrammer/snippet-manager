package com.personal.gui.main;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class MainPanel extends JPanel
{
    final Logger logger = LogManager.getLogger(MainPanel.class);
    private JTextArea textEditor = null;
    private List<String> listLanguagesData;
    private JList<String> languagesList;

    public MainPanel()
    {
        super();

        JPanel categoriesOptionsPanel = new JPanel();
        categoriesOptionsPanel.setLayout(new BorderLayout());
        categoriesOptionsPanel.setBackground(new Color(216, 216, 216));
        JButton addCategoryButton = new JButton("+");
        JButton removeCategoryButton = new JButton("-");
        categoriesOptionsPanel.add(addCategoryButton, BorderLayout.NORTH);
        categoriesOptionsPanel.add(removeCategoryButton, BorderLayout.SOUTH);

        JPanel panelCategories = new JPanel();
        panelCategories.setLayout(new BorderLayout());
        listLanguagesData = new ArrayList();
        listLanguagesData.add("C");
        listLanguagesData.add("C++");
        listLanguagesData.add("C#");
        languagesList = new JList(listLanguagesData.toArray());
        languagesList.setBackground(new Color(216, 216, 216));
        panelCategories.add(languagesList, BorderLayout.CENTER);
        panelCategories.add(categoriesOptionsPanel, BorderLayout.SOUTH);


        JPanel snippetsOptionPanel = new JPanel();
        snippetsOptionPanel.setLayout(new BorderLayout());
        snippetsOptionPanel.setBackground(new Color(236, 236, 236));
        JButton addSnippetButton = new JButton("+");
        JButton removeSnippetButton = new JButton("-");
        snippetsOptionPanel.add(addSnippetButton, BorderLayout.NORTH);
        snippetsOptionPanel.add(removeSnippetButton, BorderLayout.SOUTH);

        JPanel panelSnippets = new JPanel();
        panelSnippets.setLayout(new BorderLayout());
        String[] listSnippetsData = {"Hello World In Java", "Loop in C++", "Print JavaScript log"};
        JList<String> snippetsList = new JList<String>(listSnippetsData);
        snippetsList.setBackground(new Color(236, 236, 236));
        panelSnippets.add(snippetsList, BorderLayout.CENTER);
        panelSnippets.add(snippetsOptionPanel, BorderLayout.SOUTH);


        JPanel categoriesAndSnippetsPanel = new JPanel();
        categoriesAndSnippetsPanel.setLayout(new BorderLayout());
        categoriesAndSnippetsPanel.add(panelCategories, BorderLayout.WEST);
        categoriesAndSnippetsPanel.add(panelSnippets, BorderLayout.EAST);

        textEditor = new TextEditor();
        setLayout(new BorderLayout());
        add(categoriesAndSnippetsPanel, BorderLayout.WEST);
        add(new JScrollPane(textEditor), BorderLayout.CENTER);

        addCategoryButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFrame createNewCategorieWindow = new JFrame();
                createNewCategorieWindow.setLayout(new BorderLayout());

                JTextField newCategorieNameTextField = new JTextField();
                createNewCategorieWindow.add(newCategorieNameTextField, BorderLayout.CENTER);

                JButton confirmNewCategorieButton = new JButton("Accept");
                confirmNewCategorieButton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        listLanguagesData.add(newCategorieNameTextField.getText());

                        logger.info("List after add a category:");
                        DefaultListModel model = new DefaultListModel();
                        ListIterator<String> iterator = listLanguagesData.listIterator();

                        while (iterator.hasNext())
                        {
                            String currentCategory = iterator.next();
                            model.addElement(currentCategory);
                            logger.info(" " + currentCategory);
                        }

                        languagesList.setModel(model);
                        createNewCategorieWindow.setVisible(false);
                    }
                });

                createNewCategorieWindow.add(confirmNewCategorieButton, BorderLayout.SOUTH);
                createNewCategorieWindow.pack();
                createNewCategorieWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                createNewCategorieWindow.setLocationRelativeTo(null);
                createNewCategorieWindow.setVisible(true);
            }
        });

        removeCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int selectedCategory = languagesList.getSelectedIndex();
                listLanguagesData.remove(selectedCategory);

                logger.info("List after remove a category:");
                DefaultListModel model = new DefaultListModel();
                ListIterator<String> iterator = listLanguagesData.listIterator();

                while (iterator.hasNext())
                {
                    String currentCategory = iterator.next();
                    model.addElement(currentCategory);
                    logger.info(" " + currentCategory);
                }

                languagesList.setModel(model);
            }
        });
    }

    public JTextArea getTextEditor()
    {
        return this.textEditor;
    }
}

