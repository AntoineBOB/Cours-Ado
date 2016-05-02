package com.cours_ado.coursado;

/**
 * Created by laura on 14/04/2016.
 */
public class Eleve {
    //classe eleve
    private int id;
    private String nom;
    private String prenom;


    public Eleve(int id, String nom, String prenom){
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;

    }
    public Eleve()
    {

    }
    public void setId (int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }
}
