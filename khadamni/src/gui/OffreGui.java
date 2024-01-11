package gui;

import modeles.Offre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OffreGui {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USERNAME = "ROOT";
    private static final String PASSWORD = "";

    public static void createOffre(Offre offre) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "INSERT INTO offres (titre, description, montant, statut, deadline) VALUES (?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, offre.getTitre());
            preparedStatement.setString(2, offre.getDescription());
            preparedStatement.setDouble(3, offre.getMontant());
            preparedStatement.setString(4, offre.getStatut());
            preparedStatement.setDate(5, new java.sql.Date(offre.getDeadline().getTime()));

            preparedStatement.executeUpdate();

            // Get the generated ID for the new Offre
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                offre.setId(generatedId);
                System.out.println("New Offre ID: " + generatedId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Offre getOffreById(int offreId) {
        Offre offre = null;
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM offres WHERE id = ?")) {

            preparedStatement.setInt(1, offreId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                offre = mapResultSetToOffre(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offre;
    }

    public static List<Offre> getAllOffres() {
        List<Offre> offres = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM offres");
            while (resultSet.next()) {
                Offre offre = mapResultSetToOffre(resultSet);
                offres.add(offre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offres;
    }

    public static void updateOffre(Offre offre) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "UPDATE offres SET titre=?, description=?, montant=?, statut=?, deadline=? WHERE id=?")) {

            preparedStatement.setString(1, offre.getTitre());
            preparedStatement.setString(2, offre.getDescription());
            preparedStatement.setDouble(3, offre.getMontant());
            preparedStatement.setString(4, offre.getStatut());
            preparedStatement.setDate(5, new java.sql.Date(offre.getDeadline().getTime()));
            preparedStatement.setInt(6, offre.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Offre updated: " + offre);
            } else {
                System.out.println("Offre not found. Update failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteOffre(int offreId) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM offres WHERE id=?")) {

            preparedStatement.setInt(1, offreId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Offre with ID " + offreId + " deleted.");
            } else {
                System.out.println("Offre not found. Deletion failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Offre mapResultSetToOffre(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String titre = resultSet.getString("titre");
        String description = resultSet.getString("description");
        double montant = resultSet.getDouble("montant");
        String statut = resultSet.getString("statut");
        Date deadline = resultSet.getDate("deadline");

        return new Offre(id, titre, description, montant, statut, deadline);
    }


}