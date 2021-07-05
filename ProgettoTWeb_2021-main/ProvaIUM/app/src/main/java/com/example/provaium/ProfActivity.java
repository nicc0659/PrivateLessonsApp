package com.example.provaium;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import model.Docenti;

public class ProfActivity extends AppCompatActivity {

    AsyncHttpClient client;
    ProfAdapter list = null;
    String MYURL = "http://192.168.1.2:8080/ProgettoTWeb_2021Back_war_exploded/servletdoc";
    ArrayList<Docenti> docenti = new ArrayList<>();
    RequestParams params;
    String token = null;
    String materia = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        Intent i=getIntent();
        materia=i.getStringExtra("materia");
        token=i.getStringExtra("token");
        ListView lv = (ListView) findViewById(R.id.listViewProf);
        params = new RequestParams();
        params.put("materia", materia);
        params.put("token", token);
        client = new AsyncHttpClient();
        client.post(MYURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Docenti>>() {}.getType(); //Crea un tipo che definisce una lista dell'oggetto che preferisci
                    docenti = gson.fromJson(response.toString(), listType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                list = new ProfAdapter(getApplicationContext(),R.layout.rowcustom, docenti); //L'adapter, ti permette di personalizzare/prendere gli elementi di ogni singola riga
                lv.setAdapter(list);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                if(statusCode == 401) {
                    Toast.makeText(ProfActivity.this, "Tempo scaduto! " + statusCode, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ProfActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    public class ProfAdapter extends ArrayAdapter<Docenti> {
        public ProfAdapter(Context context, int textViewResourceId,
                           ArrayList<Docenti> objects) {
            super(context, textViewResourceId, objects);
        }
        int id_docente = 0;
        @SuppressLint({"ViewHolder", "SetTextI18n"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rowcustom, null);
            TextView docenti = (TextView) convertView.findViewById(R.id.textViewMat);
            TextView docenti2 = (TextView) convertView.findViewById(R.id.textViewProva);
            Button button = (Button) convertView.findViewById(R.id.button);
            button.setText("Avanti");
            Button button_del = (Button)convertView.findViewById(R.id.buttonConferma);
            button_del.setVisibility(View.GONE);
            TextView prenotazioni_materia = (TextView) convertView.findViewById(R.id.textViewMateria);
            TextView prenotazioni_giorno = (TextView) convertView.findViewById(R.id.textViewGiorno);
            TextView prenotazioni_orario = (TextView) convertView.findViewById(R.id.textViewOrarioIn);
            TextView prenotazioni_stato = (TextView) convertView.findViewById(R.id.textViewStato);
            prenotazioni_stato.setVisibility(View.GONE);
            prenotazioni_materia.setVisibility(View.GONE);
            prenotazioni_giorno.setVisibility(View.GONE);
            prenotazioni_orario.setVisibility(View.GONE);
            Docenti d = getItem(position);
            id_docente = d.getId();
            docenti.setText(d.getNome());
            docenti2.setText(String.valueOf(d.getId()));
            docenti2.setVisibility(View.GONE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), RipetizioniActivity.class);
                    i.putExtra("docente", docenti.getText());
                    i.putExtra("id_docente", docenti2.getText());
                    i.putExtra("token", token);
                    i.putExtra("materia", materia);
                    getContext().startActivity(i);
                }
            });
            return convertView;
        }
    }
}