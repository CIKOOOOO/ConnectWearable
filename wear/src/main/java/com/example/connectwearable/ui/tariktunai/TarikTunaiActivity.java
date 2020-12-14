package com.example.connectwearable.ui.tariktunai;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.wear.ambient.AmbientModeSupport;

import com.example.connectwearable.R;
import com.example.connectwearable.utils.BaseActivity;
import com.example.connectwearable.model.Event;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.ncorti.slidetoact.SlideToActView;

public class TarikTunaiActivity extends BaseActivity implements DataClient.OnDataChangedListener
        , MessageClient.OnMessageReceivedListener
        , CapabilityClient.OnCapabilityChangedListener {

    public static final String PARCEL_DATA = "parcel_data";

    private static final String TEXT_KEY = "text";

    private TextView mTextView;
    private TextView tvStatus;
    private SlideToActView slideToActView;

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarik_tunai);

        initVar();
    }

    private void initVar() {
        mTextView = (TextView) findViewById(R.id.tv_transaction_code);
        tvStatus = findViewById(R.id.tv_status_main);
        slideToActView = findViewById(R.id.slide_main);

        Intent intent = getIntent();
        if (intent.hasExtra(PARCEL_DATA)) {
            String parcelData = intent.getStringExtra(PARCEL_DATA);
            Gson gson = new Gson();
            this.event = gson.fromJson(parcelData, Event.class);
        } else {
            onBackPressed();
        }

        slideToActView.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                String dueDate = "Jatuh Tempo : " + event.getDueDate();
                slideToActView.setVisibility(View.GONE);
                mTextView.setVisibility(View.VISIBLE);
                mTextView.setText(event.getTransactionCode());
                tvStatus.setText(dueDate);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Wearable.getDataClient(this).addListener(this);
        Wearable.getMessageClient(this).addListener(this);
        Wearable.getCapabilityClient(this).addListener(this, Uri.parse("wear://"), CapabilityClient.FILTER_REACHABLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.getDataClient(this).removeListener(this);
        Wearable.getMessageClient(this).removeListener(this);
        Wearable.getCapabilityClient(this).removeListener(this);
    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
        Log.e("asd", dataEventBuffer.toString());
//        mTextView.setText("");
//        for (DataEvent dataEvent : dataEventBuffer) {
//            String data = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap().getString(TEXT_KEY);
//            mTextView.append(data + "\n");
//        }
    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {
        Log.e("asd", "Wear : Message Received : " + messageEvent.getPath());
    }

    @Override
    public void onCapabilityChanged(@NonNull CapabilityInfo capabilityInfo) {
        Log.e("asd", "Wear : Capability Info : " + capabilityInfo.getName());
    }
}
