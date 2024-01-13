package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modeles.Role;
import modeles.User;
import modeles.Publication;
import gui.CommentaireGui;

public class PublicationGui {

    // Create a new Publication
    public static int createPublication(Connection connection, Publication publication) {
        try (   Connection conn = utiles.Connection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO publication (title, content, date, clientId, freelancerId) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, publication.getTitle());
            preparedStatement.setString(2, publication.getContent());
            preparedStatement.setDate(3, new java.sql.Date(publication.getDate().getTime()));
            preparedStatement.setInt(4, publication.getclient().getIdUser());
            preparedStatement.setInt(5, publication.getUser().getIdUser());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the auto-generated key (publicationId) after insertion
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the insertion fails
    }

    // Retrieve all Publications for a given client
    public static List<Publication> getAllPublicationsForClient(Connection connection, User client) {
        List<Publication> publications = new ArrayList<>();
        try (   Connection conn = utiles.Connection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM publication WHERE clientId = ?")) {

            preparedStatement.setInt(1, client.getIdUser());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int publicationId = resultSet.getInt("publicationId");
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    Date date = resultSet.getDate("date");

                    Publication publication = new Publication(publicationId, title, content, date, client, null);
                    publications.add(publication);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publications;
    }

    // Update an existing Publication
    public static boolean updatePublication(Connection connection, Publication publication) {
        try (   Connection conn = utiles.Connection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE publication SET title = ?, content = ?, date = ? WHERE publicationId = ?")) {

            preparedStatement.setString(1, publication.getTitle());
            preparedStatement.setString(2, publication.getContent());
            preparedStatement.setDate(3, new java.sql.Date(publication.getDate().getTime()));
            preparedStatement.setInt(4, publication.getPublicationId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a Publication
    public static boolean deletePublication(Connection connection, int publicationId) {
        try (   Connection conn = utiles.Connection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM publication WHERE publicationId = ?")) {

            preparedStatement.setInt(1, publicationId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}