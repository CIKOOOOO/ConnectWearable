package com.example.connectwearable.ui.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.connectwearable.utils.Constant;
import com.example.connectwearable.model.Response;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginViewModel extends AndroidViewModel {

    private ILoginCallback callback;

    public void setCallback(ILoginCallback callback) {
        this.callback = callback;
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void checkingCard(String card_id) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("card_id", card_id);

        AndroidNetworking.post(Constant.BASE_URL + "checking-card")
                .addApplicationJsonBody(objectMap)
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("asd", response.toString());
                        Gson gson = new Gson();
                        Response response1 = gson.fromJson(response.toString(), Response.class);
                        callback.cardChecking(response1.getStatus() == 200 && response1.getMessage().equals(Constant.SUCCESS));
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("asd", anError.getErrorBody());
                    }
                });

    }
}
