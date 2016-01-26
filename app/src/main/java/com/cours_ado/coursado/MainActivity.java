package com.cours_ado.coursado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boutonProfesseur = (Button) findViewById(R.id.buttonProfesseur);
        boutonProfesseur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent connexionProfesseur = new Intent(MainActivity.this,espaceConnexionProfesseur.class);
                startActivity(connexionProfesseur);
                MainActivity.this.finish();
            }
        });
        Button boutonEleve = (Button) findViewById(R.id.buttonEleve);

    }
}
