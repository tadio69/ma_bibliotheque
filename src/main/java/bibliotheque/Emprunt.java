package bibliotheque;


import java.sql.Date;

public class Emprunt {
    private int id;
    private int membre_id;
    private int livre_id;
    private Date date_emprunt;
    private Date date_retour_prevue;
    private Date date_retour_effective; //peut être null

    public Emprunt() {
    }

    public Emprunt(int id, int membre_id, int livre_id, Date date_emprunt, Date date_retour_prevue, Date date_retour_effective) {
        if (!sontDesDatesValides(date_emprunt, date_retour_prevue)) {
            System.out.println("Les dates ne sont pas dans le bon ordre");
        }

        this.id = id;
        this.membre_id = membre_id;
        this.livre_id = livre_id;
        this.date_emprunt = date_emprunt;
        this.date_retour_prevue = date_retour_prevue;
        this.date_retour_effective = date_retour_effective;
    }

    public Emprunt(int membre_id, int livre_id, Date date_emprunt, Date date_retour_prevue) {
        if (!sontDesDatesValides(date_emprunt, date_retour_prevue)) {
            System.out.println("Les dates ne sont pas dans le bon ordre");
        }

        this.membre_id = membre_id;
        this.livre_id = livre_id;
        this.date_emprunt = date_emprunt;
        this.date_retour_prevue = date_retour_prevue;
        this.date_retour_effective = null;
    }

    public Emprunt(int membre_id, int livre_id, Date date_emprunt, Date date_retour_prevue, Date date_retour_effective) {
        if (!sontDesDatesValides(date_emprunt, date_retour_prevue)) {
            System.out.println("Les dates ne sont pas dans le bon ordre");
        }

        this.membre_id = membre_id;
        this.livre_id = livre_id;
        this.date_emprunt = date_emprunt;
        this.date_retour_prevue = date_retour_prevue;
        this.date_retour_effective = date_retour_effective;
    }

    public int getId() {
        return id;
    }

    public int getMembre_id() {
        return membre_id;
    }

    public int getLivre_id() {
        return livre_id;
    }

    public Date getDate_emprunt() {
        return date_emprunt;
    }

    public Date getDate_retour_prevue() {
        return date_retour_prevue;
    }

    public Date getDate_retour_effective() {
        return date_retour_effective;
    }

    private boolean sontDesDatesValides(Date date_emprunt, Date date_retour_prevue) {
        return date_emprunt.before(date_retour_prevue);
    }

    @Override
    public String toString() {
        return "Emprunt{" +
                "id=" + id +
                ", membre_id='" + membre_id + '\'' +
                ", livre_id='" + livre_id + '\'' +
                ", date_emprunt='" + date_emprunt + '\'' +
                ", date_retour_prevue='" + date_retour_prevue + '\'' +
                ", date_retour_effective=" + date_retour_effective +
                '}';
    }
}
