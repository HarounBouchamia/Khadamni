package modeles;

public enum Role {
    ADMIN(1),
    CLIENT(2),
    FREELANCER(3);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
