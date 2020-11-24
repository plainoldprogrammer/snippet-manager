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
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.Dimension;
import com.personal.db.JdbcSqliteConnection;
import com.personal.util.Category;
import com.personal.util.Snippet;

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

        JLabel categoriesLabel = new JLabel("Category");
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
        listCategoriesData = dbConnection.getCategories();
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

        JTextField snippetFilter = new JTextField("Snippet");
        snippetFilter.setBackground(new Color(216, 216, 216));
        titleOfSelectedSnippet = new JTextField("Selected Snippet");

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

        textEditor = new TextEditor();

        if (listCategoriesData.size() > 0)
        {
            Category firstCategory = listCategoriesData.get(0);
            List<Snippet> snippetsInFirstCategory = firstCategory.getListOfSnippets();

            /*
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
                    textEditor.setText(listOfSnippets.getSelectedValue().getCode());
                    removeSnippetButton.setEnabled(true);
                    addSnippetButton.setEnabled(true);
                }
            }
            else
            {
                removeSnippetButton.setEnabled(false);
            }

             */
        }
        else
        {
            listOfSnippets = new JList();
        }

        panelSnippets.add(snippetFilter, BorderLayout.NORTH);
        panelSnippets.add(new JScrollPane(listOfSnippets), BorderLayout.CENTER);
        panelSnippets.add(snippetsOptionPanel, BorderLayout.SOUTH);


        // Panel that contains categories and snippets panel.

        panelCategories.setPreferredSize(new Dimension(150, panelCategories.getHeight()));
        panelSnippets.setPreferredSize(new Dimension(150, panelCategories.getHeight()));
        JPanel categoriesAndSnippetsPanel = new JPanel();
        categoriesAndSnippetsPanel.setLayout(new BorderLayout());
        categoriesAndSnippetsPanel.add(panelCategories, BorderLayout.WEST);
        categoriesAndSnippetsPanel.add(panelSnippets, BorderLayout.EAST);


        // Panel that contains the title and code of the selected snippet.

        JPanel titleAndEditorPanel = new JPanel();
        titleAndEditorPanel.setLayout(new BorderLayout());
        titleAndEditorPanel.add(new JScrollPane(textEditor), BorderLayout.CENTER);
        titleAndEditorPanel.add(titleOfSelectedSnippet, BorderLayout.NORTH);


        // Adding the two main panels to the main window.

        categoriesAndSnippetsPanel.setPreferredSize(new Dimension(300, categoriesAndSnippetsPanel.getHeight()));
        setLayout(new BorderLayout());
        add(categoriesAndSnippetsPanel, BorderLayout.WEST);
        add(titleAndEditorPanel, BorderLayout.CENTER);


        addCategoryButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFrame createNewCategoryWindow = new JFrame();
                createNewCategoryWindow.setLayout(new BorderLayout());

                JTextField newCategoryNameTextField = new JTextField();
                createNewCategoryWindow.add(newCategoryNameTextField, BorderLayout.CENTER);


                // Create a jbutton mockup in order to expose the "fireActionPerformed" method to use when hit <Enter>.

                class ConfirmNewCategoryButton extends JButton
                {
                    public ConfirmNewCategoryButton(String buttonTitle)
                    {
                        super(buttonTitle);
                    }

                    @Override
                    public void fireActionPerformed(ActionEvent e)
                    {
                        super.fireActionPerformed(e);
                    }
                }

                ConfirmNewCategoryButton confirmNewCategoryButton = new ConfirmNewCategoryButton("Accept");

                newCategoryNameTextField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        logger.info("Enter is pressed!");
                        confirmNewCategoryButton.fireActionPerformed(e);
                    }
                });


                confirmNewCategoryButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        Category theNewCategory = new Category(newCategoryNameTextField.getText());
                        listCategoriesData.add(theNewCategory);
                        int lastCategoryInserted = listCategoriesData.size() - 1;

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
                        logger.info("index at last category inserted: " + lastCategoryInserted);
                        listOfCategories.setSelectedIndex(lastCategoryInserted);
                        createNewCategoryWindow.setVisible(false);
                        removeCategoryButton.setEnabled(true);

                        // the new category is selected in the gui and theres no one selected
                        addSnippetButton.setEnabled(true);

                        dbConnection.createCategory(newCategoryNameTextField.getText());
                        List<Category> categories = dbConnection.getCategories();
                    }
                });

                createNewCategoryWindow.add(confirmNewCategoryButton, BorderLayout.SOUTH);
                createNewCategoryWindow.pack();
                createNewCategoryWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                createNewCategoryWindow.setLocationRelativeTo(null);
                createNewCategoryWindow.setVisible(true);
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

                        logger.info("model: " + listOfSnippets.getModel());

                        if (selectedCategory.getListOfSnippets().size() > 0)
                        {
                            listOfSnippets.setModel(model);
                        }
                        else if (model.isEmpty())
                        {
                            model.removeAllElements();
                            listOfSnippets.setModel(model);
                        }

                        addSnippetButton.setEnabled(true);

                        if (selectedCategory.getListOfSnippets().size() > 0 )
                        {
                            listOfSnippets.setSelectedIndex(0);
                        }
                    }
                }
            }
        });

        if (listOfSnippets != null)
        {
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
        }

        addSnippetButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                logger.info("adding a new snippet");
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
                listOfSnippets.setSelectedIndex(listOfSnippets.getModel().getSize() - 1);
                textEditor.setText("");
                titleOfSelectedSnippet.grabFocus();
                logger.info("id of selected snippet: " + newSnippet.getId());

                try
                {
                    JdbcSqliteConnection jdbcSqliteConnection = new JdbcSqliteConnection();
                    int idOfNewSnippet = jdbcSqliteConnection.getLastId() + 1;
                    logger.info("idOfNewSnippet: " + idOfNewSnippet);
                    newSnippet.setId(idOfNewSnippet);
                    jdbcSqliteConnection.insertNewSnippetToDB(selectedCategory, newSnippet);

                    logger.info("id of inserted snippet: " + idOfNewSnippet);
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
                int idOfRemovedSnippet = ((Snippet) (selectedCategory.getListOfSnippets().get(indexOfRemovedSnippet))).getId();
                logger.info("id of removed snippet: " + idOfRemovedSnippet);
                selectedCategory.getListOfSnippets().remove(indexOfRemovedSnippet);

                DefaultListModel<Snippet> model = new DefaultListModel();
                ListIterator<Snippet> iterator = selectedCategory.getListOfSnippets().listIterator();

                while (iterator.hasNext())
                {
                    Snippet currentSnippet = iterator.next();
                    model.addElement(currentSnippet);
                }

                logger.info("model: " + model);

                if (!(model.isEmpty()))
                {
                    listOfSnippets.setModel(model);
                }
                else
                {
                    titleOfSelectedSnippet.setText("");
                    textEditor.setText("");
                    model.removeAllElements();
                    listOfSnippets.setModel(model);
                }

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

                try
                {
                    JdbcSqliteConnection sqliteConnection = new JdbcSqliteConnection();
                    sqliteConnection.deleteSnippet(idOfRemovedSnippet);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
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


                    // refresh the snippet list each time the title is modified

                    DefaultListModel<Snippet> model = new DefaultListModel();
                    ListIterator<Snippet> iterator = selectedCategory.getListOfSnippets().listIterator();

                    while (iterator.hasNext())
                    {
                        Snippet currentSnippet = iterator.next();
                        model.addElement(currentSnippet);
                        logger.info("snippet: " + currentSnippet.getTitle());
                    }

                    logger.info("model: " + model);

                    if (!(model.isEmpty()))
                    {
                        listOfSnippets.setModel(model);
                        listOfSnippets.setSelectedIndex(indexSelectedSnippet);
                    }


                    // update the title in the DB of the selected snippet

                    try
                    {
                        JdbcSqliteConnection jdbcSqliteConnection = new JdbcSqliteConnection();
                        logger.info("id of the snippet to update their title: " + listOfSnippets.getSelectedValue().getId());
                        jdbcSqliteConnection.updateTitleOfSnippet(listOfSnippets.getSelectedValue().getId(), listOfSnippets.getSelectedValue().getTitle());
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                insertUpdate(e);
            }
        });

        textEditor.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void changedUpdate(DocumentEvent e)
            {
            }

            @Override
            public void insertUpdate(DocumentEvent e)
            {
                logger.info("Snippet code change to: " + textEditor.getText());

                if (listOfSnippets.getModel().getSize() > 0)
                {
                    Snippet selectedSnippet = listOfSnippets.getSelectedValue();
                    int idOfSelectedSnippet = selectedSnippet.getId();
                    String currentSnippet = textEditor.getText();

                    try
                    {
                        JdbcSqliteConnection jdbcSqliteConnection = new JdbcSqliteConnection();
                        jdbcSqliteConnection.updateCodeSnippet(idOfSelectedSnippet, currentSnippet);
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
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

