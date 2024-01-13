package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modeles.Reclamation;

public class ReclamationCrud {

    private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    private static final String INSERT_QUERY = "INSERT INTO reclamation (type, description, statut, id_user, date_creation) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM reclamation";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM reclamation WHERE id_reclamation = ?";
    private static final String UPDATE_QUERY = "UPDATE reclamation SET type = ?, description = ?, statut = ?, id_user = ?, date_creation = ? WHERE id_reclamation = ?";
    private static final String DELETE_QUERY = "DELETE FROM reclamation WHERE id_reclamation = ?";

    public static void main(String[] args) {
        // Testing CRUD operations
        ReclamationCrud crud = new ReclamationCrud();

        // Test Create (Insert)
        Reclamation newReclamation = new Reclamation(0, "Type1", "Description1", "Open", 1, new Date());
        int newReclamationId = crud.createReclamation(newReclamation);
        System.out.println("New Reclamation ID: " + newReclamationId);

        // Test Read (Select)
        List<Reclamation> reclamations = crud.getAllReclamations();
        for (Reclamation reclamation : reclamations) {
            System.out.println(reclamation);
        }

        // Test Update
        Reclamation updatedReclamation = new Reclamation(newReclamationId, "TypeUpdated", "DescriptionUpdated", "Closed", 1, new Date());
        crud.updateReclamation(updatedReclamation);

        // Test Read after Update
        Reclamation retrievedReclamation = crud.getReclamationById(newReclamationId);
        System.out.println("Retrieved Reclamation after update: " + retrievedReclamation);

        // Test Delete
        crud.deleteReclamation(newReclamationId);

        // Test Read after Delete
        Reclamation deletedReclamation = crud.getReclamationById(newReclamationId);
        System.out.println("Deleted Reclamation: " + deletedReclamation);
    }

    // Create (Insert) Operation
    public int createReclamation(Reclamation reclamation) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, reclamation.getType());
            preparedStatement.setString(2, reclamation.getDescription());
            preparedStatement.setString(3, reclamation.getStatut());
            preparedStatement.setInt(4, reclamation.getIdUser());
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(reclamation.getDateCreation().getTime()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating reclamation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating reclamation failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Return -1 to indicate failure
        }
    }

    // Read (Select) Operation - Get all reclamations
    public List<Reclamation> getAllReclamations() {
        List<Reclamation> reclamations = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Reclamation reclamation = mapResultSetToReclamation(resultSet);
                reclamations.add(reclamation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reclamations;
    }

    // Read (Select) Operation - Get a reclamation by ID
    public Reclamation getReclamationById(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToReclamation(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if reclamation with given ID is not found
    }

    // Update Operation
    public void updateReclamation(Reclamation reclamation) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {

            preparedStatement.setString(1, reclamation.getType());
            preparedStatement.setString(2, reclamation.getDescription());
            preparedStatement.setString(3, reclamation.getStatut());
            preparedStatement.setInt(4, reclamation.getIdUser());
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(reclamation.getDateCreation().getTime()));
            preparedStatement.setInt(6, reclamation.getIdReclamation());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Operation
    public void deleteReclamation(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to map ResultSet to Reclamation object
    private Reclamation mapResultSetToReclamation(ResultSet resultSet) throws SQLException {
        return new Reclamation(
                resultSet.getInt("id_reclamation"),
                resultSet.getString("type"),
                resultSet.getString("description"),
                resultSet.getString("statut"),
                resultSet.getInt("id_user"),
                resultSet.getTimestamp("date_creation")
        );
    }
}

