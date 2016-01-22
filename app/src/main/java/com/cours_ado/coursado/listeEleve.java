package com.cours_ado.coursado;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class listeEleve extends AppCompatActivity {
    private ListView listeView;
    public static class Eleve {
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
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_eleve);

        this.listeView = (ListView) findViewById(R.id.listView);
    }
    private void populateListe(){


    }
    private class ListeElevTask extends AsyncTask<Void,Void,List<Eleve>>
    {
        // la fonction qui sera effectuée en "background"
        @Override
        protected List<Eleve> doInBackground(Void ... params)
        {
            ArrayList<Eleve> eleves = new ArrayList<Eleve>();
            // pour cette étape nous conservons les "mock-data"
            eleves.add(new Eleve(1,"Test 1","laura"));
            eleves.add(new Eleve(2,"Test 2","Bob"));
            eleves.add(new Eleve(3,"Test 3","Geralt"));
            eleves.add(new Eleve(4,"Test 4","Sora"));
            return eleves;


        }
        protected void onPostExecute(List<Eleve> eleves)
        {
            if (eleves != null){
                listeEleve.this.updateListe(eleves);
            }else {
                Toast.makeText(listeEleve.this,"Erreur de récupération des données",Toast.LENGTH_LONG).show();
            }
        }
    }
    private void updateListe(List<Eleve> eleves){

    }
    /*private static class EleveAdapter extends ArrayAdapter<Eleve>{
        private NumberFormat popFormat;
        public EleveAdapter(Context contexte, List<Eleve> eleves){
            super(contexte, R.layout.activity_list_item,R.id.);
        }
    }*/

}
