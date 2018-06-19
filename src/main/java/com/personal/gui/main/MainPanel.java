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
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.personal.util.Category;
import com.personal.util.Snippet;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

public class MainPanel extends JPanel
{
    final Logger logger = LogManager.getLogger(MainPanel.class);
    private JTextArea textEditor = null;
    private JTextField titleOfSelectedSnippet;
    private List<Category> listCategoriesData;
    private JList<Category> listOfCategories;
    private List<Snippet> listSnippetsData;
    private JList<Snippet> listOfSnippets;

    public MainPanel()
    {
        super();


        // Panel of the categories.

        JLabel categoriesLabel = new JLabel("All Categories");
        categoriesLabel.setFont(new Font("monospaced", Font.BOLD, 12));

        JPanel categoriesOptionsPanel = new JPanel();
        categoriesOptionsPanel.setLayout(new BorderLayout());
        categoriesOptionsPanel.setBackground(new Color(216, 216, 216));
        JButton addCategoryButton = new JButton("+");
        JButton removeCategoryButton = new JButton("-");
        categoriesOptionsPanel.add(addCategoryButton, BorderLayout.NORTH);
        categoriesOptionsPanel.add(removeCategoryButton, BorderLayout.SOUTH);

        JPanel panelCategories = new JPanel();
        panelCategories.setLayout(new BorderLayout());
        panelCategories.setBackground(new Color(216, 216, 216));
        listCategoriesData = new ArrayList<>();
        Category category1 = new Category("C");
        Category category2 = new Category("Java");
        Category category3 = new Category("html");
        listCategoriesData.add(category1);
        listCategoriesData.add(category2);
        listCategoriesData.add(category3);
        listOfCategories = new JList(listCategoriesData.toArray());
        listOfCategories.setBackground(new Color(216, 216, 216));
        listOfCategories.setSelectedIndex(0);
        panelCategories.add(categoriesLabel, BorderLayout.NORTH);
        panelCategories.add(new JScrollPane(listOfCategories), BorderLayout.CENTER);
        panelCategories.add(categoriesOptionsPanel, BorderLayout.SOUTH);


        // Panel of the snippets.

        JTextField snippetFilter = new JTextField("Filter");
        titleOfSelectedSnippet = new JTextField("Title Of Selected Snippet");

        JPanel snippetsOptionPanel = new JPanel();
        snippetsOptionPanel.setLayout(new BorderLayout());
        snippetsOptionPanel.setBackground(new Color(236, 236, 236));
        JButton addSnippetButton = new JButton("+");
        JButton removeSnippetButton = new JButton("-");
        addSnippetButton.setEnabled(false);
        removeSnippetButton.setEnabled(false);
        snippetsOptionPanel.add(addSnippetButton, BorderLayout.NORTH);
        snippetsOptionPanel.add(removeSnippetButton, BorderLayout.SOUTH);

        JPanel panelSnippets = new JPanel();
        panelSnippets.setLayout(new BorderLayout());
        Snippet snippet1 = new Snippet("Hello world", "#include <std.io>");
        Snippet snippet2 = new Snippet("Get time", "system(time)");
        Snippet snippet3 = new Snippet("How to define a macro", "private const MY_VALUE");
        Snippet snippet4 = new Snippet("Hello world java", "public static void main(args[])");
        Snippet snippet5 = new Snippet("Get current time", "getCurrentMillisTime()");
        Snippet snippet6 = new Snippet("Set utf", "setUTFCharset('utf')");
        Snippet snippet7 = new Snippet("Create a sub-div", "<div><div></div></div>");
        Snippet snippet8 = new Snippet("Generate a form", "<form><form>");
        Snippet snippet9 = new Snippet("Implement text input", "<input name=''>/");
        category1.addSnippet(snippet1);
        category1.addSnippet(snippet2);
        category1.addSnippet(snippet3);
        category2.addSnippet(snippet4);
        category2.addSnippet(snippet5);
        category3.addSnippet(snippet6);
        category3.addSnippet(snippet7);
        category3.addSnippet(snippet8);
        category3.addSnippet(snippet9);
        listSnippetsData = new ArrayList<>();
        listSnippetsData.add(snippet1);
        listSnippetsData.add(snippet2);
        listSnippetsData.add(snippet3);
        listOfSnippets = new JList(listSnippetsData.toArray());
        listOfSnippets.setBackground(new Color(236, 236, 236));

        if (listOfCategories.getSelectedIndex() >= 0)
        {
            if (listOfCategories.getSelectedValue().getListOfSnippets().size() >= 0)
            {
                listOfSnippets.setSelectedIndex(0);
                titleOfSelectedSnippet.setText(listOfSnippets.getSelectedValue().getTitle());
                removeSnippetButton.setEnabled(true);
                addSnippetButton.setEnabled(true);
            }
        }
        else
        {
            removeSnippetButton.setEnabled(false);
        }

        panelSnippets.add(snippetFilter, BorderLayout.NORTH);
        panelSnippets.add(new JScrollPane(listOfSnippets), BorderLayout.CENTER);
        panelSnippets.add(snippetsOptionPanel, BorderLayout.SOUTH);


        // Panel that contains categories and snippets panel.

        JPanel categoriesAndSnippetsPanel = new JPanel();
        categoriesAndSnippetsPanel.setLayout(new BorderLayout());
        categoriesAndSnippetsPanel.add(panelCategories, BorderLayout.WEST);
        categoriesAndSnippetsPanel.add(panelSnippets, BorderLayout.EAST);


        // Panel that contains the title and code of the selected snippet.

        setLayout(new BorderLayout());
        textEditor = new TextEditor();
        JPanel titleAndEditorPanel = new JPanel();
        titleAndEditorPanel.setLayout(new BorderLayout());
        titleAndEditorPanel.add(new JScrollPane(textEditor), BorderLayout.CENTER);
        titleAndEditorPanel.add(titleOfSelectedSnippet, BorderLayout.NORTH);


        // Adding the two main panels to the main window.

        add(categoriesAndSnippetsPanel, BorderLayout.WEST);
        add(titleAndEditorPanel, BorderLayout.CENTER);


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
                        listCategoriesData.add(new Category(newCategorieNameTextField.getText()));

                        logger.info("List after add a category:");
                        DefaultListModel model = new DefaultListModel();
                        ListIterator<Category> iterator = listCategoriesData.listIterator();

                        while (iterator.hasNext())
                        {
                            Category currentCategory = iterator.next();
                            model.addElement(currentCategory);
                            logger.info(" " + currentCategory);
                        }

                        listOfCategories.setModel(model);
                        createNewCategorieWindow.setVisible(false);
                        removeCategoryButton.setEnabled(true);

                        // the new category isn't selected in the gui and theres no one selected
                        addSnippetButton.setEnabled(false);
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
                int selectedCategory = listOfCategories.getSelectedIndex();
                listCategoriesData.remove(selectedCategory);

                logger.info("List after remove a category:");
                DefaultListModel model = new DefaultListModel();
                ListIterator<Category> iterator = listCategoriesData.listIterator();

                while (iterator.hasNext())
                {
                    Category currentCategory = iterator.next();
                    model.addElement(currentCategory);
                    logger.info(" " + currentCategory);
                }

                listOfCategories.setModel(model);
                addSnippetButton.setEnabled(false);

                if (listCategoriesData.size() == 0)
                {
                    removeCategoryButton.setEnabled(false);
                    DefaultListModel emptyModel = new DefaultListModel();
                    listOfSnippets.setModel(emptyModel);
                }
                else
                {
                    listOfCategories.setSelectedIndex(0);
                }
            }
        });

        listOfCategories.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting())
                {
                    Category selectedCategory = listOfCategories.getSelectedValue();

                    // 'selectedCategory' could be null after removing the selected category.
                    if (selectedCategory != null)
                    {
                        logger.info("selected category: " + selectedCategory);

                        DefaultListModel<Snippet> model = new DefaultListModel();
                        ListIterator<Snippet> iterator = selectedCategory.getListOfSnippets().listIterator();

                        while (iterator.hasNext())
                        {
                            Snippet currentSnippet = iterator.next();
                            model.addElement(currentSnippet);
                            logger.info("snippet in the category '" + selectedCategory + "': " + currentSnippet);
                        }

                        listOfSnippets.setModel(model);
                        addSnippetButton.setEnabled(true);

                        if (selectedCategory.getListOfSnippets().size() > 0)
                        {
                            listOfSnippets.setSelectedIndex(0);
                        }
                    }
                }
            }
        });

        listOfSnippets.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting())
                {
                    if (listOfSnippets.getModel().getSize() > 0)
                    {
                        removeSnippetButton.setEnabled(true);

                        if (!listOfSnippets.isSelectionEmpty())
                        {
                            titleOfSelectedSnippet.setText(listOfSnippets.getSelectedValue().getTitle());
                            textEditor.setText(listOfSnippets.getSelectedValue().getCode());
                        }
                    }
                    else
                    {
                        titleOfSelectedSnippet.setText("");
                        textEditor.setText("");
                        removeSnippetButton.setEnabled(false);
                    }

                }
            }
        });

        addSnippetButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Category selectedCategory = listOfCategories.getSelectedValue();
                Snippet newSnippet = new Snippet("New Snippet", "");
                selectedCategory.addSnippet(newSnippet);

                DefaultListModel<Snippet> model = new DefaultListModel();
                ListIterator<Snippet> iterator = selectedCategory.getListOfSnippets().listIterator();

                while (iterator.hasNext())
                {
                    Snippet currentSnippet = iterator.next();
                    model.addElement(currentSnippet);
                }

                listOfSnippets.setModel(model);
                textEditor.setText("");
                listOfSnippets.setSelectedIndex(listOfSnippets.getModel().getSize() - 1);
                titleOfSelectedSnippet.grabFocus();
            }
        });

        removeSnippetButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Category selectedCategory = listOfCategories.getSelectedValue();
                int index = listOfSnippets.getSelectedIndex();
                selectedCategory.getListOfSnippets().remove(index);

                DefaultListModel<Snippet> model = new DefaultListModel();
                ListIterator<Snippet> iterator = selectedCategory.getListOfSnippets().listIterator();

                while (iterator.hasNext())
                {
                    Snippet currentSnippet = iterator.next();
                    model.addElement(currentSnippet);
                }

                listOfSnippets.setModel(model);

                if (listOfSnippets.getModel().getSize() > 0)
                {
                    listOfSnippets.setSelectedIndex(0);
                }
            }
        });

        titleOfSelectedSnippet.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void changedUpdate(DocumentEvent e)
            {
            }

            @Override
            public void insertUpdate(DocumentEvent e)
            {
                logger.info("snippet title changed: " + titleOfSelectedSnippet.getText());
                listOfSnippets.getSelectedValue().setTitle(titleOfSelectedSnippet.getText());
                listOfSnippets.updateUI();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                insertUpdate(e);
            }
        });
    }

    public JTextArea getTextEditor()
    {
        return this.textEditor;
    }
}

