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
    private Connection connection;

    public JdbcSqliteConnection()
    {
        try
        {
            getSnippets();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Connection openDbConnection() throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        String dbURL = "jdbc:sqlite:src/main/resources/snippets.db";
        return DriverManager.getConnection(dbURL);
    }

    private void closeDbConnection()
    {
        try
        {
            if (connection != null)
            {
                connection.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void getSnippets()
    {
        try
        {
            connection = openDbConnection();
            if (connection != null)
            {
                Statement statement = connection.createStatement();
                String queryTableSnippets = "CREATE TABLE IF NOT EXISTS snippets (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, snippet TEXT, category TEXT)";
                statement.executeUpdate(queryTableSnippets);

                String sqlQuery = "SELECT DISTINCT category FROM snippets";
                try
                {
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

                    setCategoriesData(listOfCategories);

                    statement.close();
                }
                catch (Exception e)
                {
                    System.out.println("is NULL");
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeDbConnection();
        }
    }

    public void insertNewSnippetToDB(Category categoryToDB, Snippet snippet) throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        String dbURL = "jdbc:sqlite:src/main/resources/snippets.db";

        Connection connection = DriverManager.getConnection(dbURL);

        if (connection != null)
        {
            String categoryTitle = categoryToDB.getName();
            String titleOfNewSnippet = snippet.getTitle();
            String codeOfNewSnippet = snippet.getCode();

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
        int id = -1;

        Connection connection = DriverManager.getConnection(dbURL);

        if (connection != null)
        {
            String sqlQuery = "SELECT SEQ FROM SQLITE_SEQUENCE WHERE NAME='snippets'";
            logger.info("check the current id: " + sqlQuery);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            id = -1;

            while (resultSet.next())
            {
                id = Integer.parseInt(resultSet.getString("seq"));
            }

            preparedStatement.close();
            connection.close();
        }

        return id;
    }

    public void updateTitleOfSnippet(int id, String snippetTitle) throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        String dbURL = "jdbc:sqlite:src/main/resources/snippets.db";

        Connection connection = DriverManager.getConnection(dbURL);

        if (connection != null)
        {
            String sqlQuery = "UPDATE snippets SET title='" + snippetTitle + "' WHERE id=" + id;
            logger.info("query to execute: " + sqlQuery);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }
    }

    public void updateCodeSnippet(int id, String codeSnippet) throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        String dbURL = "jdbc:sqlite:src/main/resources/snippets.db";

        Connection connection = DriverManager.getConnection(dbURL);

        if (connection != null)
        {
            String sqlQuery = "UPDATE snippets SET snippet ='" + codeSnippet + "' WHERE id=" + id ;
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }
    }

    public void deleteSnippet(int id) throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        String dbURL = "jdbc:sqlite:src/main/resources/snippets.db";

        Connection connection = DriverManager.getConnection(dbURL);

        if (connection != null)
        {
            String sqlQuery = "DELETE FROM snippets WHERE id=" + id;
            logger.info("query to execute: " + sqlQuery);
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

    public void createCategory(String category)
    {
        try
        {
            connection = openDbConnection();
            String sqlQuery = "INSERT INTO categories(name) VALUES('" + category + "')";
            logger.info(sqlQuery);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeDbConnection();
        }
    }

    public void getCategories()
    {
        try
        {
            connection = openDbConnection();

            String sqlQuery = "SELECT * FROM categories";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next())
            {
                String id = resultSet.getString("id");
                String categoryName = resultSet.getString("name");
                logger.info (id +  " - " + categoryName);
            }

            statement.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeDbConnection();
        }
    }
}
