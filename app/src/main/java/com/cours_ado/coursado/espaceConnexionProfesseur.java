package com.cours_ado.coursado;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
                        checkLogin(emailProf, pwProf);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Veuillez entrez vos identifiants et mot de passe", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void checkLogin(String email, String mdp) throws IOException {
        URL url_Login = new URL("http://google.fr/"/*AppConfig.URL_Login*/);
        HttpURLConnection conn = (HttpURLConnection) url_Login.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.connect();
        //conn.getInputStream();
        if( conn.getResponseCode() == HttpURLConnection.HTTP_OK ){
            InputStream is = conn.getInputStream();
            // do something with the data here
            //is.read();
        }else{
            InputStream err = conn.getErrorStream();
            // err may have useful information.. but could be null see javadocs for more information
        }

        /*conn.disconnect();*/
        //Intent inten = new Intent(this,choixProfesseur.class);
        //startActivity(inten);
    }
}
