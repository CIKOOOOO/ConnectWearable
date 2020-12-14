package com.example.connectwearable.ui.tariktunai.confirmation;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.connectwearable.R;
import com.example.connectwearable.model.Event;
import com.example.connectwearable.model.Response;
import com.example.connectwearable.model.TarikTunai;
import com.example.connectwearable.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ConfirmationTarikTunai extends AppCompatActivity implements View.OnClickListener, IConfirmationCallback, DataClient.OnDataChangedListener, MessageClient.OnMessageReceivedListener, CapabilityClient.OnCapabilityChangedListener {

    public static final String PARCEL_DATA = "parcel_data";

    private static final String CAPABILITY_1_NAME = "capability_1";
    private static final String CAPABILITY_2_NAME = "capability_2";
    private static final String TAG = ConfirmationTarikTunai.class.getSimpleName();
    private static final String TRANSACTION_CODE = "/transaction_code";

    private TextView tvNominal;
    private ConfirmationViewModel viewModel;
    private CheckBox checkBox;

    private double nominal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_tarik_tunai);
        initVar();
    }

    private void initVar() {
        Button btnOk = findViewById(R.id.btn_ok_tarik_tunai);
        Button btnCancel = findViewById(R.id.btn_cancel_tarik_tunai);

        tvNominal = findViewById(R.id.tv_nominal_tarik_tunai);
        checkBox = findViewById(R.id.cb_smartwatch_tariktunai);

        viewModel = new ViewModelProvider(this).get(ConfirmationViewModel.class);
        viewModel.setCallback(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(PARCEL_DATA)) {
            nominal = intent.getIntExtra(PARCEL_DATA, -1);
            if (nominal == -1) {
                onBackPressed();
            }
            tvNominal.setText(Utils.priceFormat(nominal));
        }


        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok_tarik_tunai:
                if (checkBox.isChecked()) {
                    showNodes(CAPABILITY_1_NAME, CAPABILITY_2_NAME);
                } else
                    viewModel.tarikTunai(nominal, "123123", "5271517891");
                break;
            case R.id.btn_cancel_tarik_tunai:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onResponse(Response response) {
        TextView textView = findViewById(R.id.tv_result_tarik_tunai);
        Gson gson = new Gson();
        String data = gson.toJson(response.getValue());
        TarikTunai tarikTunai = gson.fromJson(data, TarikTunai.class);
        textView.setText(gson.toJson(response));
        if (checkBox.isChecked()) {
            Event event = new Event();
            event.setDueDate(tarikTunai.getEnd_date());
            event.setTransactionCode(tarikTunai.getTransaction_code());
            event.setResponse(TRANSACTION_CODE);
            sendData(gson.toJson(event));
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
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {

    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {

    }

    @Override
    public void onCapabilityChanged(@NonNull CapabilityInfo capabilityInfo) {

    }

    private void sendData(String data) {
        StartWearableActivity startWearableActivity = new StartWearableActivity();
        startWearableActivity.execute(data);
    }

    private class StartWearableActivity extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... voids) {
            Collection<String> stringCollection = getNodes();
            for (String node : stringCollection) {
                sendStartActivityMessage(node, voids[0]);
            }
            return null;
        }
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

    private void sendStartActivityMessage(String node, String data) {
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

    private void showNodes(final String... capabilityNames) {
        Task<Map<String, CapabilityInfo>> capabilitiesTask =
                Wearable.getCapabilityClient(this)
                        .getAllCapabilities(CapabilityClient.FILTER_REACHABLE);

        capabilitiesTask.addOnSuccessListener(
                new OnSuccessListener<Map<String, CapabilityInfo>>() {
                    @Override
                    public void onSuccess(Map<String, CapabilityInfo> capabilityInfoMap) {
                        Set<Node> nodes = new HashSet<>();

                        if (capabilityInfoMap.isEmpty()) {
                            showDiscoveredNodes(nodes);
                        }
                        for (String capabilityName : capabilityNames) {
                            CapabilityInfo capabilityInfo = capabilityInfoMap.get(capabilityName);
                            if (capabilityInfo != null) {
                                nodes.addAll(capabilityInfo.getNodes());
                            }
                        }
                        showDiscoveredNodes(nodes);
                    }
                });
    }

    private void showDiscoveredNodes(Set<Node> nodes) {
        List<String> nodesList = new ArrayList<>();
        for (Node node : nodes) {
            nodesList.add(node.getId());
        }
        Log.e(
                "asd",
                "Connected Nodes: "
                        + (nodesList.isEmpty()
                        ? "No connected device was found for the given capabilities"
                        : TextUtils.join(",", nodesList)));

        if (nodesList.size() == 0) {
            Toast.makeText(this, "Tidak ada device yang tersambung", Toast.LENGTH_SHORT).show();
        } else if (nodesList.size() > 1) {
            Toast.makeText(this, "Device yang terkoneksi hanya boleh 1 device", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("asd", "this is else");
            viewModel.tarikTunai(nominal, "123123", "5271517891");
        }
    }
}