package com.example.connectwearable.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.example.connectwearable.R;
import com.example.connectwearable.model.Event;
import com.example.connectwearable.ui.smartbca.main.MainSmartBCA;
import com.example.connectwearable.ui.tariktunai.main.MainTarikTunai;
import com.example.connectwearable.utils.BaseActivity;
import com.example.connectwearable.utils.Constant;
import com.example.connectwearable.utils.Utils;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.here.oksse.OkSse;
import com.here.oksse.ServerSentEvent;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements DataClient.OnDataChangedListener, View.OnClickListener, MessageClient.OnMessageReceivedListener, CapabilityClient.OnCapabilityChangedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String START_ACTIVITY_PATH = "/start-activity";
    private static final String TEXT_PATH = "/text";
    private static final String TEXT_KEY = "text";
    private static final String TRANSACTION_CODE = "/transaction_code";

    private EditText etData;

    private ServerSentEvent sse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVar();
    }

    private void initVar() {
        Button sendData = findViewById(R.id.send_data);
        Button btnTarikTunai = findViewById(R.id.btn_tarik_tunai);
        Button btnSmartBCA = findViewById(R.id.btn_smart_bca);

//        etData = findViewById(R.id.et_main);

        sendData.setOnClickListener(this);
        btnTarikTunai.setOnClickListener(this);
        btnSmartBCA.setOnClickListener(this);
    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_data:
//                AndroidNetworking.post(Constant.BASE_URL + "checking-card")
//                        .addBodyParameter("card_id", "1111111111111111")
//                        .setTag("checking-card")
//                        .setPriority(Priority.MEDIUM)
//                        .build()
//                        .getAsJSONObject(new JSONObjectRequestListener() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                Log.e("asd", response.toString());
//                                try {
//                                    Log.e("asd", response.getString("message"));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onError(ANError anError) {
//                                Log.e("asd", anError.getMessage().toString());
//                            }
//                        });
//                sendData();
                break;
            case R.id.btn_tarik_tunai:
                startActivity(new Intent(this, MainTarikTunai.class));
                break;
            case R.id.btn_smart_bca:
                startActivity(new Intent(this, MainSmartBCA.class));
                break;
        }
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
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {
        Log.e("asd", "Message Event : " + messageEvent.getPath());
    }

    @Override
    public void onCapabilityChanged(@NonNull CapabilityInfo capabilityInfo) {
        Log.e("asd", "Capability Info : " + capabilityInfo.getName());
    }

//    private void sendData() {
//        String data = etData.getText().toString();
//        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(TEXT_PATH);
//        putDataMapRequest.getDataMap().putString(TEXT_KEY, "My Data : " + data);
//        PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();
//        putDataRequest.setUrgent();
//
//        Task<DataItem> itemTask = Wearable.getDataClient(this).putDataItem(putDataRequest);
//        itemTask.addOnSuccessListener(new OnSuccessListener<DataItem>() {
//            @Override
//            public void onSuccess(DataItem dataItem) {
//                Log.e("asd", "Success to send data " + dataItem);
//            }
//        });
//    }

    private void sendData() {
        new StartWearableActivity().execute();
    }

    @WorkerThread
    private Collection<String> getNodes() {
        HashSet<String> result = new HashSet<>();

        Task<List<Node>> listTask = Wearable.getNodeClient(this).getConnectedNodes();

        try {
            List<Node> nodes = Tasks.await(listTask);

            for (Node nodes1 : nodes) {
                result.add(nodes1.getId());
                Log.e("asd", nodes1.getDisplayName());
            }

        } catch (ExecutionException nodes) {
            Log.e(TAG, "Task failed: " + nodes);
        } catch (InterruptedException nodes) {
            Log.e(TAG, "Interrupt occurred: " + nodes);
        }

        return result;
    }

    private void sendStartActivityMessage(String node) {
        String data = getEventJSON();

        Task<Integer> integerTask = Wearable.getMessageClient(this).sendMessage(node, data, new byte[0]);

        try {
            Integer res = Tasks.await(integerTask);
            Log.e(TAG, "Message sent: " + res);
        } catch (ExecutionException nodes) {
            Log.e(TAG, "Task failed: " + nodes);
        } catch (InterruptedException nodes) {
            Log.e(TAG, "Interrupt occurred: " + nodes);
        }
    }

    private class StartWearableActivity extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Collection<String> stringCollection = getNodes();
            for (String node : stringCollection) {
                sendStartActivityMessage(node);
            }
            return null;
        }
    }

    private String getEventJSON() {
        Random random = new Random();
        int ran = random.nextInt(999999) + 100000;
        Event event = new Event();
        event.setTransactionCode(ran + "");
        event.setResponse(TRANSACTION_CODE);
        event.setDueDate(Utils.getTime("dd/MM HH:mm:ss", 2));

        Gson gson = new Gson();

        return gson.toJson(event);
    }

}