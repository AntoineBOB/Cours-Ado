package com.cours_ado.coursado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class choixProfesseur extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_professeur);
        Button boutonChoixListeEleve = (Button) findViewById(R.id.buttonListeEleve);
        boutonChoixListeEleve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent listeEleve = new Intent(choixProfesseur.this, listeEleve.class);
                startActivity(listeEleve);
            }

        });

        Button boutonChoixSaisirBillet = (Button) findViewById(R.id.buttonSaisirTicket);
        boutonChoixSaisirBillet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent saisirBillet = new Intent(choixProfesseur.this,SaisirBillet.class);
                startActivity(saisirBillet);
            }

        });
    }
}
