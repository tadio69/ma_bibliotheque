package bibliotheque;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String continuer = "o";
        LivreDAO livreDAO = new LivreDAOImpl();
        MembreDAO membreDAO = new MembreDAOImpl();
        EmpruntDAO empruntDAO = new EmpruntDAOImpl();
        List<Livre> livres = null;
        List<Membre> membres = null;
        List<Emprunt> emprunts = null;
        while (continuer.equals("o") || continuer.equals("n")) {
            clearConsole();
            afficherMenuGeneral();
            int menuGeneral = lireNombre(1, 18, scanner);
            switch (menuGeneral) {
                case 1: //Ajout d'un livre
                    ajouterLivre(livreDAO, scanner);
                    break;
                case 2: // Recherche de livre
                    livres = livreDAO.getAll();
                    if(!livres.isEmpty()){
                        System.out.println("Choisissez un type de critère de recherche");
                        afficherMenuCritere();
                        int menuCritere = lireNombre(1, 3, scanner);
                        int idx = 0;
                        switch (menuCritere) {
                            case 1:
                                String titre = lireNom(scanner, "le titre du livre");
                                idx = rechercherLivreByTitre(livres, titre);
                                if (idx != -1) {
                                    System.out.println("Livre trouvé après Linear Search :");
                                    System.out.println("-------------------------------------------------");
                                    afficherDetails(livres.get(idx));
                                } else {
                                    System.out.println("Aucun livre ne correspond au titre : " +'"'+ titre+'"');
                                }
                                break;
                            case 2:
                                String categorie = lireNom(scanner, "la catégorie du livre");
                                idx = rechercherLivreByCategorie(livres, categorie);;
                                if (idx != -1) {
                                    System.out.println("Livre trouvé après Linear Search :");
                                    System.out.println("-------------------------------------------------");
                                    afficherDetails(livres.get(idx));
                                } else {
                                    System.out.println("Aucun livre ne correspond à la catégorie : " +'"'+ categorie+'"');
                                }
                                break;
                            case 3:
                                String auteur = lireNom(scanner, "l'auteur du livre");
                                idx = rechercherLivreByAuteur(livres, auteur);;
                                if (idx != -1) {
                                    System.out.println("Livre trouvé après Linear Search :");
                                    System.out.println("-------------------------------------------------");
                                    afficherDetails(livres.get(idx));
                                } else {
                                    System.out.println("Aucun livre ne correspond à l'auteur : " +'"'+ auteur+'"');
                                }
                                break;
                        }
                    }else{
                        System.out.println("Il n'y a aucun livre enregisté. Veuillez d'abord ajouter des livres.");
                    }
                    break;
                case 3: //Modification d'un livre
                    livres = livreDAO.getAll();
                    if(!livres.isEmpty()){
                        int id = lireId(scanner, "du livre");
                        Livre livre = livreDAO.get(id);
                        if(livre != null){
                            System.out.print("Voulez-vous vraiment modifier le livre d'ID "+id+" (o/n)? ");
                            String reponse = scanner.nextLine();
                            while (!(reponse.equals("o") || reponse.equals("n"))) {
                                System.out.println("Mauvaise réponse: Veuillez entrer o ou n svp.");
                                System.out.print("Voulez-vous vraiment modifier le livre d'ID "+id+" (o/n)? ");
                                reponse = scanner.nextLine();
                            }
                            if(reponse.equals("o")){
                                livre = lireLivre(scanner);
                                livreDAO.update(new Livre(id, livre.getTitre(), livre.getAuteur(), livre.getCategorie(), livre.getNbexemplaires()));
                                System.out.println("Livre d'ID "+id+" modifié avec succès.");
                            }
                        }else{
                            System.out.println("Aucun livre ne correspond à cet identifiant:"+id);
                        }
                    }else{
                        System.out.println("Il n'y a aucun livre enregisté. Veuillez d'abord ajouter des livres.");
                    }
                    break;
                case 4: //Suppression d'un livre
                    livres = livreDAO.getAll();
                    if(!livres.isEmpty()){
                        int id = lireId(scanner, "du livre");
                        Livre livre = livreDAO.get(id);
                        if(livre != null){
                            System.out.print("Voulez-vous vraiment supprimer le livre d'ID "+id+" (o/n)? ");
                            String reponse = scanner.nextLine();
                            while (!(reponse.equals("o") || reponse.equals("n"))) {
                                System.out.println("Mauvaise réponse: Veuillez entrer o ou n svp.");
                                System.out.print("Voulez-vous vraiment modifier le livre d'ID "+id+" (o/n)? ");
                                reponse = scanner.nextLine();
                            }
                            if(reponse.equals("o")){
                                livreDAO.delete(livre);
                                System.out.println("Livre d'ID "+id+" supprimé avec succès.");
                            }
                        }else{
                            System.out.println("Aucun livre ne correspond à cet identifiant:"+id);
                        }
                    }else{
                        System.out.println("Il n'y a aucun livre enregisté. Veuillez d'abord ajouter des livres.");
                    }
                    break;
                case 5: //Affichage de la liste des livres disponibles
                    livres = livreDAO.getAll();
                    if(!livres.isEmpty()){
                        afficherTousLivres(livres);
                    }else{
                        System.out.println("Il n'y a aucun livre enregisté. Veuillez d'abord ajouter des livres.");
                    }
                    break;
                case 6: //Enregistrer nouveau membre
                    ajouterMembre(membreDAO, scanner);
                    break;
                case 7: // Recherche de membre
                    membres = membreDAO.getAll();
                    if(!membres.isEmpty()){
                        String critere = lireNom(scanner, "le nom du membre");
                        int idx = rechercherMembre(membres, critere);
                        if (idx != -1) {
                            System.out.println("Membre trouvé après Linear Search :");
                            System.out.println("-------------------------------------------------");
                            afficherDetails(membres.get(idx));
                        } else {
                            System.out.println("Aucun membre ne correspond au nom : " +'"'+ critere+'"');
                        }
                    }else{
                        System.out.println("Il n'y a aucun membre enregisté. Veuillez d'abord ajouter des membres.");
                    }
                    break;
                case 8: //Modification d'un membre
                    membres = membreDAO.getAll();
                    if(!membres.isEmpty()){
                        int id = lireId(scanner, "du membre");
                        Membre membre = membreDAO.get(id);
                        if(membre != null){
                            System.out.print("Voulez-vous vraiment modifier le membre d'ID "+id+" (o/n)? ");
                            String reponse = scanner.nextLine();
                            while (!(reponse.equals("o") || reponse.equals("n"))) {
                                System.out.println("Mauvaise réponse: Veuillez entrer o ou n svp.");
                                System.out.print("Voulez-vous vraiment modifier le membre d'ID "+id+" (o/n)? ");
                                reponse = scanner.nextLine();
                            }
                            if(reponse.equals("o")){
                                membre = lireMembre(scanner);
                                membreDAO.update(new Membre(id, membre.getNom(), membre.getPrenom(), membre.getEmail(), membre.getAdhesiondate()));
                                System.out.println("Membre d'ID "+id+" modifié avec succès.");
                            }
                        }else{
                            System.out.println("Aucun membre ne correspond à cet identifiant:"+id);
                        }
                    }else{
                        System.out.println("Il n'y a aucun membre enregisté. Veuillez d'abord ajouter des membres.");
                    }
                    break;
                case 9: //Suppression d'un membre
                    membres = membreDAO.getAll();
                    if(!membres.isEmpty()){
                        int id = lireId(scanner, "du membre");
                        Membre membre = membreDAO.get(id);
                        if(membre != null){
                            System.out.print("Voulez-vous vraiment supprimer le membre d'ID "+id+" (o/n)? ");
                            String reponse = scanner.nextLine();
                            while (!(reponse.equals("o") || reponse.equals("n"))) {
                                System.out.println("Mauvaise réponse: Veuillez entrer o ou n svp.");
                                System.out.print("Voulez-vous vraiment modifier le membre d'ID "+id+" (o/n)? ");
                                reponse = scanner.nextLine();
                            }
                            if(reponse.equals("o")){
                                membreDAO.delete(membre);
                                System.out.println("Membre d'ID "+id+" supprimé avec succès.");
                            }
                        }else{
                            System.out.println("Aucun membre ne correspond à cet identifiant:"+id);
                        }
                    }else{
                        System.out.println("Il n'y a aucun livre enregisté. Veuillez d'abord ajouter des livres.");
                    }
                    break;
                case 10: //Affichage de la liste des membres
                    membres = membreDAO.getAll();
                    if(!membres.isEmpty()){
                        afficherTousMembres(membres);
                    }else{
                        System.out.println("Il n'y a aucun membre enregisté. Veuillez d'abord ajouter des membres.");
                    }
                    break;
                case 11:// Enregistrer un nouvel emprunt
                    membres = membreDAO.getAll();
                    livres = livreDAO.getAll();
                    if(!membres.isEmpty() && !livres.isEmpty()){
                        empruntDAO.insert(lireEmprunt(membres, livres, scanner));
                        System.out.println("Emprunt enregisté avec succès.");
                    }else{
                        System.out.println("Soit il n'y a aucun membre enregisté, soit il n'y aucun livre enregistré. Veuillez d'abord ajouter des membres et des livres.");
                    }
                    break;
                case 12:// Rechercher un emprunt
                    membres = membreDAO.getAll();
                    livres = livreDAO.getAll();
                    emprunts = empruntDAO.getAll();
                    if(!emprunts.isEmpty()){
                        String critereMembre = lireNom(scanner, "le nom du membre");
                        int idxMembre = rechercherMembre(membres, critereMembre);
                        if (idxMembre != -1) {
                            String critereLivre = lireNom(scanner, "le titre du livre");
                            int idxLivre = rechercherLivreByTitre(livres, critereLivre);
                            if (idxLivre != -1) {
                                int idxEmprunt = rechercherEmprunt(emprunts, membres.get(idxMembre).getId(), livres.get(idxLivre).getId());
                                if(idxEmprunt != -1){
                                    System.out.println("Emprunt trouvé après Linear Search :");
                                    System.out.println("-------------------------------------------------");
                                    afficherDetails(emprunts.get(idxEmprunt));
                                }else{
                                    System.out.println("Aucun emprunt trouvé pour le membre ["+critereMembre+"] concernant le livre ["+critereLivre+"].");
                                }
                            } else {
                                System.out.println("Aucun livre ne correspond au titre : " +'"'+ critereLivre+'"');
                            }
                        } else {
                            System.out.println("Aucun membre ne correspond au nom : " +'"'+ critereMembre+'"');
                        }
                    }else{
                        System.out.println("Soit il n'y a aucun membre enregisté, soit il n'y aucun livre enregistré. Veuillez d'abord ajouter des membres et des livres.");
                    }
                    break;
                case 13: //Modification d'un emprunt
                    emprunts = empruntDAO.getAll();
                    if(!emprunts.isEmpty()){
                        int id = lireId(scanner, "de l'emprunt");
                        Emprunt emprunt = empruntDAO.get(id);
                        if(emprunt != null){
                            System.out.print("Voulez-vous vraiment modifier l'emprunt d'ID "+id+" (o/n)? ");
                            String reponse = scanner.nextLine();
                            while (!(reponse.equals("o") || reponse.equals("n"))) {
                                System.out.println("Mauvaise réponse: Veuillez entrer o ou n svp.");
                                System.out.print("Voulez-vous vraiment modifier l'emprunt d'ID "+id+" (o/n)? ");
                                reponse = scanner.nextLine();
                            }
                            if(reponse.equals("o")){
                                emprunt = lireEmprunt(membres, livres, scanner);
                                empruntDAO.update(new Emprunt(emprunt.getMembre_id(), emprunt.getLivre_id(), emprunt.getDate_emprunt(), emprunt.getDate_retour_prevue()));
                                System.out.println("Emprunt d'ID "+id+" modifié avec succès ");
                            }
                        }else{
                            System.out.println("Aucun emprunt ne correspond à cet identifiant:"+id);
                        }
                    }else{
                        System.out.println("Il n'y a aucun emprunt enregisté. Veuillez d'abord ajouter des emprunts.");
                    }
                    break;
                case 14: //Suppression emprunt
                    emprunts = empruntDAO.getAll();
                    if(!emprunts.isEmpty()){
                        int id = lireId(scanner, "de l'emprunt");
                        Emprunt emprunt = empruntDAO.get(id);
                        if(emprunt != null){
                            System.out.print("Voulez-vous vraiment supprimer l'emprunt d'ID "+id+" (o/n)? ");
                            String reponse = scanner.nextLine();
                            while (!(reponse.equals("o") || reponse.equals("n"))) {
                                System.out.println("Mauvaise réponse: Veuillez entrer o ou n svp.");
                                System.out.print("Voulez-vous vraiment modifier l'emprunt d'ID "+id+" (o/n)? ");
                                reponse = scanner.nextLine();
                            }
                            if(reponse.equals("o")){
                                empruntDAO.delete(emprunt);
                                System.out.println("Emprunt d'ID "+id+" supprimé avec succès.");
                            }
                        }else{
                            System.out.println("Aucun emprunt ne correspond à cet identifiant:"+id);
                        }
                    }else{
                        System.out.println("Il n'y a aucun emprunt enregisté. Veuillez d'abord ajouter des emprunts.");
                    }
                    break;
                case 15: // Afficher la liste de tous les emprunts
                    emprunts = empruntDAO.getAll();
                    if(!emprunts.isEmpty()){
                        afficherTousEmprunts(emprunts);
                    }else{
                        System.out.println("Il n'y a aucun emprunt enregisté. Veuillez d'abord ajouter des emprunts.");
                    }
                    break;
                case 16: // Gérer le retour d'un livre et afficher la pénalité s'il y en a
                    emprunts = empruntDAO.getAll();
                    if(!emprunts.isEmpty()){
                        int id = lireId(scanner, "de l'emprunt");
                        Emprunt emprunt = empruntDAO.get(id);
                        if(emprunt != null){
                            System.out.println("Donnez la date de retour effective du livre: ");
                            Date date = lireDate(scanner);
                            empruntDAO.enregistrerRetour(emprunt.getId(), date);
                            System.out.println("Enregistrement du retour de livre effectué avec succès.");
                            calculerPenalite(emprunt.getDate_retour_prevue(), date);
                            /*
                            // Conversion en LocalDate
                            LocalDate dateDebut = emprunt.getDate_retour_prevue().toLocalDate();
                            LocalDate dateFin = date.toLocalDate();

                            // Calcul de la période de retard
                            Period periode = Period.between(dateDebut, dateFin);

                            // Extraction du nombre de jours de retard
                            int nombreJours = periode.getDays();
                            if(nombreJours > 0){
                                System.out.println("La pénalité est de : "+nombreJours*100+" FCFA");
                            }
                            */
                        }else{
                            System.out.println("Aucun emprunt ne correspond à cet identifiant:"+id);
                        }
                    }else{
                        System.out.println("Il n'y a aucun emprunt enregisté. Veuillez d'abord ajouter des emprunts.");
                    }
                    break;
                case 17: // Afficher tous les emprunts en retard
                    emprunts = empruntDAO.getAll();
                    if(!emprunts.isEmpty()){
                        List<Emprunt> empruntsEnRetard = empruntDAO.getEmpruntsEnRetard();
                        afficherTousEmpruntsEnRetard(empruntsEnRetard);
                    }else{
                        System.out.println("Il n'y a aucun emprunt enregisté. Veuillez d'abord ajouter des emprunts.");
                    }

                    break;
                case 18: // Quitter
                    System.exit(0);
                    break;
            }

            System.out.print("Voulez-vous effectuer un autre choix (o/n)? ");

            continuer = scanner.nextLine();
            while (!(continuer.equals("o") || continuer.equals("n"))) {
                System.out.println("Mauvaise réponse: Veuillez entrer o ou n svp.");
                System.out.print("Voulez-vous effectuer un autre choix (o/n)? ");
                continuer = scanner.nextLine();
            }
        }
        scanner.close();
    }

    // Méthode permettant de lire le nombre d'exemplaires de publication entre 1900 et max à la
    // console
    public static int lireNombreExemplaires(Scanner scan) {
        int n = 0;
        try {
            n = scan.nextInt();
            scan.nextLine(); // Consume newline after reading integer
        } catch (InputMismatchException e) {
            scan.nextLine(); // Consomme l'entrée invalide
        }
        if (n <= 0) {
            System.out.println("Valeur rejetée. Vous devez saisir un nombre strictement positif");
            System.out.print("Nombre d'exemplaires: ");
        }

        return n;
    }

    // Méthode permettant de lire un nombre entre 2 bornes à la console
    public static int lireNombre(int min, int max, Scanner scan) {
        int n = 0;
        while (n < min || n > max) {
            System.out.print("Quel est votre choix: ");
            try {
                n = scan.nextInt();
                scan.nextLine(); // Consume newline after reading integer
            } catch (InputMismatchException e) {
                scan.nextLine(); // Consomme l'entrée invalide
            }
            if (n < min || n > max) {
                System.out.println("Valeur rejetée. Vous devez saisir un nombre entre " + min + " et " + max + ".");
            }
        }

        return n;
    }

    // Méthode permettant de lire le titre ou le nom de l'emprunteur d'un livre à la console
    public static String lireNom(Scanner scan, String str) {
        System.out.println("Donnez "+str+" :");
        String ligne = scan.nextLine();
        while (ligne == null || ligne.length() < 2) {
            System.out.println("Le "+str+" doit être fourni et contenir au moins 2 caractères.");
            System.out.println("Donnez "+str+" :");
            ligne = scan.nextLine();
        }
        return ligne;
    }

    // Méthode permettant de lire le titre ou le nom de l'emprunteur d'un livre à la console
    public static int lireId(Scanner scan, String str) {
        int n = 0;
        System.out.println("Donnez l'id "+str+" :");
        try {
            n = scan.nextInt();
            scan.nextLine(); // Consume newline after reading integer
        } catch (InputMismatchException e) {
            scan.nextLine(); // Consomme l'entrée invalide
        }
        return n;
    }

    public  static Date lireDate(Scanner scan){
        String dateSaisie = scan.nextLine();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dateUtil = null;
        try {
            dateUtil = sdf.parse(dateSaisie);
        } catch (ParseException e) {
            System.out.println("Format de date incorrect.");
        }
        java.sql.Date dateSql = new java.sql.Date(dateUtil.getTime());

        return dateSql;
    }

    // Méthode qui lit les données d'un livre à la console
    public static Livre lireLivre(Scanner scan) {
        String titre = "";
        System.out.println("Titre du livre :");
        if (scan.hasNextLine()) {
            titre = scan.nextLine();
        }
        String auteur = "";
        System.out.println("Auteur du livre :");
        if (scan.hasNextLine()) {
            auteur = scan.nextLine();
        }
        String categorie = "";
        System.out.println("Catégorie du livre :");
        if (scan.hasNextLine()) {
            categorie = scan.nextLine();
        }
        int nbexemplaires = 0;
        System.out.println("Nombre d'exemplaires du livre: ");
        if (scan.hasNextLine()) {
            nbexemplaires = lireNombreExemplaires(scan);
        }
        Livre livre = new Livre(titre, auteur, categorie, nbexemplaires);
        return livre;
    }

    // Méthode qui lit les données d'un membre à la console
    public static Membre lireMembre(Scanner scan) {
        String nom = "";
        System.out.println("Nom membre :");
        if (scan.hasNextLine()) {
            nom = scan.nextLine();
        }
        String prenom = "";
        System.out.println("Prénom membre :");
        if (scan.hasNextLine()) {
            prenom = scan.nextLine();
        }
        String email = "";
        System.out.println("Email :");
        if (scan.hasNextLine()) {
            email = scan.nextLine();
        }
        Date date = null;
        System.out.println("Date d'ahésion (AAAA-MM-JJ) : ");
        if (scan.hasNextLine()) {
            date = lireDate(scan);
        }

        Membre membre = new Membre(nom, prenom, email, date);

        return membre;
    }

    // Méthode qui lit les données d'un membre à la console
    public static Emprunt lireEmprunt(List<Membre> membres, List<Livre> livres, Scanner scan) {
        Emprunt emprunt = new Emprunt();
        String critereMembre = lireNom(scan, "le nom du membre");
        int idxMembre = rechercherMembre(membres, critereMembre);
        if (idxMembre != -1) {
            String critereLivre = lireNom(scan, "le titre du livre");
            int idxLivre = rechercherLivreByTitre(livres, critereLivre);
            if (idxLivre != -1) {
                System.out.println("Date d'emprunt : ");
                Date date_emprunt = lireDate(scan);
                System.out.println("Date de retour prévue : ");
                Date date_retour_prevue = lireDate(scan);
                emprunt = new Emprunt(membres.get(idxMembre).getId(), livres.get(idxLivre).getId(), date_emprunt, date_retour_prevue, null);
            } else {
                System.out.println("Aucun livre ne correspond au titre : " +'"'+ critereLivre+'"');
            }
        } else {
            System.out.println("Aucun membre ne correspond au nom : " +'"'+ critereMembre+'"');
        }

        return emprunt;
    }

    // Méthode permettant d'ajouter un livre dans la bd
    public static void ajouterLivre(LivreDAO livreDAO, Scanner scan) {
        System.out.println("Entrer un livre");
        System.out.println("----------------------------------------------------");
        Livre livre = lireLivre(scan);// ajoute le livre lu à la console
        System.out.println("Livre créé : " + livre);
        try {
            livreDAO.insert(livre);
            System.out.println("Un livre a été ajouté avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion du livre : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Méthode permettant d'ajouter un livre dans la bd
    public static void ajouterMembre(MembreDAO membreDAO, Scanner scan) {
        System.out.println("Entrer un membre");
        System.out.println("----------------------------------------------------");
        Membre membre = lireMembre(scan);// ajoute le membre lu à la console
        try {
            membreDAO.insert(membre);
            System.out.println("Un membre a été ajouté avec succès.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // recherche linéaire de livre suivant le titre
    public static int rechercherLivreByTitre(List<Livre> livres, String titre) {
        int n = livres.size();

        for (int i = 0; i < n; i++) {
            if (livres.get(i).getTitre().equals(titre)) {
                return i;
            }
        }
        return -1;
    }

    // recherche linéaire de livre suivant la catégorie
    public static int rechercherLivreByCategorie(List<Livre> livres, String categorie) {
        int n = livres.size();

        for (int i = 0; i < n; i++) {
            if (livres.get(i).getCategorie().equals(categorie)) {
                return i;
            }
        }
        return -1;
    }

    // recherche linéaire de livre suivant l'auteur
    public static int rechercherLivreByAuteur(List<Livre> livres, String auteur) {
        int n = livres.size();

        for (int i = 0; i < n; i++) {
            if (livres.get(i).getAuteur().equals(auteur)) {
                return i;
            }
        }
        return -1;
    }

    // recherche linéaire de membre suivant le nom
    public static int rechercherMembre(List<Membre> membres, String critere) {
        int n = membres.size();

        for (int i = 0; i < n; i++) {
            if (membres.get(i).getNom().equals(critere)) {
                return i;
            }
        }
        return -1;
    }

    // recherche linéaire d'un emprunt suivant le nom de l'emprunteur et le titre du livre
    public static int rechercherEmprunt(List<Emprunt> emprunts, int idMembre, int idLivre) {
        int n = emprunts.size();

        for (int i = 0; i < n; i++) {
            if ((emprunts.get(i).getMembre_id() == idMembre) && (emprunts.get(i).getLivre_id() == idLivre)) {
                return i;
            }
        }
        return -1;
    }

    // Méthode permettant d'afficher un livre à la console
    public static void afficherDetails(Livre livre) {
        System.out.println("Id: " + livre.getId());
        System.out.println("Titre: " + livre.getTitre());
        System.out.println("Auteur: " + livre.getAuteur());
        System.out.println("Catégorie: " + livre.getCategorie());
        System.out.println("Nombre d'exemplaires: " + livre.getNbexemplaires());
    }

    // Méthode permettant d'afficher un membre à la console
    public static void afficherDetails(Membre membre) {
        System.out.println("Id: " + membre.getId());
        System.out.println("Nom: " + membre.getNom());
        System.out.println("Prénom: " + membre.getPrenom());
        System.out.println("Email: " + membre.getEmail());
        System.out.println("Date d'adhésion: " + membre.getAdhesiondate());
    }

    // Méthode permettant d'afficher un emprunt à la console
    public static void afficherDetails(Emprunt emprunt) {
        System.out.println("Id: " + emprunt.getId());
        System.out.println("Id membre : " + emprunt.getMembre_id());
        System.out.println("Id livre : " + emprunt.getLivre_id());
        System.out.println("Date d'emprunt : " + emprunt.getDate_emprunt());
        System.out.println("Date de retour prévue : " + emprunt.getDate_retour_prevue());
        System.out.println("Date de retour effective : " + emprunt.getDate_retour_effective());
    }

    // Méthode permettant d'afficher tous les livres à la console
    public static void afficherTousLivres(List<Livre> livres) {
        int n = livres.size();
        if (n == 0) {
            System.out.println("La liste des livres est vide.");
        } else {
            System.out.println("*************************************************************");
            System.out.println("********************* Liste des " + n + " livres ********************");
            System.out.println("*************************************************************");
            int i = 1;
            for (Livre livre : livres) {
                System.out.println("Livre n°" + (i++));
                System.out.println("----------------------------------------------------");
                afficherDetails(livre);
                System.out.println("\n");
            }
        }
    }

    // Méthode permettant d'afficher tous les membres à la console
    public static void afficherTousMembres(List<Membre> membres) {
        int n = membres.size();
        if (n == 0) {
            System.out.println("La liste des membres est vide.");
        } else {
            System.out.println("*************************************************************");
            System.out.println("********************* Liste des " + n + " membres *******************");
            System.out.println("*************************************************************");
            int i = 1;
            for (Membre membre : membres) {
                System.out.println("Membre n°" + (i++));
                System.out.println("----------------------------------------------------");
                afficherDetails(membre);
                System.out.println("\n");
            }
        }
    }

    // Méthode permettant d'afficher tous les emprunts à la console
    public static void afficherTousEmprunts(List<Emprunt> emprunts) {
        int n = emprunts.size();
        if (n == 0) {
            System.out.println("La liste des emprunts est vide.");
        } else {
            System.out.println("*************************************************************");
            System.out.println("********************* Liste des " + n + " emprunts ******************");
            System.out.println("*************************************************************");
            int i = 1;
            for (Emprunt emprunt : emprunts) {
                System.out.println("Emprunt n°" + (i++));
                System.out.println("----------------------------------------------------");
                afficherDetails(emprunt);
                System.out.println("\n");
            }
        }
    }

    // Méthode permettant d'afficher tous les emprunts en retard à la console
    public static void afficherTousEmpruntsEnRetard(List<Emprunt> emprunts) {
        int n = emprunts.size();
        if (n == 0) {
            System.out.println("La liste des emprunts en retard est vide.");
        } else {
            System.out.println("*************************************************************");
            System.out.println("************ Liste des " + n + " emprunts en retard **************");
            System.out.println("*************************************************************");
            int i = 1;
            for (Emprunt emprunt : emprunts) {
                System.out.println("Emprunt n°" + (i++));
                System.out.println("----------------------------------------------------");
                afficherDetails(emprunt);
                calculerPenalite(emprunt.getDate_retour_prevue(), emprunt.getDate_retour_effective());
                /*
                // Conversion en LocalDate
                LocalDate dateDebut = emprunt.getDate_retour_prevue().toLocalDate();
                LocalDate dateFin = emprunt.getDate_retour_effective().toLocalDate();

                // Calcul de la période de retard
                Period periode = Period.between(dateDebut, dateFin);

                // Extraction du nombre de jours de retard
                int nombreJours = periode.getDays();
                if(nombreJours > 0){
                    System.out.println("La pénalité est de : "+nombreJours*100+" FCFA");
                }
                 */
                System.out.println("\n");
            }
        }
    }

    // effacer la console
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // afficher le menu
    public static void afficherMenuGeneral() {
        System.out.println("**************************************************");
        System.out.println("*************          MENU          *************");
        System.out.println("**************************************************");
        String[] menu = {
                "1-  Ajouter un livre",
                "2-  Rechercher un livre",
                "3-  Modifier un livre",
                "4-  Supprimer un livre",
                "5-  Afficher tous les livres disponibles",
                "6-  Inscrire un nouveau membre",
                "7-  Rechercher un membre par son nom",
                "8-  Modifier un membre",
                "9-  Supprimer un membre",
                "10- Afficher tous les membres",
                "11- Enregistrer un emprunt",
                "12- Rechercher un emprunt",
                "13- Modifier un emprunt",
                "14- Supprimer un emprunt",
                "15- Afficher tous les emprunts",
                "16- Gérer le retour d'un livre",
                "17- Afficher les emprunts en retard",
                "18- Quitter"
        };
        for (String str : menu) {
            System.out.println("         " + str + "          ");
        }
    }

    // afficher le critère de recherche
    // si menu=2
    public static void afficherMenuCritere() {
        String[] critere = {
                "1- Par titre de livre",
                "2- Par catégorie",
                "3- Par auteur"
        };
        for (String str : critere) {
            System.out.println("         " + str + "          ");
        }
    }

    public static void calculerPenalite(Date datePrevue, Date DateEffective){
        // Conversion en LocalDate
        LocalDate dateDebut = datePrevue.toLocalDate();
        LocalDate dateFin = DateEffective.toLocalDate();

        // Calcul de la période de retard
        Period periode = Period.between(dateDebut, dateFin);

        // Extraction du nombre de jours de retard
        int nombreJours = periode.getDays();
        if(nombreJours > 0){
            System.out.println("La pénalité est de : "+nombreJours*100+" FCFA");
        }
    }
}