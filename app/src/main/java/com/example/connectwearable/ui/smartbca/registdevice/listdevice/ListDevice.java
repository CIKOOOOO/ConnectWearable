package com.example.connectwearable.ui.smartbca.registdevice.listdevice;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connectwearable.R;
import com.example.connectwearable.model.Response;
import com.example.connectwearable.model.Smartwatch;
import com.example.connectwearable.ui.smartbca.registdevice.authenticatedevice.AuthenticateDeviceActivity;
import com.example.connectwearable.utils.BaseActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListDevice extends BaseActivity implements View.OnClickListener, DeviceAdapter.onDevicesClick, IListDeviceCallback {

    private static final String CAPABILITY_1_NAME = "capability_1";
    private static final String CAPABILITY_2_NAME = "capability_2";

    private DeviceAdapter adapter;
    private Smartwatch smartwatch;
    private ListDeviceViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_device);
        initVar();
    }

    private void initVar() {
        Button btnPairing = findViewById(R.id.btn_pairing_smartbca);
        RecyclerView recyclerView = findViewById(R.id.recycler_device_list);

        adapter = new DeviceAdapter(this);
        viewModel = new ViewModelProvider(this).get(ListDeviceViewModel.class);
        viewModel.setCallback(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        showNodes(CAPABILITY_1_NAME, CAPABILITY_2_NAME);
//
//        List<Smartwatch> smartwatchList = new ArrayList<>();
//        smartwatchList.add(new Smartwatch("iWatch ex1", "nodes_001"));
//        smartwatchList.add(new Smartwatch("Oppo Watch", "nodes_002"));
//        smartwatchList.add(new Smartwatch("Galaxy Smartwatch", "nodes_003"));
//
//        adapter.setStringList(smartwatchList);
//        adapter.notifyDataSetChanged();

        btnPairing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pairing_smartbca:
                if (smartwatch == null) {
                    Toast.makeText(this, "Mohon pilih device smartwatch", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.authenticateDevice("user_001", smartwatch.getNodesID());
                }
                break;
        }
    }

    @Override
    public void onCLick(Smartwatch smartwatch) {
        this.smartwatch = smartwatch;
    }

    @Override
    public void onResponse(Response response) {
        Gson gson = new Gson();
        String data = gson.toJson(response);
        Intent intent = new Intent(this, AuthenticateDeviceActivity.class);
        intent.putExtra(AuthenticateDeviceActivity.DATA, data);
        startActivity(intent);
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
        List<Smartwatch> smartwatchList = new ArrayList<>();
        for (Node node : nodes) {
            smartwatchList.add(new Smartwatch(node.getDisplayName(), node.getId()));
        }
        adapter.setStringList(smartwatchList);
        adapter.notifyDataSetChanged();
        Log.e(
                "asd",
                "Connected Nodes: "
                        + (nodesList.isEmpty()
                        ? "No connected device was found for the given capabilities"
                        : TextUtils.join(",", nodesList)));

    }
}