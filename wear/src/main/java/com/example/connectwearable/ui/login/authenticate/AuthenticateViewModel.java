package com.example.connectwearable.ui.login.authenticate;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.connectwearable.utils.Constant;
import com.example.connectwearable.model.DeviceAuthenticate;
import com.example.connectwearable.model.Response;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthenticateViewModel extends AndroidViewModel {

    private IAuthenticateCallback callback;

    public void setCallback(IAuthenticateCallback callback) {
        this.callback = callback;
    }

    public AuthenticateViewModel(@NonNull Application application) {
        super(application);
    }

    public void authenticateData(String card_id, String authenticate_id, String node_id) {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("card_id", card_id);
        stringMap.put("authenticate_code", authenticate_id);
        stringMap.put("smartphone_node_id", node_id);

        AndroidNetworking.post(Constant.BASE_URL + "authenticate-from-smartwatch")
                .addApplicationJsonBody(stringMap)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Response response1 = gson.fromJson(response.toString(), Response.class);
                        String data = gson.toJson(response1.getValue());
                        DeviceAuthenticate deviceAuthenticate = gson.fromJson(data, DeviceAuthenticate.class);
                        callback.onResponse(deviceAuthenticate);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("asd", anError.getErrorBody());
                    }
                });
    }
}
