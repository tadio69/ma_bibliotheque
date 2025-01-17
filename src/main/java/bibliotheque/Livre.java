package bibliotheque;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private String categorie;
    private int nbexemplaires;

    public Livre() {
    }

    public Livre(int id, String titre, String auteur, String categorie, int nbexemplaires) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.nbexemplaires = nbexemplaires;
    }

    public Livre(String titre, String auteur, String categorie, int nbexemplaires) {
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.nbexemplaires = nbexemplaires;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getNbexemplaires() {
        return nbexemplaires;
    }

    public void setNbexemplaires(int nbexemplaires) {
        this.nbexemplaires = nbexemplaires;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", categorie='" + categorie + '\'' +
                ", nbexemplaires=" + nbexemplaires +
                '}';
    }
}
