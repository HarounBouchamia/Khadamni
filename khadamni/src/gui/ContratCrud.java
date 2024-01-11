package gui;

import modeles.Contrat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratCrud {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/votre_base_de_donnees";
    private static final String USERNAME = "votre_nom_utilisateur";
    private static final String PASSWORD = "votre_mot_de_passe";

    public static void createContrat(Contrat contrat) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "INSERT INTO contrats (titreContrat, statutContrat, descriptionContrat, idUsers, idOffre) VALUES (?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, contrat.getTitreContrat());
            preparedStatement.setString(2, contrat.getStatutContrat());
            preparedStatement.setString(3, contrat.getDescriptionContrat());
            preparedStatement.setInt(4, contrat.getIdUsers());
            preparedStatement.setInt(5, contrat.getIdOffre());

            preparedStatement.executeUpdate();

            // Récupérer l'ID généré pour le nouveau contrat
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                contrat.setIdContrat(generatedId);
                System.out.println("Nouveau contrat ID: " + generatedId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Contrat getContratById(int contratId) {
        Contrat contrat = null;
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM contrats WHERE idContrat = ?")) {

            preparedStatement.setInt(1, contratId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                contrat = mapResultSetToContrat(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contrat;
    }

    public static List<Contrat> getAllContrats() {
        List<Contrat> contrats = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM contrats");
            while (resultSet.next()) {
                Contrat contrat = mapResultSetToContrat(resultSet);
                contrats.add(contrat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contrats;
    }

    public static void updateContrat(Contrat contrat) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "UPDATE contrats SET titreContrat=?, statutContrat=?, descriptionContrat=?, idUsers=?, idOffre=? WHERE idContrat=?")) {

            preparedStatement.setString(1, contrat.getTitreContrat());
            preparedStatement.setString(2, contrat.getStatutContrat());
            preparedStatement.setString(3, contrat.getDescriptionContrat());
            preparedStatement.setInt(4, contrat.getIdUsers());
            preparedStatement.setInt(5, contrat.getIdOffre());
            preparedStatement.setInt(6, contrat.getIdContrat());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Contrat mis à jour : " + contrat);
            } else {
                System.out.println("Contrat non trouvé. La mise à jour a échoué.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteContrat(int contratId) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM contrats WHERE idContrat=?")) {

            preparedStatement.setInt(1, contratId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Contrat avec l'ID " + contratId + " supprimé.");
            } else {
                System.out.println("Contrat non trouvé. La suppression a échoué.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Contrat mapResultSetToContrat(ResultSet resultSet) throws SQLException {
        int idContrat = resultSet.getInt("idContrat");
        String titreContrat = resultSet.getString("titreContrat");
        String statutContrat = resultSet.getString("statutContrat");
        String descriptionContrat = resultSet.getString("descriptionContrat");
        int idUsers = resultSet.getInt("idUsers");
        int idOffre = resultSet.getInt("idOffre");

        return new Contrat(idContrat, titreContrat, statutContrat, descriptionContrat, idUsers, idOffre);
    }

   }
