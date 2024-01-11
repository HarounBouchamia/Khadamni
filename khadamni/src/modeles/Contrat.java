package modeles;

public class Contrat {
    private int idContrat;
    private String titreContrat;
    private String statutContrat;
    private String descriptionContrat;
    private int idUsers;
    private int idOffre;

    // Constructors
    public Contrat() {
    }

    public Contrat(int idContrat, String titreContrat, String statutContrat, String descriptionContrat, int idUsers, int idOffre) {
        this.idContrat = idContrat;
        this.titreContrat = titreContrat;
        this.statutContrat = statutContrat;
        this.descriptionContrat = descriptionContrat;
        this.idUsers = idUsers;
        this.idOffre = idOffre;
    }

    // Getters and Setters
    public int getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(int idContrat) {
        this.idContrat = idContrat;
    }

    public String getTitreContrat() {
        return titreContrat;
    }

    public void setTitreContrat(String titreContrat) {
        this.titreContrat = titreContrat;
    }

    public String getStatutContrat() {
        return statutContrat;
    }

    public void setStatutContrat(String statutContrat) {
        this.statutContrat = statutContrat;
    }

    public String getDescriptionContrat() {
        return descriptionContrat;
    }

    public void setDescriptionContrat(String descriptionContrat) {
        this.descriptionContrat = descriptionContrat;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    // toString method to display object information
    @Override
    public String toString() {
        return "Contrat{" +
                "idContrat=" + idContrat +
                ", titreContrat='" + titreContrat + '\'' +
                ", statutContrat='" + statutContrat + '\'' +
                ", descriptionContrat='" + descriptionContrat + '\'' +
                ", idUsers=" + idUsers +
                ", idOffre=" + idOffre +
                '}';
    }
}