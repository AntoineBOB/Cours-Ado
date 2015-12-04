package com.cours_ado.coursado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
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
                //10.0.2.2
                //EditText de l'email et du mot de passe
                EditText emailProfEdit = (EditText) findViewById(R.id.EmailProf);
                EditText pwProfEdit = (EditText) findViewById(R.id.PasswordProf);

                //Valeur récupérées dans les EditText
                String emailProf = emailProfEdit.getText().toString().trim();
                String pwProf = pwProfEdit.getText().toString().trim();

                if (!emailProf.isEmpty() && !pwProf.isEmpty()) {
                    // login user
                    try {
                        checkLogin(emailProf, pwProf);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Veuillez entrez vos identifiants et mot de passe", Toast.LENGTH_LONG).show();
                }
                //Si la connexion est OK
                Intent connexionProfesseurApprouvée = new Intent(espaceConnexionProfesseur.this,choixProfesseur.class);
                startActivity(connexionProfesseurApprouvée);
            }
        });
    }
    public void checkLogin(String Email, String mdp) throws IOException {
        URL url_Login = new URL(AppConfig.URL_Login);
        HttpURLConnection Conn = (HttpURLConnection) url_Login.openConnection();
        Conn.setDoOutput(true);
        //OutputStream paramètres = new DataOutputStream();
    }
}
