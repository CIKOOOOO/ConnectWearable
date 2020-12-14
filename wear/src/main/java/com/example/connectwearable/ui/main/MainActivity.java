package com.example.connectwearable.ui.main;


import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import com.example.connectwearable.R;
import com.example.connectwearable.utils.BaseActivity;
import com.example.connectwearable.ui.infosaldo.InfoSaldoActivity;
import com.example.connectwearable.ui.qrku.QRActivity;

public class MainActivity extends BaseActivity implements MainAdapter.onItemClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVar();
    }

    private void initVar() {
        WearableRecyclerView wearableRecyclerView = findViewById(R.id.recycler_main_menu);

        MainAdapter mainAdapter = new MainAdapter(this);

        wearableRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        wearableRecyclerView.setAdapter(mainAdapter);
    }

    @Override
    public void onClickAt(int pos) {
        switch (pos) {
            case 0:
                startActivity(new Intent(this, InfoSaldoActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, QRActivity.class));
                break;
        }
    }
}