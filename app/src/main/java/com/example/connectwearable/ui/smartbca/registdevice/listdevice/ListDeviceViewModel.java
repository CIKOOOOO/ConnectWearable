package com.example.connectwearable.ui.smartbca.registdevice.listdevice;

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

public class ListDeviceViewModel extends AndroidViewModel {

    private IListDeviceCallback callback;

    public void setCallback(IListDeviceCallback callback) {
        this.callback = callback;
    }

    public ListDeviceViewModel(@NonNull Application application) {
        super(application);
    }

    public void authenticateDevice(String user_id, String nodes_id){
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("user_id", user_id);
        objectMap.put("smartwatch_nodes_id", nodes_id);

        AndroidNetworking.post(Constant.BASE_URL + "authenticate-device")
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
