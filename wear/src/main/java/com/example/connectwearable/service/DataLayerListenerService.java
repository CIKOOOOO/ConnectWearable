package com.example.connectwearable.service;

import android.content.Intent;
import android.util.Log;

import com.example.connectwearable.model.Event;
import com.example.connectwearable.ui.splash.SplashScreen;
import com.example.connectwearable.utils.Constant;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.gson.Gson;

public class DataLayerListenerService extends WearableListenerService {
    private static final String START_ACTIVITY_PATH = "/start-activity";
    private static final String TRANSACTION_CODE = "/transaction_code";

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        Log.e("asd", "hola hola");
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
//        super.onMessageReceived(messageEvent);
        Gson gson = new Gson();
        Event event = gson.fromJson(messageEvent.getPath(), Event.class);
        Log.e("asd", messageEvent.getPath());
        if (event.getResponse().equals(TRANSACTION_CODE)) {
            Intent startIntent = new Intent(this, SplashScreen.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startIntent.putExtra(SplashScreen.DATA, messageEvent.getPath());
            startIntent.putExtra(SplashScreen.TYPE, Constant.TO_TRANSACTION_SCREEN);
            startActivity(startIntent);
        }
    }
}
