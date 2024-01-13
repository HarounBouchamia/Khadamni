package modeles;

import java.util.Date;

public class Reclamation {
    private int idReclamation;
    private String type;
    private String description;
    private String statut;
    private int idUser; // Nouvel attribut pour l'ID de l'utilisateur
    private Date dateCreation;

    // Constructeurs

    public Reclamation() {
        // Constructeur par défaut
    }

    public Reclamation(int id, String type, String description, String statut, int idUtilisateur, Date dateCreation) {
        this.idReclamation = id;
        this.type = type;
        this.description = description;
        this.statut = statut;
        this.idUser = idUtilisateur;
        this.dateCreation = dateCreation;
    }

    // Getters et setters

    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    // Méthode pour simuler la réponse à la réclamation par l'envoi d'un email
    public void repondreParEmail(String reponse) {
        // Logique d'envoi d'email (simulation)
        System.out.println("Envoi d'email à l'utilisateur avec l'ID " + idUser + " : " + reponse);
    }
}
