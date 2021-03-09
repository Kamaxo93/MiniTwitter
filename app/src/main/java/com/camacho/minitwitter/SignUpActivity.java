package com.camacho.minitwitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    Button signUpBtn;
    TextView goLoginLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        initializeView();
        goLoginLabel.setOnClickListener(v -> goToLogin());
    }

    private void goToLogin() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void initializeView() {
        signUpBtn = findViewById(R.id.activity_sing_up__btn__login_in);
        goLoginLabel = findViewById(R.id.activity_sing_up__label__login_in);
    }
}