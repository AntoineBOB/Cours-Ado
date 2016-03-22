package com.cours_ado.coursado;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class espaceConnexionProfesseur extends AppCompatActivity {
    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_connexion_professeur);

        Button boutonConnexionProf = (Button) findViewById(R.id.BoutonConnexionProfesseur);
        boutonConnexionProf.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //EditText de l'email et du mot de passe
                EditText emailProfEdit = (EditText) findViewById(R.id.EmailProf);
                EditText pwProfEdit = (EditText) findViewById(R.id.PasswordProf);

                //Valeur récupérées dans les EditText
                final String emailProf = emailProfEdit.getText().toString().trim();
                final String pwProf = pwProfEdit.getText().toString().trim();

                if (!emailProf.isEmpty() && !pwProf.isEmpty()) {
                    try {
                        ConnectivityManager aConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo aNetworkInfo = aConnectivityManager.getActiveNetworkInfo();
                        if (aNetworkInfo != null && aNetworkInfo.isConnected()) {
                            checkLogin(emailProf, pwProf);
                        } else {
                            Toast.makeText(espaceConnexionProfesseur.this, "Aucune connexion Internet", Toast.LENGTH_LONG).show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Veuillez entrez vos identifiants et mot de passe", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void checkLogin(String email, String mdp) throws IOException {
        new myDownloadTask(mdp,email).execute();
    }
    public class myDownloadTask extends AsyncTask<Void,String,Void>{

        private String mdp;
        private String user;


        public myDownloadTask(String mdp, String user){
            this.mdp=mdp;
            this.user=user;
        }

        @Override
        protected Void doInBackground(Void... params) {


            try {
                try {
                    String stringurl = AppConfig.URL_Login+"?email="+this.user+"&mdp="+this.mdp;
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
                            //publishProgress(this.user);
                            try {
                                //On transforme ces données en Objet json et on verifie si il y a une erreur
                                JSONObject jsonObject = new JSONObject(result.toString());
                                if(jsonObject.getString("error").equals("false")){
                                    String nomUtilisateur = jsonObject.getString("nom");
                                    String prenomUtilisateur = jsonObject.getString("prenom");
                                    int idUilisateur = jsonObject.getInt("id");
                                    publishProgress("Bonjour " + nomUtilisateur + " " + prenomUtilisateur);
                                    Intent intent = new Intent(espaceConnexionProfesseur.this,choixProfesseur.class);
                                    intent.putExtra(id,idUilisateur);

                                    startActivity(intent);
                                }
                                else{
                                    publishProgress(jsonObject.getString("error_msg"));
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
            return null;
        }

        @Override
        protected void onProgressUpdate(String... param){
            for(String para : param){
                Toast.makeText(espaceConnexionProfesseur.this,para,Toast.LENGTH_LONG).show();
            }
        }
    }

}
