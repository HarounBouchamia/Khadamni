package gui;


import modeles.Reponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReponseCrud {

    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://your_database_url";
    private static final String JDBC_USER = "your_database_user";
    private static final String JDBC_PASSWORD = "your_database_password";

    // SQL queries
    private static final String INSERT_REPONSE_SQL = "INSERT INTO reponses (contenu, id_reclamation, date_creation) VALUES (?, ?, ?)";
    private static final String SELECT_REPONSE_BY_ID_SQL = "SELECT * FROM reponses WHERE id_reponse = ?";
    private static final String SELECT_ALL_REPONSES_SQL = "SELECT * FROM reponses";
    private static final String UPDATE_REPONSE_SQL = "UPDATE reponses SET contenu = ?, date_creation = ? WHERE id_reponse = ?";
    private static final String DELETE_REPONSE_SQL = "DELETE FROM reponses WHERE id_reponse = ?";

    public void addReponse(Reponse reponse) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REPONSE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, reponse.getContenu());
            preparedStatement.setInt(2, reponse.getIdReclamation());
            preparedStatement.setDate(3, new java.sql.Date(reponse.getDateCreation().getTime()));

            preparedStatement.executeUpdate();

            // Retrieve the generated keys (in case of auto-incremented primary key)
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reponse.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Reponse getReponseById(int id) {
        Reponse reponse = null;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REPONSE_BY_ID_SQL)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    reponse = mapResultSetToReponse(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reponse;
    }

    public List<Reponse> getAllReponses() {
        List<Reponse> reponses = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_REPONSES_SQL)) {

            while (resultSet.next()) {
                Reponse reponse = mapResultSetToReponse(resultSet);
                reponses.add(reponse);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reponses;
    }

    public void updateReponse(Reponse reponse) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_REPONSE_SQL)) {

            preparedStatement.setString(1, reponse.getContenu());
            preparedStatement.setDate(2, new java.sql.Date(reponse.getDateCreation().getTime()));
            preparedStatement.setInt(3, reponse.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteReponse(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REPONSE_SQL)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Reponse mapResultSetToReponse(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_reponse");
        String contenu = resultSet.getString("contenu");
        int idReclamation = resultSet.getInt("id_reclamation");
        Date dateCreation = resultSet.getDate("date_creation");

        return new Reponse(id, contenu, idReclamation, dateCreation);
    }
}
