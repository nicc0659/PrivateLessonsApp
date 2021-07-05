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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import model.Corsi;

public class CorsiActivity extends AppCompatActivity {

    AsyncHttpClient client;
    CorsiAdapter list = null;
    String MYURL = "http://192.168.1.2:8080/ProgettoTWeb_2021Back_war_exploded/servletcorsi";
    ArrayList<Corsi> corsi = new ArrayList<>();
    String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corsi);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        Intent i=getIntent();
        token=i.getStringExtra("token");
        ListView lv = (ListView) findViewById(R.id.listViewDemo);
        client = new AsyncHttpClient();
        client.get(MYURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Corsi>>() {}.getType(); //Crea un tipo che definisce una lista dell'oggetto che preferisci
                    corsi = gson.fromJson(response.toString(), listType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                list = new CorsiAdapter(getApplicationContext(),R.layout.rowcustom, corsi); //L'adapter, ti permette di personalizzare/prendere gli elementi di ogni singola riga
                lv.setAdapter(list);
            }
        });
    }
    public class CorsiAdapter extends ArrayAdapter<Corsi>{
        public CorsiAdapter(Context context, int textViewResourceId,
                            ArrayList<Corsi> objects) {
            super(context, textViewResourceId, objects);
        }
        @SuppressLint({"ViewHolder", "SetTextI18n"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rowcustom, null);
            TextView materia = (TextView)convertView.findViewById(R.id.textViewMat);
            TextView prenotazioni_id = (TextView) convertView.findViewById(R.id.textViewProva);
            TextView prenotazioni_materia = (TextView) convertView.findViewById(R.id.textViewMateria);
            TextView prenotazioni_giorno = (TextView) convertView.findViewById(R.id.textViewGiorno);
            TextView prenotazioni_orario = (TextView) convertView.findViewById(R.id.textViewOrarioIn);
            TextView prenotazioni_stato = (TextView) convertView.findViewById(R.id.textViewStato);
            prenotazioni_stato.setVisibility(View.GONE);
            prenotazioni_id.setVisibility(View.GONE);
            prenotazioni_materia.setVisibility(View.GONE);
            prenotazioni_giorno.setVisibility(View.GONE);
            prenotazioni_orario.setVisibility(View.GONE);
            Button button = (Button)convertView.findViewById(R.id.button);
            button.setText("Avanti");
            Button button_del = (Button)convertView.findViewById(R.id.buttonConferma);
            button_del.setVisibility(View.GONE);
            Corsi c = getItem(position);
            materia.setText(c.getMateria());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), ProfActivity.class);
                    i.putExtra("materia", materia.getText());
                    i.putExtra("token", token);
                    getContext().startActivity(i);
                }
            });
            return convertView;
        }
    }
}