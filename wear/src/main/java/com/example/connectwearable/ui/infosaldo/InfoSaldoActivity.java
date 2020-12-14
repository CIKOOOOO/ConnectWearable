package com.example.connectwearable.ui.infosaldo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.connectwearable.R;
import com.example.connectwearable.utils.BaseActivity;
import com.example.connectwearable.utils.Constant;
import com.example.connectwearable.utils.Utils;
import com.example.connectwearable.model.User;

public class InfoSaldoActivity extends BaseActivity implements IInfoSaldoCallback {

    private InfoSaldoViewModel viewModel;
    private TextView tvAccountNumber, tvSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_saldo);
        initVar();
    }

    private void initVar() {
        TextView tvTimeInfo = findViewById(R.id.tv_time_info_saldo);
        tvAccountNumber = findViewById(R.id.tv_account_number_info_saldo);
        tvSaldo = findViewById(R.id.tv_sisa_saldo_info_saldo);

        viewModel = new ViewModelProvider(this).get(InfoSaldoViewModel.class);
        viewModel.setCallback(this);

        viewModel.infoSaldo(prefConfig.getUserID(), "123123");

        tvTimeInfo.setText(Utils.getTime(Constant.DATE_FORMAT_1));
    }

    @Override
    public void onResponse(User user) {
        String accountNumber = "No. Rekening : " + user.getAccount_number();
        String saldo = "Rp " + Utils.priceFormat(user.getSaldo());

        tvAccountNumber.setText(accountNumber);
        tvSaldo.setText(saldo);
    }
}