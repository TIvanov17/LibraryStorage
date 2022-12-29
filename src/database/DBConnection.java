package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection{

    private static Connection connection;

    private static final String databaseUrl = "jdbc:h2:file:./data/LibraryDatabase";

    private static final String username = "sa";
    private static final String password = "1234";

    public static Connection getConnection(){

        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(databaseUrl, username, password);

        } catch (ClassNotFoundException | SQLException e){
                e.printStackTrace();
        }

        return connection;
    }
}
