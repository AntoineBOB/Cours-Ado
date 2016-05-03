package com.cours_ado.coursado;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class modifierBilan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_bilan);
        final Bilan bilan =getIntent().getExtras().getParcelable("monBilan");
        //on ajoute les valeurs non modifie du bilan
        final EditText theme=(EditText) findViewById(R.id.editTextTheme);
        final EditText commentaire=(EditText) findViewById(R.id.editTextCommentaire);
        theme.setText(bilan.getThemesAbordes());
        commentaire.setText(bilan.getCommentaire());
        //gerer le bouton retour
        Button boutonRetour = (Button) findViewById(R.id.buttonRetourBilan);
        boutonRetour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        //gerer le bouton update
        Button boutonModififer=(Button) findViewById(R.id.buttonModifierBilan);
        boutonModififer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                bilan.setCommentaire(commentaire.getText().toString());
                bilan.setThemesAbordes(theme.getText().toString());
                Update(bilan.getId(),bilan.getCommentaire(),bilan.getThemesAbordes());
            }
        });

    }

    public void Update(int id, String commentaire,String theme){
        new UpdateBilan(id,theme,commentaire).execute();
    }

    private class UpdateBilan extends AsyncTask<Void,String,Void>{
        private int id;
        private String theme,commentaire;
        private UpdateBilan(int id,String theme, String commentaire){
            this.id=id;
            this.theme=theme.replace(" ","_");
            this.commentaire=commentaire.replace(" ","_");
        }
        protected Void doInBackground(Void... params) {
            try {
                try {
                    String stringurl = AppConfig.URL_UpdateBilan + "?id=" + this.id + "&theme=" + this.theme + "&commentaire=" + this.commentaire;
                    URL url = new URL(stringurl);
                    //On ouvre la connexion
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream in = url.openStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        try {
                            JSONObject json = new JSONObject(result.toString());
                            publishProgress(json.getString("message"));
                        }
                        catch (JSONException e ) {
                            e.printStackTrace();
                        }
                        }
                    else{
                        publishProgress("L'adresse n'est pas la bonne");
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

        protected void onProgressUpdate(String... param){
            for(String para : param){
                Toast.makeText(modifierBilan.this,para,Toast.LENGTH_LONG).show();
            }
        }
    }
}
