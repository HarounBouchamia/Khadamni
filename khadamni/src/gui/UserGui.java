package gui;

import modeles.Role;

import java.io.ByteArrayInputStream;
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
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "INSERT INTO users (userName, email, number, status, skills, roleId) VALUES (?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, number);
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, String.join(",", skills)); // Assuming skills is a comma-separated string
            preparedStatement.setInt(6, role.getIdRole());

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

//    public static User getUserById(int id) {
//        User user = null;
//        try (Connection conn = DriverManager.getConnection(url, user, password);
//             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
//
//            preparedStatement.setInt(1, id);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                user = new User(
//                        resultSet.getInt("id"),
//                        resultSet.getString("userName"),
//                        resultSet.getString("email"),
//                        resultSet.getInt("number"),
//                        resultSet.getString("status"),
//                        deserializeSkills(resultSet.getBytes("skills")),
//                        new Role(resultSet.getInt("roleId"), null), // Replace 'null' with actual roleName retrieval logic
//                        null // Replace 'null' with actual role object retrieval logic
//                );
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return user;
//    }

    private ArrayList<String> deserializeSkills(byte[] skillsData) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(skillsData);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (ArrayList<String>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

//    public static List<User> getAllUsers() {
//        List<User> users = new ArrayList<>();
//        try (Connection conn = DriverManager.getConnection(url, user, password);
//             Statement statement = conn.createStatement()) {
//
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
//            while (resultSet.next()) {
//                User user = new User(
//                        resultSet.getInt("id"),
//                        resultSet.getString("userName"),
//                        resultSet.getString("email"),
//                        resultSet.getInt("number"),
//                        resultSet.getString("status"),
//                        parseSkills(resultSet.getString("skills")),
//                        new Role(resultSet.getInt("roleId"), null), // Replace 'null' with actual roleName retrieval logic
//                        null // Replace 'null' with actual role object retrieval logic
//                );
//                users.add(user);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return users;
//    }

    public static void deleteUser(int id) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
