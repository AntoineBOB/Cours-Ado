package com.cours_ado.coursado;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by laura on 02/05/2016.
 */
public class Bilan implements Parcelable {
    int id;
    String dateSeance,startSeance,dureeSeance, endSeance, themesAbordes,commentaire;

    public Bilan() {
    }

    public Bilan(String dateSeance, int id, String startSeance, String dureeSeance, String endSeance, String themesAbordes, String commentaire) {
        this.dateSeance = dateSeance;
        this.id = id;
        this.startSeance = startSeance;
        this.dureeSeance = dureeSeance;
        this.endSeance = endSeance;
        this.themesAbordes = themesAbordes;
        this.commentaire = commentaire;
    }

    protected Bilan(Parcel in) {
        id = in.readInt();
        dateSeance = in.readString();
        startSeance = in.readString();
        dureeSeance = in.readString();
        endSeance = in.readString();
        themesAbordes = in.readString();
        commentaire = in.readString();
    }

    public static final Creator<Bilan> CREATOR = new Creator<Bilan>() {
        @Override
        public Bilan createFromParcel(Parcel in) {
            return new Bilan(in);
        }

        @Override
        public Bilan[] newArray(int size) {
            return new Bilan[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateSeance() {
        return dateSeance;
    }

    public void setDateSeance(String dateSeance) {
        this.dateSeance = dateSeance;
    }

    public String getStartSeance() {
        return startSeance;
    }

    public void setStartSeance(String startSeance) {
        this.startSeance = startSeance;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(dateSeance);
        dest.writeString(startSeance);
        dest.writeString(dureeSeance);
        dest.writeString(endSeance);
        dest.writeString(themesAbordes);
        dest.writeString(commentaire);

    }
}
