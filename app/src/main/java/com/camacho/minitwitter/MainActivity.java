package com.camacho.minitwitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button loginBtn;
    TextView goSingUpLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        initializeView();
        loginBtn.setOnClickListener(v -> Toast.makeText(this, "boton login", Toast.LENGTH_LONG).show());
        goSingUpLabel.setOnClickListener(v -> goToSignUp());
    }

    private void initializeView() {
        loginBtn = findViewById(R.id.activity_login__btn__login_in);
        goSingUpLabel = findViewById(R.id.activity_login__label__check_in);
    }

    private void goToSignUp() {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }


}