package com.example.connectwearable.ui.smartbca.registdevice.authenticatedevice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.connectwearable.R;
import com.example.connectwearable.model.DeviceAuthenticate;
import com.example.connectwearable.model.Response;
import com.example.connectwearable.utils.BaseActivity;
import com.google.gson.Gson;

public class AuthenticateDeviceActivity extends BaseActivity implements View.OnClickListener {

    public static final String DATA = "parcel_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_device);
        initVar();
    }

    private void initVar() {
        Button btnBack = findViewById(R.id.btn_back_authenticate);
        TextView tvAuthenticateCode = findViewById(R.id.tv_authenticate_code);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(DATA)) {
            String data = intent.getStringExtra(DATA);
            Gson gson = new Gson();
            Response response = gson.fromJson(data, Response.class);
            String res = gson.toJson(response.getValue());
            DeviceAuthenticate deviceAuthenticate = gson.fromJson(res, DeviceAuthenticate.class);
            tvAuthenticateCode.setText(deviceAuthenticate.getAuthenticate_code());
            prefConfig.setSmartWatchNodes(deviceAuthenticate.getSmartwatch_nodes_id());
            prefConfig.setAuthenticateID(deviceAuthenticate.getAuthenticate_id());
        } else onBackPressed();

        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back_authenticate:
                onBackPressed();
                break;
        }
    }
}