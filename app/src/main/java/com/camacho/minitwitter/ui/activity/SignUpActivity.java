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
import com.camacho.minitwitter.retrofit.request.RequestSignUp;
import com.camacho.minitwitter.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    Button signUpBtn;
    TextView goLoginLabel;
    EditText nameUserInput;
    EditText emailInput;
    EditText passwordInput;
    private MiniTwitterClient miniTwitterClient;
    private MiniTwitterService miniTwitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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

    private void initializeView() {
        signUpBtn = findViewById(R.id.activity_sing_up__btn__login_in);
        goLoginLabel = findViewById(R.id.activity_sing_up__label__login_in);
        nameUserInput = findViewById(R.id.activity_sing_up__input__user_name);
        emailInput = findViewById(R.id.activity_sing_up__input__email);
        passwordInput = findViewById(R.id.activity_sing_up__input__password);
    }

    private void events() {
        goLoginLabel.setOnClickListener(v -> goToLogin());
        signUpBtn.setOnClickListener(v -> goToSignUp());
    }

    private void goToSignUp() {
        String nameUser = nameUserInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (nameUser.isEmpty()) {
            nameUserInput.setError(getString(R.string.text_error_input_user_name));

        } else if (email.isEmpty()) {
            emailInput.setError(getString(R.string.text_error_input_email_sign_up));

        } else if (password.isEmpty() ||
                password.length() < Constant.MINIMUM_NUMBER_OF_CHARACTERS) {
            passwordInput.setError(getString(R.string.text_error_input_password_sign_up));

        } else {
            RequestSignUp requestSignUp = new RequestSignUp(nameUser, email, password, Constant.CODE_REGISTER);

            Call<ResponseAuth> call = miniTwitterService.doSignUp(requestSignUp);

            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Registro correcto", Toast.LENGTH_SHORT).show();

                        SharedPreferencesManager.setSomeStringValue(Constant.PREF_TOKEN, response.body().getToken());
                        SharedPreferencesManager.setSomeStringValue(Constant.PREF_USERNAME, response.body().getUsername());
                        SharedPreferencesManager.setSomeStringValue(Constant.PREF_EMAIL, response.body().getEmail());
                        SharedPreferencesManager.setSomeStringValue(Constant.PREF_PHOTOURL, response.body().getPhotoUrl());
                        SharedPreferencesManager.setSomeStringValue(Constant.PREF_CREATED, response.body().getCreated());
                        SharedPreferencesManager.setSomeBooleanValue(Constant.PREF_ACTIVE, response.body().getActive());
                        Intent i = new Intent(SignUpActivity.this, DashboardActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(SignUpActivity.this, "Algo fue mal, revise sus datos de acceso", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, "Problemas en la conexión, intenteló de nuevo mas tarde", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void goToLogin() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}