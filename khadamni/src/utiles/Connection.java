package utiles;

import java.sql.*;

public class Connection {
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/khadamni";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    private java.sql.Connection connection;

    public Connection() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

            System.out.println("Connected to the database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Getter method to access the connection
    public java.sql.Connection getConnection() {
        return connection;
    }

    // Close the connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

