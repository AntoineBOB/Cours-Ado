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

public class controleTicket extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controle_ticket);

        Button boutonVerif = (Button) findViewById(R.id.buttonControleTicket2);
        boutonVerif.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.insertTicketControle);
                String codeBarre = editText.getText().toString().trim();

                ConnectivityManager aConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo aNetworkInfo = aConnectivityManager.getActiveNetworkInfo();
                if (aNetworkInfo != null && aNetworkInfo.isConnected()) {
                    checkTicket(codeBarre);
                } else {
                    Toast.makeText(controleTicket.this, "Aucune connexion Internet :/", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void checkTicket(String codeBarre) {
        new myDownloadTask(codeBarre).execute();
    }

    public class myDownloadTask extends AsyncTask<Void, String, Void> {
        private String codeBarre;

        public myDownloadTask(String codeBarre) {
            this.codeBarre = codeBarre;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                try {
                    String stringurl = AppConfig.URL_ValidateTicket+"?codeBarre="+this.codeBarre;
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
                                publishProgress(jsonObject.getString("message"));
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
                Toast.makeText(controleTicket.this,para,Toast.LENGTH_LONG).show();
            }
        }
    }
}
