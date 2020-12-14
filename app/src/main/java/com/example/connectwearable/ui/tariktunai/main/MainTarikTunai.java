package com.example.connectwearable.ui.tariktunai.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.connectwearable.R;
import com.example.connectwearable.ui.tariktunai.confirmation.ConfirmationTarikTunai;
import com.example.connectwearable.utils.BaseActivity;

public class MainTarikTunai extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tarik_tunai);
        initVar();
    }

    private void initVar() {
        Button btnNominal = findViewById(R.id.btn_nominal);

        btnNominal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_nominal:
                Intent intent = new Intent(this, ConfirmationTarikTunai.class);
                intent.putExtra(ConfirmationTarikTunai.PARCEL_DATA, 100000);
                startActivity(intent);
                break;
        }

    }
}