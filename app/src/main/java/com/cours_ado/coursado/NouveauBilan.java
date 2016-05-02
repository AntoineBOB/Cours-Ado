package com.cours_ado.coursado;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NouveauBilan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_bilan);

        final String id_inscription_prof=getIntent().getExtras().getString("id_inscription_prof");
        final int idProf = getIntent().getExtras().getInt("idprof");
        final int idEleve = getIntent().getExtras().getInt("idEleve");


        Button boutonRetour = (Button) findViewById(R.id.buttonRetour);
        boutonRetour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


        //On complete les listes
        new HorairesTask(id_inscription_prof,idProf).execute();


        Button boutonAjoutBilan = (Button) findViewById(R.id.buttonAjouterBilan);
        boutonAjoutBilan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(NouveauBilan.this,"En cours de développement",Toast.LENGTH_LONG).show();
                new AjoutBilanTask(id_inscription_prof,idProf);
            }
        });
    }
    public class HorairesTask extends AsyncTask<Void,String,List<String>> {

        private String idInscription;
        private int idProf;

        public HorairesTask(String idInscription, int idProf){
            this.idInscription=idInscription;
            this.idProf=idProf;
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> listeDate = new ArrayList<>();

            try {
                try {
                    String stringurl = AppConfig.URL_Horaires+"?idInscription="+this.idInscription+"&idProf="+this.idProf;
                    URL url = new URL(stringurl);
                    //On ouvre la connexion
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                        //On récupère les données renvoyées par le script
                        InputStream in = url.openStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        try {
                            //On transforme ces données en Objet json et on verifie si il y a une erreur
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if(jsonObject.getString("reponse").equals("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("date_debut");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json_data = jsonArray.getJSONObject(i);

                                    //Modification format de la date
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");
                                    Date testDate = null;
                                    try {
                                        testDate = sdf.parse(json_data.getString("date_deb"));
                                    }catch(Exception ex){
                                        ex.printStackTrace();
                                    }
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
                                    String newFormat = formatter.format(testDate);
                                    //Ajout de la date formatée dans le fichier
                                    listeDate.add(newFormat);
                                }
                            }
                            else{
                                publishProgress("Une date de premier cours est nécessaire pour le suivi de l'inscription");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    urlConnection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            return listeDate;
        }

        protected void onPostExecute(List<String> result) {
            Spinner spinnerdate = (Spinner) findViewById(R.id.spinner);
            Spinner spinnerHeures = (Spinner) findViewById(R.id.spinner2);
            Spinner spinnerDuree = (Spinner) findViewById(R.id.spinner3);
            List<String> listeHeures = new ArrayList<>();
            List<String> listeDuree = new ArrayList<>();
            for(int i =8;i<20;i++){
                for(int j=0;j<4;j++){
                    String heure;
                    if(j!=0) {
                        heure = String.valueOf(i) + ':' + String.valueOf(j * 15);
                    }
                    else{
                        heure = String.valueOf(i)+ ":00";
                    }
                    listeHeures.add(heure);
                }
            }
            listeHeures.add("20:00");

            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++){
                    String duree;
                    if (j != 0) {
                        duree = String.valueOf(i) + ':' + String.valueOf(j * 15);
                    } else {
                        duree = String.valueOf(i) + ":00";
                    }
                    listeDuree.add(duree);
                }
            }
            listeDuree.add("4:00");

            if(result!=null){
                ArrayAdapter<String> dataAdapterDate = new ArrayAdapter<>(NouveauBilan.this,android.R.layout.simple_spinner_item, result);
                spinnerdate.setAdapter(dataAdapterDate);

                ArrayAdapter<String> dataAdapterHeure = new ArrayAdapter<>(NouveauBilan.this,android.R.layout.simple_spinner_item, listeHeures);
                spinnerHeures.setAdapter(dataAdapterHeure);

                ArrayAdapter<String> dataAdapterDuree = new ArrayAdapter<>(NouveauBilan.this,android.R.layout.simple_spinner_item, listeDuree);
                spinnerDuree.setAdapter(dataAdapterDuree);
            }
        }

        @Override
        protected void onProgressUpdate(String... param){
            for(String para : param){
                Toast.makeText(NouveauBilan.this,para,Toast.LENGTH_SHORT).show();
            }
        }

    }

    public class AjoutBilanTask extends AsyncTask<Void,String,List<String>> {

        private String idInscription;
        private int idProf;
        private int idInscriptionProf;
        private int idEleve;
        private String dateSeance;
        private int duree;
        private String start;
        private String end;
        private String themes;
        private String commentaire;
        private int idRDV;
        private String dateEnregistrement;

        public AjoutBilanTask(String idInscription, int idProf){
            this.idInscription=idInscription;
            this.idProf=idProf;
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> listeDate = new ArrayList<>();

            try {
                try {
                    String stringurl = AppConfig.URL_AjoutBilan+"?idInscription="+this.idInscription+"&idProf="+this.idProf;
                    URL url = new URL(stringurl);
                    //On ouvre la connexion
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                        //On récupère les données renvoyées par le script
                        InputStream in = url.openStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        try {
                            //On transforme ces données en Objet json et on verifie si il y a une erreur
                            JSONObject jsonObject = new JSONObject(result.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    urlConnection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            return listeDate;
        }

        @Override
        protected void onProgressUpdate(String... param){
            for(String para : param){
                Toast.makeText(NouveauBilan.this,para,Toast.LENGTH_SHORT).show();
            }
        }

    }


}
