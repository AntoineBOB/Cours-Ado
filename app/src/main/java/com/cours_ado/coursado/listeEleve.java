package com.cours_ado.coursado;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_eleve);

        this.listeView = (ListView) findViewById(R.id.listView);
        CreateListe();
    }
    private void CreateListe(){
        ListeElevTask task = new ListeElevTask();
        task.execute();


    }
    private class ListeElevTask extends AsyncTask<Void,Void,List<Eleve>>
    {
        // la fonction qui sera effectuée en "background"
        @Override
        protected List<Eleve> doInBackground(Void ... params)
        {
            try {
                ArrayList<Eleve> eleves = new ArrayList<Eleve>();
                // pour cette étape nous conservons les "mock-data"
            /*eleves.add(new Eleve(1,"Test 1","laura"));
            eleves.add(new Eleve(2,"Test 2","Bob"));
            eleves.add(new Eleve(3,"Test 3","Geralt"));
            eleves.add(new Eleve(4, "Test 4", "Sora"));
            return eleves;*/
            // Etape 1: on récupère les données sous forme de String
                //String strData = getStringResult("http://10.0.2.2/Cours-Ado/listeEleve.php");
                // Etape 2: on traduit en JSON
                //JSONObject jsonData = new JSONObject(strData);
                //parseListeEleves(jsonData, eleves);
                return eleves;
            } catch (Exception ex) {
                Log.e("ListeElevTask", "Erreur de récupération des données !", ex);
                return null;

            }

        }


        protected void onPostExecute(List<Eleve> eleves)
        {
            if (eleves != null){
                listeEleve.this.updateListe(eleves);
            }else {
                Toast.makeText(listeEleve.this,"Erreur de récupération des données",Toast.LENGTH_LONG).show();
            }
        }

        private void parseListeEleves(JSONObject data, ArrayList<Eleve> eleves) throws JSONException
        {
            JSONArray jsonArray = data.getJSONArray("liste");
            for (int i = 0; (i < jsonArray.length()); ++i) {
                JSONObject jsonPays = jsonArray.getJSONObject(i);
                Eleve eleve = new Eleve();
                parseEleve(jsonPays, eleve);
                eleves.add(eleve);
            }
        }

        // encore une fois on sépare la fonction, toute modification de "Pays" ne nécessitera alors que la modification de cette fonction-ci.
        // a noter que dans l'idéal, la classe "Pays" pourrait fournir elle-même la fonction de parsing depuis un objet JSON.
        private void parseEleve(JSONObject data, Eleve eleve) throws JSONException
        {
            eleve.setNom(data.getString("nom"));
            eleve.setPrenom(data.getString("prenom"));
        }
    }
    private void updateListe(List<Eleve> eleves){
        EleveAdapter adapter = new EleveAdapter(this,eleves);
        this.listeView.setAdapter(adapter);

    }
    private static class EleveAdapter extends ArrayAdapter<Eleve>{
        public EleveAdapter(Context contexte, List<Eleve> eleves){
            super(contexte, R.layout.activity_list_item,R.id.nom,eleves);

        }
        public View getView (int position, View convertView, ViewGroup parent){

            // on laisse ArrayAdapter faire le boulot de création de la view
            View ret = super.getView(position,convertView,parent);
            // on va juste mettre ici les bonnes données au bon endroit
            Eleve eleve = getItem(position);
            TextView nameView = (TextView) ret.findViewById(R.id.nom);
            nameView.setText(eleve.getNom());

            TextView prenomView = (TextView) ret.findViewById(R.id.prenom);
            prenomView.setText(eleve.getPrenom());

            return ret;
        }
    }

}
