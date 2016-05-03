package com.cours_ado.coursado;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.List;

public class listeBilans extends AppCompatActivity {
    private ListView listeViewBilan;
    public String id_inscription_prof;
    public int idProf,idEleve,idInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_bilans);
        //on recupere les donnes passer dans l'intent.
        id_inscription_prof=getIntent().getExtras().getString("id_inscription_prof");
        idProf=getIntent().getExtras().getInt("idProf");
        idEleve=getIntent().getExtras().getInt("idEleve");
        idInscription=getIntent().getExtras().getInt("idInscription");
        CreateListe(idProf, idEleve,id_inscription_prof,idInscription);
        //on gere le bouton retour
        Button boutonRetour = (Button) findViewById(R.id.buttonRet);
        boutonRetour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        //on gere le clic sur un bilan
        this.listeViewBilan = (ListView) findViewById(R.id.listViewBilan);
        listeViewBilan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //on prend le bilan sur lequel on a cliquer
                Bilan b = (Bilan) listeViewBilan.getItemAtPosition(position);
                //on l'ajoute en extra dans l'intent modifier
                Intent bilan = new Intent(listeBilans.this, modifierBilan.class);
                bilan.putExtra("monBilan", b);
                startActivity(bilan);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        CreateListe(idProf, idEleve,id_inscription_prof,idInscription);

    }


    private void CreateListe(int idProf, int idEleve,String id_inscription_prof,int idInscription){
        //methode pour appelrer l'asyncTask
        ListeBilanTask task = new ListeBilanTask(idProf, idEleve,id_inscription_prof,idInscription);
        task.execute();


    }


    private class ListeBilanTask extends AsyncTask<Void,Void,List<Bilan>> {
        private int idProf;
        private int idEleve,idInscription;
        private String id_inscription_prof;

        public ListeBilanTask(int idProf, int idEleve,String  id_inscription_prof, int idInscription){
            //champ recquis pour script php
            this.idProf=idProf; this.idEleve=idEleve;
            this.id_inscription_prof=id_inscription_prof; this.idInscription=idInscription;
        }

        @Override
        protected List<Bilan> doInBackground(Void... params) {
            ArrayList<Bilan> dataBilan = new ArrayList<>();
            try {
                try {
                    URL url = new URL(AppConfig.URL_ListeBilans+"?idProf="+this.idProf+"&idEleve="+this.idEleve+"&idInscription="+this.idInscription+"&idInscriptionProf="+this.id_inscription_prof);
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
                            JSONArray jsonArray = json.getJSONArray("Bilans");
                            for(int b=0;b<jsonArray.length();b++){

                                //on renregistre les bilans un par un.
                                JSONObject json_data =jsonArray.getJSONObject(b);
                                Bilan bi= new Bilan();
                                bi.setId(json_data.getInt("id"));
                                bi.setDateSeance(json_data.getString("dateSeance"));
                                bi.setStartSeance(json_data.getString("startSeance"));
                                bi.setDureeSeance(json_data.getString("dureeSeance"));
                                bi.setEndSeance(json_data.getString("endSeance"));
                                bi.setThemesAbordes(json_data.getString("themesAbordes"));
                                bi.setCommentaire(json_data.getString("commentaires"));
                                dataBilan.add(bi);

                            }

                        } catch (JSONException e ) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(listeBilans.this, "L'adresse n'est pas la bonne", Toast.LENGTH_LONG).show();
                    }
                    urlConnection.disconnect();
                } catch (MalformedURLException e ) {
                    e.printStackTrace();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            return dataBilan;
        }

        protected void onPostExecute(List<Bilan> dataBilan)
        {
            if (dataBilan != null){
                listeBilans.this.updateListe(dataBilan);
            }else {
                Toast.makeText(listeBilans.this,"Erreur de récupération des données",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateListe(List<Bilan> bilan) {
        BilanAdapter adapter = new BilanAdapter(this,bilan);
        this.listeViewBilan.setAdapter(adapter);

    }
    private class BilanAdapter extends ArrayAdapter<Bilan> {
        public BilanAdapter(Context contexte, List<Bilan> bilan){
            super(contexte, R.layout.activity_list_item_bilan,R.id.textDate,bilan);

        }
        public View getView (int position, View convertView, ViewGroup parent){

            // on laisse ArrayAdapter faire le boulot de création de la view
            View ret = super.getView(position, convertView, parent);
            // on va juste mettre ici les bonnes données au bon endroit
            Bilan bilan = getItem(position);
            TextView dateBilan=(TextView) ret.findViewById(R.id.textDate);
            dateBilan.setText("Séance du "+bilan.getDateSeance());
            TextView debutFin=(TextView) ret.findViewById(R.id.textDebutFin);
            debutFin.setText("de "+bilan.getStartSeance()+" à "+bilan.getEndSeance());




            return ret;
        }
    }
}
