package com.cours_ado.coursado;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;

public class listeCours extends AppCompatActivity {
    private ListView listeViewCours;
    private static final int DIALOG_ALERT = 10;
    public static String id_inscription_prof;
    public static int id_inscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_cours);
        //on recupere les extra passer dans l'intent
        final int idProf=getIntent().getExtras().getInt("idProf");
        final int idEleve=getIntent().getExtras().getInt("idEleve");
        String nomEleve=getIntent().getExtras().getString("nomEleve");
        String prenomEleve=getIntent().getExtras().getString("prenomEleve");
        CreateListe(idProf, idEleve);
        //on gere le bouton retour
        Button boutonRetour = (Button) findViewById(R.id.buttonRetour);
        boutonRetour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


        //on ajout du le nom de l'eleve en texte
        TextView text = (TextView) findViewById(R.id.textViewNom);
        text.setText("Liste des inscriptions avec l'éléve : "+nomEleve);
        //on gere le click de la listView
        this.listeViewCours = (ListView) findViewById(R.id.listView2);
        listeViewCours.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, final int position, long itemID) {
                //afichage d'une boite de dialogue au click
                AlertDialog.Builder boite3;
                boite3 = new AlertDialog.Builder(listeCours.this);
                boite3.setTitle("Que voulez-vous faire ?");
                boite3.setMessage("Vous avez cliquer sur une inscription, vous avez le choix entre lire/telecharger ses bilans, ajouter un bilan ou ne rien faire et revenir sur la page des inscriptions.");

                boite3.setPositiveButton("Modifier les bilans", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //passage a l'intent des liste de bilan modifiable
                        Intent listBilan=new Intent(listeCours.this, listeBilans.class);
                        Cours e =(Cours) listeViewCours.getItemAtPosition(position);
                        listBilan.putExtra("idInscription",e.getId());
                        listBilan.putExtra("idProf",idProf);
                        listBilan.putExtra("id_inscription_prof",id_inscription_prof);
                        listBilan.putExtra("idEleve",idEleve);
                        startActivity(listBilan);

                        }
                    }
                    );
                    boite3.setNeutralButton("Ajouter un bilan", new DialogInterface.OnClickListener()

                            {

                    public void onClick(DialogInterface dialog, int which) {
                        //passage à l'intent pour ajouter un bilan
                        Intent intent =new Intent(listeCours.this, NouveauBilan.class);
                        intent.putExtra("id_inscription_prof",id_inscription_prof);
                        intent.putExtra("idprof",idProf);
                        intent.putExtra("idEleve",idEleve);
                        intent.putExtra("idInscription",id_inscription);
                        startActivity(intent);
                    }});

                boite3.setNegativeButton("Ne rien faire", new DialogInterface.OnClickListener() {


                                public void onClick(DialogInterface dialog, int which) {


                                }
                            }

                    );

                    boite3.show();


                }
            });
    }


    private void CreateListe(int idProf, int idEleve){
        //methode qui cree les element de la listeview et qui appel l'asyncTask
        ListeCoursTask task = new ListeCoursTask(idProf, idEleve);
        task.execute();


    }


    private class ListeCoursTask extends AsyncTask<Void,Void,List<Cours>>{
        private int idProf;
        private int idEleve;

        public ListeCoursTask(int idProf, int idEleve){
            //passage des champs qu'on a besoin
            this.idProf=idProf; this.idEleve=idEleve;
        }

        @Override
        protected List<Cours> doInBackground(Void... params) {
            //methode pour appeler le script php et interpreter le resultat donnée
            ArrayList<Cours> dataCours = new ArrayList<>();
            try {
                try {
                    URL url = new URL(AppConfig.URL_ListeCours+"?idProf="+this.idProf+"&idEleve="+this.idEleve);
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
                            JSONArray jsonArray = json.getJSONArray("Cours");
                            for(int b=0;b<jsonArray.length();b++){

                                //on renregistre les cours un par un aussi.
                                JSONObject json_data =jsonArray.getJSONObject(b);
                                id_inscription_prof=json_data.getString("id");
                                Cours cour= new Cours();
                                cour.setId(json_data.getInt("idInscription"));
                                cour.setNumInscription(json_data.getString("num_inscription"));
                                cour.setMatiere(json_data.getString("matiere"));
                                cour.setNbHeures(json_data.getString("nbHeures"));
                                cour.setNbTickets(json_data.getString("nbTickets"));
                                cour.setHeure_cadence(json_data.getString("heure_cadence"));
                                cour.setVille(json_data.getString("ville"));
                                cour.setNiveau(json_data.getString("niveau"));
                                dataCours.add(cour);

                                id_inscription=cour.getId();

                            }

                        } catch (JSONException e ) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(listeCours.this, "L'adresse n'est pas la bonne", Toast.LENGTH_LONG).show();
                    }
                    urlConnection.disconnect();
                } catch (MalformedURLException e ) {
                    e.printStackTrace();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            return dataCours;
        }

        protected void onPostExecute(List<Cours> dataCours)
        {
            if (dataCours != null){
                //on appel l'adapter
                listeCours.this.updateListe(dataCours);
            }else {
                Toast.makeText(listeCours.this,"Erreur de récupération des données",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateListe(List<Cours> cours) {
        CoursAdapter adapter = new CoursAdapter(this,cours);
        this.listeViewCours.setAdapter(adapter);

    }
    private class CoursAdapter extends ArrayAdapter<Cours> {
        public CoursAdapter(Context contexte, List<Cours> cours){
            super(contexte, R.layout.activity_list_item_cours,R.id.numInscription,cours);

        }
        public View getView (int position, View convertView, ViewGroup parent){

            // on laisse ArrayAdapter faire le boulot de création de la view
            View ret = super.getView(position, convertView, parent);
            // on va juste mettre ici les bonnes données au bon endroit
            Cours cour = getItem(position);
            TextView numInscriptionView = (TextView) ret.findViewById(R.id.numInscription);
            numInscriptionView.setText(cour.getNumInscription());
            TextView matiereView = (TextView) ret.findViewById(R.id.matiere);
            matiereView.setText(cour.getMatiere());
            TextView villeView = (TextView) ret.findViewById(R.id.niveau);
            villeView.setText(cour.getNiveau());
            TextView dureeView = (TextView) ret.findViewById(R.id.dureeTicket);
            dureeView.setText("H"+cour.getHeure_cadence());
            TextView heureValView = (TextView) ret.findViewById(R.id.heuresVal);
            heureValView.setText(cour.getNbTickets());
            TextView bilanView = (TextView) ret.findViewById(R.id.bilan);
            bilanView.setText(cour.getNbHeures());




            return ret;
        }
    }
}
