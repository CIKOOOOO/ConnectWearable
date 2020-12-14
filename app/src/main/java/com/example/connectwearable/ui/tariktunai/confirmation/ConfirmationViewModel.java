package com.example.connectwearable.ui.tariktunai.confirmation;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.connectwearable.model.Response;
import com.example.connectwearable.utils.Constant;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfirmationViewModel extends AndroidViewModel {

    private IConfirmationCallback callback;

    public void setCallback(IConfirmationCallback callback) {
        this.callback = callback;
    }

    public ConfirmationViewModel(@NonNull Application application) {
        super(application);
    }

    public void tarikTunai(double nominal, String pinCode, String accountNumber) {
        Log.e("asd", "aaa");
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("accountNumber", accountNumber);
        objectMap.put("pin_code", pinCode);
        objectMap.put("amount", nominal);

        AndroidNetworking.post(Constant.BASE_URL + "tarik-tunai")
                .addApplicationJsonBody(objectMap)
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Response response1 = gson.fromJson(response.toString(), Response.class);
                        callback.onResponse(response1);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("asd", anError.getErrorBody());
                    }
                });
    }

}
