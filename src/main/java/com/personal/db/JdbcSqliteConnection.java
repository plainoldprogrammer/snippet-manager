package com.personal.db;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class JdbcSqliteConnection
{
    final static Logger logger = LogManager.getLogger(JdbcSqliteConnection.class);

    public static void main(String args[])
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:src/main/resources/snippets.db";
            String sqlQuery = "SELECT * FROM snippets";

            Connection connection = DriverManager.getConnection(dbURL);

            if (connection != null)
            {
                logger.info("Connected ok!");
                logger.info(connection);
                Statement statement = connection.createStatement();
                logger.info(statement);
                ResultSet resultSet = statement.executeQuery(sqlQuery);

                while (resultSet.next())
                {
                    logger.info(resultSet.getInt("id") + "\t" + resultSet.getString("category") + "\t" +
                        resultSet.getString("title") + "\t");
                }
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
