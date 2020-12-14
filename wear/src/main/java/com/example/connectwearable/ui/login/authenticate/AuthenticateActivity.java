package com.example.connectwearable.ui.login.authenticate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.connectwearable.R;
import com.example.connectwearable.utils.BaseActivity;
import com.example.connectwearable.model.DeviceAuthenticate;
import com.example.connectwearable.ui.splash.SplashScreen;

public class AuthenticateActivity extends BaseActivity implements View.OnClickListener, IAuthenticateCallback {

    public static final String PARCEL_DATA = "data";
    public static final String NODE_DATA = "node_data";

    private EditText et_authenticate_code;
    private AuthenticateViewModel viewModel;

    private String card, nodeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        initVar();
    }

    private void initVar() {
        Button btnFinish = findViewById(R.id.btn_finish_login);

        et_authenticate_code = findViewById(R.id.et_authenticate_code);

        viewModel = new ViewModelProvider(this).get(AuthenticateViewModel.class);
        viewModel.setCallback(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(PARCEL_DATA) && intent.hasExtra(NODE_DATA)) {
            card = intent.getStringExtra(PARCEL_DATA);
            nodeID = intent.getStringExtra(NODE_DATA);
            if (card == null || nodeID == null) {
                onBackPressed();
            }
        } else onBackPressed();

        btnFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish_login:
                String data = et_authenticate_code.getText().toString();
                if (data.trim().length() != 6) {
                    Toast.makeText(this, "Mohon masukkan data yang valid", Toast.LENGTH_SHORT).show();
                } else
                    viewModel.authenticateData(card, data, nodeID);
                break;
        }
    }

    @Override
    public void onResponse(DeviceAuthenticate deviceAuthenticate) {
        prefConfig.setSmartphoneNodesID(deviceAuthenticate.getSmartphone_nodes_id());
        prefConfig.setUserID(deviceAuthenticate.getUser_id());
        prefConfig.setLogin(true);
        finishAffinity();
        startActivity(new Intent(this, SplashScreen.class));
    }
}