package com.example.wifiou4g_versao_1_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.SigningInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {

    TextInputEditText  textInputEditTextUsername, textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonLogin =findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progress);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });

                  buttonLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String fullname, username, password, email;
                       username = String.valueOf(textInputEditTextUsername.getText());
                        password = String.valueOf(textInputEditTextPassword.getText());

                        if(!username.equals("") && !password.equals("")) {
                            progressBar.setVisibility(View.VISIBLE);
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    String[] field = new String[2];
                                    field[0] = "username";
                                    field[1] = "password";
                                    String[] data = new String[2];
                                    data[0] = username;
                                    data[1] = password;
                                    PutData putData = new PutData("http://api.luanmelo.tk/wifiou4g/login.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            progressBar.setVisibility(View.GONE);
                                            String result = putData.getResult();

                                            if(result.equals("Login Success")){
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            }

                                             Log.i("PutData", result);
                                        }
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Todos Os Campos São Obrigatórios!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}