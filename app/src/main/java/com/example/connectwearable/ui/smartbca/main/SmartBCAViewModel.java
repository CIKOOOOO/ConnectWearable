package com.example.connectwearable.ui.smartbca.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.connectwearable.utils.Constant;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SmartBCAViewModel extends AndroidViewModel {

    private ISmartBCACallback callback;

    public void setCallback(ISmartBCACallback callback) {
        this.callback = callback;
    }

    public SmartBCAViewModel(@NonNull Application application) {
        super(application);
    }

    public void removeDevice(String authenticate_id) {
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("authenticate_id", authenticate_id);
        AndroidNetworking.post(Constant.BASE_URL + "remove-smartwatch")
                .addApplicationJsonBody(stringStringMap)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onRemoveSuccess();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("asd", anError.getErrorBody());
                    }
                });
    }
}
