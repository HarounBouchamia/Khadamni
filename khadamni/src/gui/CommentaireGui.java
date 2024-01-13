package gui;

import java.sql.*;
import java.util.*;
import java.util.Date;

import modeles.Role;
import modeles.User;
import modeles.Commentaire;




public class CommentaireGui{

    public static int createCommentaire(Connection connection, Commentaire commentaire) {
        try (   Connection conn = utiles.Connection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO commentaire (content, date, userId) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, commentaire.getContent());
            preparedStatement.setDate(2, new java.sql.Date(commentaire.getDate().getTime()));
            preparedStatement.setInt(3, commentaire.getUserId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the auto-generated key (commentaireId) after insertion
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Retrieve all Commentaires for a given user
    public static List<Commentaire> getAllCommentairesForUser(Connection connection, User user) {
        List<Commentaire> commentaires = new ArrayList<>();
        try (   Connection conn = utiles.Connection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM commentaire WHERE userId = ?")) {

            preparedStatement.setInt(1, user.getIdUser());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int commentaireId = resultSet.getInt("commentaireId");
                    String content = resultSet.getString("content");
                    Date date = resultSet.getDate("date");

                    Commentaire commentaire = new Commentaire(commentaireId, content, date, user.getIdUser());
                    commentaires.add(commentaire);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentaires;
    }

    // Update an existing Commentaire
    public static boolean updateCommentaire(Connection connection, Commentaire commentaire) {
        try (   Connection conn = utiles.Connection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE commentaire SET content = ?, date = ? WHERE commentaireId = ?")) {

            preparedStatement.setString(1, commentaire.getContent());
            preparedStatement.setDate(2, new java.sql.Date(commentaire.getDate().getTime()));
            preparedStatement.setInt(3, commentaire.getCommentaireId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a Commentaire
    public static boolean deleteCommentaire(Connection connection, int commentaireId) {
        try (   Connection conn = utiles.Connection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM commentaire WHERE commentaireId = ?")) {

            preparedStatement.setInt(1, commentaireId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

