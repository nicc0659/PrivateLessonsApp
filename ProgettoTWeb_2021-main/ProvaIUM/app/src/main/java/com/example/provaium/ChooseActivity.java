package com.example.provaium;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class ChooseActivity extends AppCompatActivity {

    private Button btnCatalogo, btnMieRipe, btnLogout;
    AsyncHttpClient client;
    String MYURL = "http://192.168.1.2:8080/ProgettoTWeb_2021Back_war_exploded/logout";
    RequestParams params;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        Intent i=getIntent();
        String token=i.getStringExtra("token");
        btnCatalogo = findViewById(R.id.catal_ripe);
        btnMieRipe= findViewById(R.id.mieripe);
        btnLogout= findViewById(R.id.logout);
        if (token == null) {
            btnMieRipe.setVisibility(View.GONE);
            btnCatalogo.setY(300);
            btnLogout.setText("Login");
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ChooseActivity.this, MainActivity.class);
                    startActivity(i);
                }
            });
        }
        btnCatalogo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ChooseActivity.this, CorsiActivity.class);
                i.putExtra("token", token);
                startActivity(i);
            }
        });
        btnMieRipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ChooseActivity.this, MineRipetizioniActivity.class);
                i.putExtra("token", token);
                startActivity(i);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params = new RequestParams();
                params.put("token", token);
                client = new AsyncHttpClient();
                client.post(MYURL, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        try {

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                Intent i = new Intent(ChooseActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}