package com.cours_ado.coursado;

/**
 * Created by laura on 02/05/2016.
 */
public class Bilan {
    int id;
    String DateSeance,StartSeance,dureeSeance, endSeance, themesAbordes,commentaire;

    public Bilan() {
    }

    public Bilan(String dateSeance, int id, String startSeance, String dureeSeance, String endSeance, String themesAbordes, String commentaire) {
        DateSeance = dateSeance;
        this.id = id;
        StartSeance = startSeance;
        this.dureeSeance = dureeSeance;
        this.endSeance = endSeance;
        this.themesAbordes = themesAbordes;
        this.commentaire = commentaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateSeance() {
        return DateSeance;
    }

    public void setDateSeance(String dateSeance) {
        DateSeance = dateSeance;
    }

    public String getStartSeance() {
        return StartSeance;
    }

    public void setStartSeance(String startSeance) {
        StartSeance = startSeance;
    }

    public String getDureeSeance() {
        return dureeSeance;
    }

    public void setDureeSeance(String dureeSeance) {
        this.dureeSeance = dureeSeance;
    }

    public String getEndSeance() {
        return endSeance;
    }

    public void setEndSeance(String endSeance) {
        this.endSeance = endSeance;
    }

    public String getThemesAbordes() {
        return themesAbordes;
    }

    public void setThemesAbordes(String themesAbordes) {
        this.themesAbordes = themesAbordes;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

}
