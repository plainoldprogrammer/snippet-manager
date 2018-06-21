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
import com.personal.db.JdbcSqliteConnection;

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


        // Prepare data from DB.
        JdbcSqliteConnection dbConnection = new JdbcSqliteConnection();
        listCategoriesData = dbConnection.getCategoriesData();
        logger.info("listCategories on DB: " + listCategoriesData);

        JPanel panelCategories = new JPanel();
        panelCategories.setLayout(new BorderLayout());
        panelCategories.setBackground(new Color(216, 216, 216));
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
        listSnippetsData = new ArrayList<>();

        Category firstCategory = listCategoriesData.get(0);
        List<Snippet> snippetsInFirstCategory = firstCategory.getListOfSnippets();

        ListIterator<Snippet> iterator = snippetsInFirstCategory.listIterator();

        while (iterator.hasNext())
        {
            Snippet currentSnippet = iterator.next();
            listSnippetsData.add(currentSnippet);
        }

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

                        if (selectedCategory.getListOfSnippets().size() > 0 )
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
                            // prevent an infinite loop events between the 'titleOfSelectedSnippet' and 'listOfSnippets'
                            if (!titleOfSelectedSnippet.getText().equals(listOfSnippets.getSelectedValue().getTitle()))
                            {
                                titleOfSelectedSnippet.setText(listOfSnippets.getSelectedValue().getTitle());
                            }

                            textEditor.setText(listOfSnippets.getSelectedValue().getCode());
                            logger.info("id of selected snippet: " + listOfSnippets.getSelectedValue().getId());
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
                logger.info("id of selected snippet: " + newSnippet.getId());


                try
                {
                    int idOfNewSnippet = -1;
                    JdbcSqliteConnection jdbcSqliteConnection = new JdbcSqliteConnection();
                    idOfNewSnippet = jdbcSqliteConnection.getLastId() + 1;
                    logger.info("id of inserted snippet: " + idOfNewSnippet);
                    newSnippet.setId(idOfNewSnippet);

                    jdbcSqliteConnection.insertNewSnippetToDB(selectedCategory, newSnippet);
                }
                catch(Exception sqlException)
                {
                    sqlException.printStackTrace();
                }

            }
        });

        removeSnippetButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Category selectedCategory = listOfCategories.getSelectedValue();
                int indexOfRemovedSnippet = listOfSnippets.getSelectedIndex();
                selectedCategory.getListOfSnippets().remove(indexOfRemovedSnippet);

                DefaultListModel<Snippet> model = new DefaultListModel();
                ListIterator<Snippet> iterator = selectedCategory.getListOfSnippets().listIterator();

                while (iterator.hasNext())
                {
                    Snippet currentSnippet = iterator.next();
                    model.addElement(currentSnippet);
                }

                logger.info("model: " + model);

                listOfSnippets.setModel(model);

                if ((listOfSnippets.getModel().getSize() > 0) && !(model.isEmpty()))
                {
                    if (((indexOfRemovedSnippet - 1) == -1) && (listOfSnippets.getModel().getSize() > 0))
                    {
                        listOfSnippets.setSelectedIndex(0);
                    }
                    else
                    {
                        listOfSnippets.setSelectedIndex(indexOfRemovedSnippet - 1);
                    }
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

                if (listOfSnippets.getModel().getSize() > 0)
                {
                    logger.info("inside");
                    int indexSelectedSnippet = listOfSnippets.getSelectedIndex();
                    listOfSnippets.getSelectedValue().setTitle(titleOfSelectedSnippet.getText());

                    Snippet selectedSnippet = listOfSnippets.getSelectedValue();
                    selectedSnippet.setTitle(titleOfSelectedSnippet.getText());
                    logger.info("new title for snippet: " + selectedSnippet.getTitle());

                    Category selectedCategory = listOfCategories.getSelectedValue();

                    DefaultListModel<Snippet> model = new DefaultListModel();
                    ListIterator<Snippet> iterator = selectedCategory.getListOfSnippets().listIterator();

                    while (iterator.hasNext())
                    {
                        Snippet currentSnippet = iterator.next();
                        model.addElement(currentSnippet);
                        logger.info("snippet: " + currentSnippet.getTitle());
                    }

                    logger.info("model: " + model);
                    listOfSnippets.setModel(model);
                    listOfSnippets.setSelectedIndex(indexSelectedSnippet);
                }
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

