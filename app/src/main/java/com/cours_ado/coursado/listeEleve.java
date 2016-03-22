package com.cours_ado.coursado;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class listeEleve extends AppCompatActivity {

    private ListView listeView;

    public static class Eleve {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_eleve);
        Intent intent = getIntent();
        final int id=intent.getIntExtra(choixProfesseur.message, 0);

        Button boutonRetour = (Button) findViewById(R.id.button);
        boutonRetour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


        this.listeView = (ListView) findViewById(R.id.listView);
        CreateListe(id);
        listeView.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View view, int position,long itemID){
                //on prend l'id de l'eleve sur lequel on a cliquer.
                Eleve e =(Eleve) listeView.getItemAtPosition(position);
                Intent listCours = new Intent(listeEleve.this, listeCours.class);
                listCours.putExtra("idProf",id);
                listCours.putExtra("idEleve",e.getId());
                listCours.putExtra("nomEleve",e.getNom());
                listCours.putExtra("prenomEleve",e.getPrenom());
                startActivity(listCours);


            }
        });
    }
    private void CreateListe(int id){
        ListeElevTask task = new ListeElevTask(id);
        task.execute();


    }
    private class ListeElevTask extends AsyncTask<Void,Void,List<Eleve>>
    {
        private int id;

        public ListeElevTask(int id){
            this.id=id;
        }
        @Override
        protected List<Eleve> doInBackground(Void ... params)
        {

                ArrayList<Eleve> data = new ArrayList<>();

                try {
                    try {
                        URL url = new URL(AppConfig.URL_ListeEleve+"?idProf="+this.id);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                            InputStream in = url.openStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder result = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                result.append(line);
                            }
                            try {
                                JSONObject json = new JSONObject(result.toString());
                                JSONArray jsonArray = json.getJSONArray("eleve");
                                for(int i=0;i<jsonArray.length();i++){

                                    //on enregistre la liste des Eleves un par un.
                                    JSONObject json_data =jsonArray.getJSONObject(i);
                                    Eleve eleve= new Eleve();
                                    eleve.setId(json_data.getInt("id"));
                                    eleve.setNom(json_data.getString("nom"));
                                    eleve.setPrenom(json_data.getString("prenom"));
                                    data.add(eleve);
                                }
                            } catch (JSONException e ) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(listeEleve.this,"L'adresse n'est pas la bonne",Toast.LENGTH_LONG).show();
                        }
                        urlConnection.disconnect();
                    } catch (MalformedURLException e ) {
                        e.printStackTrace();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
                return data;
        }
        protected void onPostExecute(List<Eleve> data)
        {
            if (data != null){
                listeEleve.this.updateListe(data);
            }else {
                Toast.makeText(listeEleve.this,"Erreur de récupération des données",Toast.LENGTH_LONG).show();
            }
        }

    }
    private void updateListe(List<Eleve> eleves) {
        EleveAdapter adapter = new EleveAdapter(this,eleves);
        //CoursAdapter adapter2 =
        this.listeView.setAdapter(adapter);

    }
    private class EleveAdapter extends ArrayAdapter<Eleve>{
        public EleveAdapter(Context contexte, List<Eleve> eleves){
            super(contexte, R.layout.activity_list_item,R.id.nom,eleves);

        }
        public View getView (int position, View convertView, ViewGroup parent){

            // on laisse ArrayAdapter faire le boulot de création de la view
            View ret = super.getView(position, convertView, parent);
            // on va juste mettre ici les bonnes données au bon endroit
            Eleve eleve = getItem(position);
            TextView nameView = (TextView) ret.findViewById(R.id.nom);
            nameView.setText(eleve.getNom()+" "+eleve.getPrenom()+" ");


            return ret;
        }
    }



}
