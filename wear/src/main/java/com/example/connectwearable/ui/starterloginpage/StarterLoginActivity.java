package com.example.connectwearable.ui.starterloginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.connectwearable.R;
import com.example.connectwearable.utils.BaseActivity;
import com.example.connectwearable.ui.login.LoginActivity;

public class StarterLoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter_login);
        initVar();
    }

    private void initVar() {
        Button btnStarterLogin = findViewById(R.id.btn_starter_login);

        btnStarterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StarterLoginActivity.this, LoginActivity.class));
            }
        });
    }
}