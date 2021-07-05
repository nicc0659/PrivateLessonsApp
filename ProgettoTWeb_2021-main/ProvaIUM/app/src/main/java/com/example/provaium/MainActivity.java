package com.example.provaium;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import model.Account;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnGuest;
    private TextInputEditText etUsername;
    private TextInputEditText etPassword;
    String token = null;
    String u, p;
    AsyncHttpClient client;
    RequestParams params;
    String MYURL = "http://192.168.1.2:8080/ProgettoTWeb_2021Back_war_exploded/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGuest = findViewById(R.id.btnGuest);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u = etUsername.getText().toString();
                p = etPassword.getText().toString();
                params = new RequestParams();
                params.put("username", u);
                params.put("password", p);
                client = new AsyncHttpClient();
                client.post(MYURL, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            JSONObject object = response;
                                int id = object.getInt("id");
                                String username = object.getString("username");
                                int admin = object.getInt("admin");
                                token = object.getString("token");
                                Account account = new Account(id, username, admin);
                                account.setToken(token);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this, "Benvenuto!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, ChooseActivity.class);
                        i.putExtra("token", token);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(MainActivity.this, "Login failure " + statusCode, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ChooseActivity.class);
                i.putExtra("token", token);
                startActivity(i);
            }
        });

    }
}
