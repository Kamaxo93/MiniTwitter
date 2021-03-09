package com.camacho.minitwitter.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.camacho.minitwitter.R;
import com.camacho.minitwitter.common.Constant;
import com.camacho.minitwitter.common.SharedPreferencesManager;
import com.camacho.minitwitter.retrofit.MiniTwitterClient;
import com.camacho.minitwitter.retrofit.MiniTwitterService;
import com.camacho.minitwitter.retrofit.request.RequestLogin;
import com.camacho.minitwitter.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button loginBtn;
    TextView goSingUpLabel;
    EditText emailInput;
    EditText passwordInput;
    MiniTwitterClient miniTwitterClient;
    MiniTwitterService miniTwitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        retrofitInit();
        initializeView();
        events();
    }

    private void retrofitInit() {
        miniTwitterClient = MiniTwitterClient.getInstance();
        miniTwitterService = miniTwitterClient.getMiniTwitterService();
    }

    private void events() {
        loginBtn.setOnClickListener(v -> goToLogin());
        goSingUpLabel.setOnClickListener(v -> goToSignUp());
    }

    private void goToLogin() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty()) {
            emailInput.setError(getString(R.string.text_error_input_email_login));

        } else if (password.isEmpty()) {
            passwordInput.setError(getString(R.string.text_error_input_password_login));

        } else {
            RequestLogin requestLogin = new RequestLogin(email, password);

            Call<ResponseAuth> call = miniTwitterService.doLogin(requestLogin);

            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if (response.isSuccessful() &&
                    response.body() != null) {
                        Toast.makeText(MainActivity.this, "Sesión inciada correctamente", Toast.LENGTH_SHORT).show();
                        SharedPreferencesManager.setSomeStringValue(Constant.PREF_TOKEN, response.body().getToken());
                        SharedPreferencesManager.setSomeStringValue(Constant.PREF_USERNAME, response.body().getUsername());
                        SharedPreferencesManager.setSomeStringValue(Constant.PREF_EMAIL, response.body().getEmail());
                        SharedPreferencesManager.setSomeStringValue(Constant.PREF_PHOTOURL, response.body().getPhotoUrl());
                        SharedPreferencesManager.setSomeStringValue(Constant.PREF_CREATED, response.body().getCreated());
                        SharedPreferencesManager.setSomeBooleanValue(Constant.PREF_ACTIVE, response.body().getActive());
                        Intent i = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(MainActivity.this, "Algo fue mal, revise sus datos de acceso", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Problemas en la conexión, intenteló de nuevo mas tarde", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initializeView() {
        loginBtn = findViewById(R.id.activity_login__btn__login_in);
        goSingUpLabel = findViewById(R.id.activity_login__label__check_in);
        emailInput = findViewById(R.id.activity_login__input__email);
        passwordInput = findViewById(R.id.activity_login__input__password);
    }

    private void goToSignUp() {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }


}