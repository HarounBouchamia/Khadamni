package gui;

import modeles.Role;


import java.sql.*;
import java.util.*;
import utiles.Connection.*;

public class RoleGui {
    private static final String url = "jdbc:mysql://localhost:3306/khadamni";
    private static final String user = "root";
    private static final String password = "";

    public RoleGui() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void insertRoles() {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = conn.prepareStatement(
                     "INSERT INTO role (roleName) VALUES (?)"
             )) {
            statement.setString(1, "Administrator");
            statement.executeUpdate();

            statement.setString(1, "Client");
            statement.executeUpdate();

            statement.setString(1, "Freelancer");
            statement.executeUpdate();

            System.out.println("Roles inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
