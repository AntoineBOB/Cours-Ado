package com.cours_ado.coursado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class espaceConnexionProfesseur extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_connexion_professeur);
        Button boutonConnexionProf = (Button) findViewById(R.id.BoutonConnexionProfesseur);
        boutonConnexionProf.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent connexionProfesseurApprouvée = new Intent(espaceConnexionProfesseur.this,choixProfesseur.class);
                startActivity(connexionProfesseurApprouvée);
                espaceConnexionProfesseur.this.finish();
            }
        });
    }
}
