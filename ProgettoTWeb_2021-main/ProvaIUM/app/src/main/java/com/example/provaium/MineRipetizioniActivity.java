package com.example.provaium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import model.Prenotazioni;
import model.Ripetizioni;

public class MineRipetizioniActivity extends AppCompatActivity {

    AsyncHttpClient client, client2, client3;
    MineRipetizioniAdapter list = null;
    String MYURL = "http://192.168.1.2:8080/ProgettoTWeb_2021Back_war_exploded/servletprenotazioni";
    ArrayList<Prenotazioni> prenotazioni = new ArrayList<>();
    RequestParams params, parametri, parametri_2;
    String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_ripetizioni);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        Intent i=getIntent();
        token=i.getStringExtra("token");
        ListView lv = (ListView) findViewById(R.id.listViewMieRipe);
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        params = new RequestParams();
        params.put("token", token);
        client = new AsyncHttpClient();
        client.post(MYURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Prenotazioni>>() {}.getType(); //Crea un tipo che definisce una lista dell'oggetto che preferisci
                    prenotazioni = gson.fromJson(response.toString(), listType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                list = new MineRipetizioniAdapter(getApplicationContext(),R.layout.rowcustom, prenotazioni); //L'adapter, ti permette di personalizzare/prendere gli elementi di ogni singola riga
                lv.setAdapter(list);
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                if(statusCode == 401) {
                    Intent i = new Intent(MineRipetizioniActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                params = new RequestParams();
                params.put("token", token);
                client = new AsyncHttpClient();
                client.post(MYURL, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Prenotazioni>>() {}.getType(); //Crea un tipo che definisce una lista dell'oggetto che preferisci
                            prenotazioni = gson.fromJson(response.toString(), listType);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        list = new MineRipetizioniAdapter(getApplicationContext(),R.layout.rowcustom, prenotazioni); //L'adapter, ti permette di personalizzare/prendere gli elementi di ogni singola riga
                        lv.setAdapter(list);
                    }
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        if(statusCode == 401) {
                            Intent i = new Intent(MineRipetizioniActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                });
                refreshLayout.setRefreshing(false);
            }
        });
    }
    public class MineRipetizioniAdapter extends ArrayAdapter<Prenotazioni> {
        public MineRipetizioniAdapter(Context context, int textViewResourceId,
                                      ArrayList<Prenotazioni> objects) {
            super(context, textViewResourceId, objects);
        }
        @SuppressLint({"ViewHolder", "SetTextI18n"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rowcustom, null);
            TextView prenotazioni = (TextView) convertView.findViewById(R.id.textViewMat);
            TextView prenotazioni_id = (TextView) convertView.findViewById(R.id.textViewProva);
            TextView prenotazioni_materia = (TextView) convertView.findViewById(R.id.textViewMateria);
            TextView prenotazioni_giorno = (TextView) convertView.findViewById(R.id.textViewGiorno);
            TextView prenotazioni_orario = (TextView) convertView.findViewById(R.id.textViewOrarioIn);
            TextView prenotazioni_stato = (TextView) convertView.findViewById(R.id.textViewStato);
            Button button = (Button) convertView.findViewById(R.id.button);
            button.setText("Disdici");
            Button button2 = (Button) convertView.findViewById(R.id.buttonConferma);
            button2.setText("Effettua");
            Prenotazioni p = getItem(position);
            prenotazioni_id.setText(String.valueOf(p.getId_prof()));
            prenotazioni_materia.setText(new StringBuilder().append(p.getMat()));
            prenotazioni_giorno.setText(new StringBuilder().append(p.getGiorno()));
            prenotazioni_orario.setText(new StringBuilder().append(p.getOrarioIn()));
            prenotazioni_stato.setText(new StringBuilder().append(p.getStato()));

            if (p.getStato().equals("effettuata") || p.getStato().equals("disdetta")) {
                button.setEnabled(false);
                button2.setEnabled(false);
            }

            prenotazioni_stato.setVisibility(View.GONE);
            prenotazioni_id.setVisibility(View.GONE);
            prenotazioni_materia.setVisibility(View.GONE);
            prenotazioni_giorno.setVisibility(View.GONE);
            prenotazioni_orario.setVisibility(View.GONE);
            prenotazioni.setText(new StringBuilder().append(p.getNome()).append(" ").append(p.getMat()).append(" ").append(p.getGiorno()).append(" ").append(p.getOrarioIn()).append(" ").append(p.getStato()).toString()); //Volendo puoi concatenare l'ID con StringBuilder (scrivi una normale concat e risolvi il warning)
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button.setEnabled(false);
                    button2.setEnabled(false);
                    parametri = new RequestParams();
                    parametri.put("token", token);
                    parametri.put("id_prof", prenotazioni_id.getText());
                    parametri.put("mat", prenotazioni_materia.getText());
                    parametri.put("giorno", prenotazioni_giorno.getText());
                    parametri.put("orarioIn", prenotazioni_orario.getText());
                    parametri.put("id_button", 1);
                    client2 = new AsyncHttpClient();
                    client2.get(MYURL, parametri, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            super.onSuccess(statusCode, headers, response);
                            Toast.makeText(getContext(), "Lezione disdetta con successo!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            if(statusCode == 401) {
                                Toast.makeText(getContext(), "Tempo scaduto!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MineRipetizioniActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                        }
                    });
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button.setEnabled(false);
                    button2.setEnabled(false);
                    parametri_2 = new RequestParams();
                    parametri_2.put("token", token);
                    parametri_2.put("id_prof", prenotazioni_id.getText());
                    parametri_2.put("mat", prenotazioni_materia.getText());
                    parametri_2.put("giorno", prenotazioni_giorno.getText());
                    parametri_2.put("orarioIn", prenotazioni_orario.getText());
                    parametri_2.put("id_button", 2);
                    client3 = new AsyncHttpClient();
                    client3.get(MYURL, parametri_2, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            super.onSuccess(statusCode, headers, response);
                            Toast.makeText(getContext(), "Lezione effettuata con successo!", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            if(statusCode == 401) {
                                Toast.makeText(getContext(), "Tempo scaduto!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MineRipetizioniActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                        }
                    });
                }
            });
            return convertView;
        }
    }
}