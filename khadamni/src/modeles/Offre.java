package modeles;

import java.util.Date;
    public class Offre {
        private int idOffre;
        private String titreOffre;
        private String descriptionOffre;
        private double montantOffre;
        private String statutOffre;
        private Date deadlineOffre;

        // Constructors
        public Offre(String titre1, String description1, double v, String enCours, java.sql.Date date) {
        }

        public Offre(int id, String titre, String description, double montant, String statut, Date deadline) {
            this.idOffre = id;
            this.titreOffre = titre;
            this.descriptionOffre = description;
            this.montantOffre = montant;
            this.statutOffre = statut;
            this.deadlineOffre = deadline;
        }

        // Getters and Setters
        public int getId() {
            return idOffre;
        }

        public void setId(int id) {
            this.idOffre = id;
        }

        public String getTitre() {
            return titreOffre;
        }

        public void setTitre(String titre) {
            this.titreOffre = titre;
        }

        public String getDescription() {
            return descriptionOffre;
        }

        public void setDescription(String description) {
            this.descriptionOffre = description;
        }

        public double getMontant() {
            return montantOffre;
        }

        public void setMontant(double montant) {
            this.montantOffre = montant;
        }

        public String getStatut() {
            return statutOffre;
        }

        public void setStatut(String statut) {
            this.statutOffre = statut;
        }

        public Date getDeadline() {
            return deadlineOffre;
        }

        public void setDeadline(Date deadline) {
            this.deadlineOffre = deadline;
        }

        // toString method to display object information
        @Override
        public String toString() {
            return "Offre{" +
                    "id=" + idOffre +
                    ", titre='" + titreOffre + '\'' +
                    ", description='" + descriptionOffre + '\'' +
                    ", montant=" + montantOffre +
                    ", statut='" + statutOffre + '\'' +
                    ", deadline=" + deadlineOffre +
                    '}';
        }
    }

