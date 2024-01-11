package modeles;


import java.awt.image.BufferedImage;
import java.io.File;


public class User {

    private int idUser;
    private String userName;
    private String email;
    private int number;
    private String status;
    private File resumeFile;
    private BufferedImage image;
    private String skills;
    private Role role;

    public User() {
    }

    public User(int idUser, String userName, String email, int number, String status, File resumeFile, BufferedImage image, String skills, Role role) {
        this.idUser = idUser;
        this.userName = userName;
        this.email = email;
        this.number = number;
        this.status = status;
        this.resumeFile = resumeFile;
        this.image = image;
        this.skills = skills;
        this.role = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public File getResumeFile() {
        return resumeFile;
    }

    public void setResumeFile(File resumeFile) {
        this.resumeFile = resumeFile;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", number=" + number +
                ", status='" + status + '\'' +
                ", resumeFile=" + resumeFile +
                ", image=" + image +
                ", skills=" + skills +
                ", role=" + role +
                '}';
    }
}