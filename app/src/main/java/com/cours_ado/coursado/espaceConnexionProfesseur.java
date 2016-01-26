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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class espaceConnexionProfesseur extends AppCompatActivity {

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
                            Toast.makeText(espaceConnexionProfesseur.this, "La connexion Internet a été perdue", Toast.LENGTH_LONG).show();
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
        new myDownloadTask().execute();
    }
    public class myDownloadTask extends AsyncTask<Void,String,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            try {
                try {
                    URL url = new URL(AppConfig.URL_Login);
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
                                JSONObject jsonObject = new JSONObject(result.toString());
                                if(jsonObject.getString("error").equals("false")){
                                    String nomUtilisateur = jsonObject.getString("nom");
                                    String prenomUtilisateur = jsonObject.getString("prenom");
                                    publishProgress("Bonjour "+ nomUtilisateur+" "+prenomUtilisateur);
                                }
                            } catch (JSONException e ) {
                                e.printStackTrace();
                            }
                        }
                    else{
                            Toast.makeText(espaceConnexionProfesseur.this,"L'adresse n'est pas la bonne",Toast.LENGTH_LONG).show();
                        }
                    urlConnection.disconnect();
                } catch (MalformedURLException  e ) {
                    e.printStackTrace();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... param){
            TextView tv = (TextView) findViewById(R.id.textView3);
            for(String para : param){
                tv.setText(para);
            }
        }
    }

}
