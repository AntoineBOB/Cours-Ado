package com.cours_ado.coursado;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SaisirBillet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisir_billet);

        Button boutonRetour = (Button) findViewById(R.id.buttonRetourSaisieBillet);
        boutonRetour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        Button boutonSaisie = (Button) findViewById(R.id.button2);
        boutonSaisie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText codeBarre = (EditText) findViewById(R.id.editText);
                String valeurCodeBarre = codeBarre.getText().toString().trim();

                if(!valeurCodeBarre.isEmpty()){
                    try {
                        ConnectivityManager aConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo aNetworkInfo = aConnectivityManager.getActiveNetworkInfo();
                        if (aNetworkInfo != null && aNetworkInfo.isConnected()) {
                            submitTicket(valeurCodeBarre);
                        } else {
                            Toast.makeText(SaisirBillet.this, "Aucune connexion Internet", Toast.LENGTH_LONG).show();
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
    public void submitTicket(String codeBarre) throws IOException {
        new myDownloadTask(codeBarre).execute();
    }
    public class myDownloadTask extends AsyncTask<Void,String,Void> {

        private String codeBarre;

        public myDownloadTask(String codeBarre){
            this.codeBarre=codeBarre;
        }

        @Override
        protected Void doInBackground(Void... params) {


            try {
                try {
                    String stringurl = AppConfig.URL_SaisirTicket+"?codeBarre="+this.codeBarre;
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
                            //On transforme ces données en Objet json
                            JSONObject jsonObject = new JSONObject(result.toString());
                            publishProgress(jsonObject.getString("message"));
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
                Toast.makeText(SaisirBillet.this,para,Toast.LENGTH_LONG).show();
            }
        }
    }
}
