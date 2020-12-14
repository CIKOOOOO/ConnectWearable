package com.example.connectwearable.ui.login;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.connectwearable.R;
import com.example.connectwearable.utils.BaseActivity;
import com.example.connectwearable.utils.Constant;
import com.example.connectwearable.ui.login.authenticate.AuthenticateActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginActivity extends BaseActivity implements View.OnClickListener, CapabilityClient.OnCapabilityChangedListener
        , DataClient.OnDataChangedListener, MessageClient.OnMessageReceivedListener, ILoginCallback {

    private static final String CAPABILITY_1_NAME = "capability_1";
    private static final String CAPABILITY_2_NAME = "capability_2";

    private EditText etCard;
    private LoginViewModel viewModel;

    private String nodesID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initVar();
    }

    private void initVar() {
        Button btnLogin = findViewById(R.id.btn_next_login);

        etCard = findViewById(R.id.et_card_login);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.setCallback(this);

        btnLogin.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Instantiates clients without member variables, as clients are inexpensive to create and
        // won't lose their listeners. (They are cached and shared between GoogleApi instances.)
        Wearable.getDataClient(this).addListener(this);
        Wearable.getMessageClient(this).addListener(this);
        Wearable.getCapabilityClient(this)
                .addListener(this, Uri.parse("wear://"), CapabilityClient.FILTER_REACHABLE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Wearable.getDataClient(this).removeListener(this);
        Wearable.getMessageClient(this).removeListener(this);
        Wearable.getCapabilityClient(this).removeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next_login:
                String card = etCard.getText().toString().trim();
                if (card.length() != Constant.LENGTH_OF_CARD) {
                    Toast.makeText(this, getString(R.string.error_login_card), Toast.LENGTH_SHORT).show();
                } else {
//                    showNodes(CAPABILITY_1_NAME, CAPABILITY_2_NAME);
                    nodesID = "nodes01";
                    viewModel.checkingCard(etCard.getText().toString());
                }
                break;
        }
    }

    @Override
    public void onCapabilityChanged(@NonNull CapabilityInfo capabilityInfo) {
        Log.e("asd", capabilityInfo.toString());
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
            nodesID = nodesList.get(0);
            viewModel.checkingCard(etCard.getText().toString());
        }
    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
        Log.e("asd", "hola");
    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {
        Log.e("asd", "message");
    }

    @Override
    public void cardChecking(boolean isExist) {
        if (isExist) {
            Intent intent = new Intent(LoginActivity.this, AuthenticateActivity.class);
            intent.putExtra(AuthenticateActivity.PARCEL_DATA, etCard.getText().toString());
            intent.putExtra(AuthenticateActivity.NODE_DATA, this.nodesID);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Nomor Kartu tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }
}