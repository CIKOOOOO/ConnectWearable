package com.example.connectwearable.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.connectwearable.R;
import com.example.connectwearable.utils.BaseActivity;
import com.example.connectwearable.utils.Constant;
import com.example.connectwearable.ui.main.MainActivity;
import com.example.connectwearable.ui.starterloginpage.StarterLoginActivity;
import com.example.connectwearable.ui.tariktunai.TarikTunaiActivity;

public class SplashScreen extends BaseActivity {

    public static final String DATA = "data";
    public static final String TYPE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initVar();
    }

    private void initVar() {
        Intent intent = getIntent();
        if (intent != null) {
            int directionIntent = intent.getIntExtra(TYPE, 0);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    if (directionIntent == Constant.TO_TRANSACTION_SCREEN && intent.hasExtra(DATA)) {
                        Intent intent1 = new Intent(SplashScreen.this, TarikTunaiActivity.class);
                        intent1.putExtra(TarikTunaiActivity.PARCEL_DATA, intent.getStringExtra(DATA));
                        startActivity(intent1);
                    } else if (directionIntent == 0) {
                        if (prefConfig.isLogin()) {
                            startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        } else {
                            startActivity(new Intent(SplashScreen.this, StarterLoginActivity.class));
                        }
                    }
                }
            }, 2000);
        }
    }
}