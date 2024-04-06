package Entity;

public class User {


    private int id;
    private String nom;
    private String prenom;
    private String email;
    private int num;
    private String role;
    private String duree;

    public User(int id ,String nom, String prenom, String email, int num, String role, String duree){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.num = num;
        this.role = role;
        this.duree = duree;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDuree() {
        return duree;
    }


    public void setDuree(String duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", num=" + num +
                ", role='" + role + '\'' +
                ", duree=" + duree +
                '}';
    }
}
