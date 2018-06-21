package com.personal.db;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;
import com.personal.util.Snippet;
import com.personal.util.Category;

public class JdbcSqliteConnection
{
    final static Logger logger = LogManager.getLogger(JdbcSqliteConnection.class);
    private List<Category> categoriesData;

    public JdbcSqliteConnection()
    {
        getSnippets();
    }

    private void getSnippets()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:src/main/resources/snippets.db";

            Connection connection = DriverManager.getConnection(dbURL);

            if (connection != null)
            {
                logger.info("Connected ok!");
                logger.info(connection);
                Statement statement = connection.createStatement();
                logger.info(statement);

                String sqlQuery = "SELECT DISTINCT category FROM snippets";
                ResultSet resultSet = statement.executeQuery(sqlQuery);

                List<Category> listOfCategories = new ArrayList();

                while (resultSet.next())
                {

                    String categoryName = resultSet.getString("category");
                    logger.info(categoryName);
                    Category currentCategory = new Category(categoryName);

                    String sqlQueryForSnippetsOfACategory = "SELECT id, title, snippet FROM snippets WHERE category = '" + categoryName + "'";
                    logger.info(sqlQueryForSnippetsOfACategory);

                    Statement statementForSnippets = connection.createStatement();
                    ResultSet resultSetOfSnippets = statementForSnippets.executeQuery(sqlQueryForSnippetsOfACategory);

                    while (resultSetOfSnippets.next())
                    {
                        logger.info(resultSetOfSnippets.getString("title"));

                        String idOfSnippet = resultSetOfSnippets.getString("id");
                        String titleOfSnippet = resultSetOfSnippets.getString("title");
                        String codeSnippet = resultSetOfSnippets.getString("snippet");

                        Snippet currentSnippet = new Snippet(titleOfSnippet, codeSnippet);
                        currentSnippet.setId(Integer.parseInt(idOfSnippet));
                        currentCategory.addSnippet(currentSnippet);

                        logger.info("current id: " + idOfSnippet);
                    }

                    listOfCategories.add(currentCategory);
                    statementForSnippets.close();
                }

                logger.info("Categories: " + listOfCategories.size());
                logger.info("Snippet in category 1: " + listOfCategories.get(0).getListOfSnippets().size());
                logger.info("Snippet in category 2: " + listOfCategories.get(1).getListOfSnippets().size());
                logger.info("Snippet in category 3: " + listOfCategories.get(2).getListOfSnippets().size());
                logger.info("Snippet in category 4: " + listOfCategories.get(3).getListOfSnippets().size());

                setCategoriesData(listOfCategories);

                statement.close();
            }

            connection.close();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void insertNewSnippetToDB(Category categoryToDB, Snippet newSnippetToDB) throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        String dbURL = "jdbc:sqlite:src/main/resources/snippets.db";

        Connection connection = DriverManager.getConnection(dbURL);

        if (connection != null)
        {
            String categoryTitle = categoryToDB.getTitle();
            String titleOfNewSnippet = newSnippetToDB.getTitle();
            String codeOfNewSnippet = newSnippetToDB.getCode();

            String sqlQuery = "INSERT INTO snippets(category, title, snippet) VALUES('" + categoryTitle + "', '" + titleOfNewSnippet + "', '" + codeOfNewSnippet + "')";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }
    }

    public int getLastId() throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        String dbURL = "jdbc:sqlite:src/main/resources/snippets.db";
        int lastId = -1;

        Connection connection = DriverManager.getConnection(dbURL);

        if (connection != null)
        {
            String sqlQuery = "SELECT id FROM snippets ORDER BY id DESC LIMIT 1;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            lastId = -1;

            while (resultSet.next())
            {
                lastId = Integer.parseInt(resultSet.getString("id"));
            }

            preparedStatement.close();
            connection.close();
        }

        return lastId;
    }

    public void updateTitleOfSnippet(int snippetId, String snippetTitle) throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        String dbURL = "jdbc:sqlite:src/main/resources/snippets.db";

        Connection connection = DriverManager.getConnection(dbURL);

        if (connection != null)
        {
            String sqlQuery = "UPDATE snippets SET title='" + snippetTitle + "' WHERE id=" + snippetId;
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }
    }

    public void updateCodeSnippet(int snippetId, String codeSnippet) throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        String dbURL = "jdbc:sqlite:src/main/resources/snippets.db";

        Connection connection = DriverManager.getConnection(dbURL);

        if (connection != null)
        {
            String sqlQuery = "UPDATE snippets SET snippet ='" + codeSnippet + "' WHERE id=" + snippetId ;
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }
    }

    public void deleteSnippet(int idRemovedSnippet) throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        String dbURL = "jdbc:sqlite:src/main/resources/snippets.db";

        Connection connection = DriverManager.getConnection(dbURL);

        if (connection != null)
        {
            String sqlQuery = "DELETE FROM snippets WHERE id=" + idRemovedSnippet;
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }
    }

    public void setCategoriesData(List<Category> listOfCategories)
    {
        categoriesData = listOfCategories;
    }

    public List<Category> getCategoriesData()
    {
        return categoriesData;
    }
}
