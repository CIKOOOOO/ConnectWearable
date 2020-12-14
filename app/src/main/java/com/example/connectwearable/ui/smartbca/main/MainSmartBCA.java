package com.example.connectwearable.ui.smartbca.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.connectwearable.R;
import com.example.connectwearable.ui.smartbca.registdevice.listdevice.ListDevice;
import com.example.connectwearable.utils.BaseActivity;
import com.example.connectwearable.utils.dialog.RemoveSmartwatchDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainSmartBCA extends BaseActivity implements View.OnClickListener, RemoveSmartwatchDialog.onClick, ISmartBCACallback {

    private static final String CAPABILITY_1_NAME = "capability_1";
    private static final String CAPABILITY_2_NAME = "capability_2";
    private static final String TAG = MainSmartBCA.class.getSimpleName();

    private TextView tvStatusSmartwatch;
    private RemoveSmartwatchDialog smartwatchDialog;
    private SmartBCAViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_smart_bca);
        initVar();
    }

    private void initVar() {
        ImageView imgLogo = findViewById(R.id.img_main_smartbca);
        Button btnRemoveSmartwatch = findViewById(R.id.btn_remove_smartbca);
        Button btnPairing = findViewById(R.id.btn_pairing_smartbca);

        tvStatusSmartwatch = findViewById(R.id.tv_status_smart_bca);

        viewModel = new ViewModelProvider(this).get(SmartBCAViewModel.class);
        viewModel.setCallback(this);

        if (prefConfig.getSmartWatchNodes().isEmpty()) {
            tvStatusSmartwatch.setText(getString(R.string.no_pairing));
            Glide.with(this)
                    .load(R.drawable.icon_no_smartwatch)
                    .into(imgLogo);
            btnRemoveSmartwatch.setVisibility(View.GONE);
            btnPairing.setVisibility(View.VISIBLE);
            btnPairing.setOnClickListener(this);
        } else {
            showNodes(CAPABILITY_1_NAME, CAPABILITY_2_NAME);
            Glide.with(this)
                    .load(R.drawable.icon_smartwatch)
                    .into(imgLogo);
            btnPairing.setVisibility(View.GONE);
            btnRemoveSmartwatch.setVisibility(View.VISIBLE);
            btnRemoveSmartwatch.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pairing_smartbca:
                Intent intent = new Intent(this, ListDevice.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;
            case R.id.btn_remove_smartbca:
                smartwatchDialog = new RemoveSmartwatchDialog(this, getString(R.string.warning_info_remove));
                smartwatchDialog.show(getSupportFragmentManager(), "");
                break;
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
        String data;
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
            data = "Device smartwatch sedang tidak tersambung";
        }
//        else if (nodesList.size() > 1) {
//            data = "Device smartwatch sedang tidak tersambung";
//            Toast.makeText(this, "Device yang terkoneksi hanya boleh 1 device", Toast.LENGTH_SHORT).show();
//        }
        else {
            String node = nodesList.get(0);
            data = prefConfig.getSmartWatchNodes().equals(node) ? "Device smartwatch sudah tersambung" : "Device smartwatch sedang tidak tersambung";
        }
        tvStatusSmartwatch.setText(data);
    }

    @Override
    public void onPositiveButton() {
        if (smartwatchDialog != null && smartwatchDialog.getTag() != null) {
            smartwatchDialog.dismiss();
        }
        viewModel.removeDevice(prefConfig.getAuthenticateID());
    }

    @Override
    public void onNegativeButton() {
        if (smartwatchDialog != null && smartwatchDialog.getTag() != null) {
            smartwatchDialog.dismiss();
        }
    }

    @Override
    public void onRemoveSuccess() {
        prefConfig.removeDevice();
        initVar();
    }
}