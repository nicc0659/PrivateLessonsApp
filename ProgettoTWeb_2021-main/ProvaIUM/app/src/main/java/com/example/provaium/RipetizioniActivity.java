package com.example.provaium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import model.Ripetizioni;

public class RipetizioniActivity extends AppCompatActivity {

    AsyncHttpClient client;
    RipetizioniAdapter list = null;
    String MYURL = "http://192.168.1.2:8080/ProgettoTWeb_2021Back_war_exploded/servletinsegna";
    String MYURL2 = "http://192.168.1.2:8080/ProgettoTWeb_2021Back_war_exploded/servletprenotazioni";
    ArrayList<Ripetizioni> ripetizioni = new ArrayList<>();
    RequestParams params, parametri;
    String token = null;
    String materia = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripetizioni);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        Intent i=getIntent();
        String docente=i.getStringExtra("docente");
        materia=i.getStringExtra("materia");
        token = i.getStringExtra("token");
        String id_docente=i.getStringExtra("id_docente");
        ListView lv = (ListView) findViewById(R.id.listViewRipe);
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        params = new RequestParams();
        params.put("id", id_docente);
        params.put("token", token);
        client = new AsyncHttpClient();
        client.post(MYURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Ripetizioni>>() {}.getType(); //Crea un tipo che definisce una lista dell'oggetto che preferisci
                    ripetizioni = gson.fromJson(response.toString(), listType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                list = new RipetizioniAdapter(getApplicationContext(),R.layout.rowcustom, ripetizioni); //L'adapter, ti permette di personalizzare/prendere gli elementi di ogni singola riga
                lv.setAdapter(list);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                if(statusCode == 401) {
                    Intent i = new Intent(RipetizioniActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                params = new RequestParams();
                params.put("id", id_docente);
                client = new AsyncHttpClient();
                client.post(MYURL, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Ripetizioni>>() {}.getType(); //Crea un tipo che definisce una lista dell'oggetto che preferisci
                            ripetizioni = gson.fromJson(response.toString(), listType);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        list = new RipetizioniAdapter(getApplicationContext(),R.layout.rowcustom, ripetizioni); //L'adapter, ti permette di personalizzare/prendere gli elementi di ogni singola riga
                        lv.setAdapter(list);
                    }
                });
                refreshLayout.setRefreshing(false);
            }
        });
    }
    public class RipetizioniAdapter extends ArrayAdapter<Ripetizioni> {
        public RipetizioniAdapter(Context context, int textViewResourceId,
                                  ArrayList<Ripetizioni> objects) {
            super(context, textViewResourceId, objects);
        }
        @SuppressLint({"ViewHolder", "SetTextI18n"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rowcustom, null);
            TextView ripetizioni_2 = (TextView) convertView.findViewById(R.id.textViewMat);
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
            Button button = (Button) convertView.findViewById(R.id.button);
            button.setText("Prenota!");
            Button button_del = (Button)convertView.findViewById(R.id.buttonConferma);
            button_del.setVisibility(View.GONE);
            Ripetizioni r = getItem(position);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Faccio la chiamata alla servlet
                    parametri = new RequestParams();
                    parametri.put("prof", r.getDoc());
                    parametri.put("giorno", r.getGiorno());
                    parametri.put("orarioIn", r.getOrarioIn());
                    parametri.put("materia", materia);
                    parametri.put("token", token);
                    client = new AsyncHttpClient();
                    client.post(MYURL2, parametri, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                Toast.makeText(getContext(), "Lezione prenotata!!", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            if(statusCode == 401) {
                                Intent i = new Intent(RipetizioniActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                        }
                    });
                    //Toast.makeText(getContext(), ripetizioni.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            ripetizioni_2.setText(new StringBuilder().append(r.getOrarioIn()).append(" ").append(r.getGiorno()).toString()); //Volendo puoi concatenare l'ID con StringBuilder (scrivi una normale concat e risolvi il warning)
            return convertView;
        }

    }
}
