package modeles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Publication {
    private int publicationId;
    private String title;
    private String content;
    private Date date;
    private User client;
    private User freelancer;
    private List<Commentaire> commentaires;

    public Publication(int publicationId, String title, String content, Date date, User client, User freelancer) {
        this.publicationId = publicationId;
        this.title = title;
        this.content = content;
        this.date = date;
        this.client = client;
        this.freelancer = freelancer;
        this.commentaires = new ArrayList<>();
    }

    // Getters and Setters

    public int getPublicationId() {
        return publicationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public User getclient() {
        return client;
    }

    public void setclient(User client) {
        this.client = client;
    }

    public User getUser() {
        return freelancer;
    }

    public void setUser(User freelancer) {
        this.freelancer = freelancer;
    }

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void addCommentaire(Commentaire commentaire) {
        this.commentaires.add(commentaire);
    }

    public boolean isClient() {
        return client.getRole() == Role.CLIENT;
    }

    // Add this method to check if the author is a Freelancer
    public boolean isFreelancer() {
        return freelancer.getRole() == Role.FREELANCER;
    }
}
