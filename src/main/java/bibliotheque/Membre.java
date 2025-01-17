package bibliotheque;


import java.sql.Date;

public class Membre {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private Date adhesiondate;

    public Membre() {
    }

    public Membre(int id, String nom, String prenom, String email, Date adhesiondate) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adhesiondate = adhesiondate;
    }

    public Membre(String nom, String prenom, String email, Date adhesiondate) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adhesiondate = adhesiondate;
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

    public Date getAdhesiondate() {
        return adhesiondate;
    }

    public void setAdhesiondate(Date adhesiondate) {
        this.adhesiondate = adhesiondate;
    }

    @Override
    public String toString() {
        return "Membre{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prénomn='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", date adhesion=" + adhesiondate +
                '}';
    }
}
