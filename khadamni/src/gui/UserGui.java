package gui;

import modeles.Role;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.ArrayList;

public class UserGui {
    private static final String url = "jdbc:mysql://localhost:3306/khadamni";
    private static final String user = "root";
    private static final String password = "";

    public UserGui() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static void createUser(String userName, String email, int number, String status, ArrayList<String> skills, Role role) {
        try (java.sql.Connection conn = utiles.Connection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "INSERT INTO user (userName, email, number, status, image, skills, role) VALUES (?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, number);
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, "");
            preparedStatement.setString(6, String.join(",", skills));
            preparedStatement.setInt(7, role.getId());

            preparedStatement.executeUpdate();

            // Get the generated ID for the new user
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                System.out.println("New user ID: " + generatedId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUser(int userId, String userName, String email, int number, String status, ArrayList<String> skills, Role role) {
        try (Connection conn = utiles.Connection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "UPDATE user SET userName=?, email=?, number=?, status=?, skills=?, role=? WHERE id=?")) {

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, number);
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, String.join(",", skills));
            preparedStatement.setInt(6, role.getId());
            preparedStatement.setInt(7, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("No user found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void deleteUser(int id) {
        try (java.sql.Connection conn = utiles.Connection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM user WHERE id = ?")) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
