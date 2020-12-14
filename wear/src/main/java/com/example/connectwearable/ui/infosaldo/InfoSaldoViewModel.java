package com.example.connectwearable.ui.infosaldo;

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
import com.example.connectwearable.model.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InfoSaldoViewModel extends AndroidViewModel {

    private IInfoSaldoCallback callback;

    public void setCallback(IInfoSaldoCallback callback) {
        this.callback = callback;
    }

    public InfoSaldoViewModel(@NonNull Application application) {
        super(application);
    }

    public void infoSaldo(String user_id, String pin_code) {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("user_id", user_id);
        stringMap.put("pin_code", pin_code);

        AndroidNetworking.post(Constant.BASE_URL + "info-saldo")
                .addApplicationJsonBody(stringMap)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Response response1 = gson.fromJson(response.toString(), Response.class);
                        String data = gson.toJson(response1.getValue());
                        User user = gson.fromJson(data, User.class);
                        callback.onResponse(user);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("asd", anError.getErrorBody());
                    }
                });
    }

}
