package com.personal.db;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import com.personal.util.Snippet;
import com.personal.util.Category;

public class JdbcSqliteConnection
{
    final static Logger logger = LogManager.getLogger(JdbcSqliteConnection.class);

    public static void main(String args[])
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

                    String sqlQueryForSnippetsOfACategory = "SELECT title, snippet FROM snippets WHERE category = '" + categoryName + "'";
                    logger.info(sqlQueryForSnippetsOfACategory);

                    Statement statementForSnippets = connection.createStatement();
                    ResultSet resultSetOfSnippets = statementForSnippets.executeQuery(sqlQueryForSnippetsOfACategory);

                    while (resultSetOfSnippets.next())
                    {
                        logger.info(resultSetOfSnippets.getString("title"));

                        String titleOfSnippet = resultSetOfSnippets.getString("title");
                        String codeSnippet = resultSetOfSnippets.getString("snippet");

                        Snippet currentSnippet = new Snippet(titleOfSnippet, codeSnippet);
                        currentCategory.addSnippet(currentSnippet);
                    }

                    listOfCategories.add(currentCategory);
                }

                logger.info("Categories: " + listOfCategories.size());
                logger.info("Snippet in category 1: " + listOfCategories.get(0).getListOfSnippets().size());
                logger.info("Snippet in category 2: " + listOfCategories.get(1).getListOfSnippets().size());
                logger.info("Snippet in category 3: " + listOfCategories.get(2).getListOfSnippets().size());
                logger.info("Snippet in category 4: " + listOfCategories.get(3).getListOfSnippets().size());

            }
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
}
