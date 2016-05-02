package com.cours_ado.coursado;

/**
 * Created by laura on 14/04/2016.
 */
public class Cours {
    //classe cours
    private int id;
    private String numInscription;
    private String nbHeures;
    private String matiere;
    private String ville;
    private String nbTickets;
    private String heure_cadence;
    private String niveau;

    public Cours(String numInscription, String nbHeures){
        this.numInscription=numInscription;
        this.nbHeures=nbHeures;

    }
    public Cours()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getNbTickets() {
        return nbTickets;
    }

    public void setNbTickets(String nbTickets) {
        this.nbTickets = nbTickets;
    }

    public String getHeure_cadence() {
        return heure_cadence;
    }

    public void setHeure_cadence(String heure_cadence) {
        this.heure_cadence = heure_cadence;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    public void setNumInscription (String numInscription){
        this.numInscription=numInscription;
    }

    public String getNumInscription() {
        return numInscription;
    }

    public void setNbHeures(String nbHeures) {
        this.nbHeures = nbHeures;
    }

    public String getNbHeures() {
        return nbHeures;
    }
}
