package com.example.connectwearable.utils.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.connectwearable.R;


public class RemoveSmartwatchDialog extends DialogFragment implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes_remove_dialog:
                onClick.onPositiveButton();
                break;
            case R.id.btn_no_remove_dialog:
                onClick.onNegativeButton();
                break;
        }
    }

    public interface onClick {
        void onPositiveButton();

        void onNegativeButton();
    }

    private onClick onClick;

    public RemoveSmartwatchDialog(RemoveSmartwatchDialog.onClick onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_remove_smartwatch_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnYes = view.findViewById(R.id.btn_yes_remove_dialog);
        Button btnNo = view.findViewById(R.id.btn_no_remove_dialog);
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
    }
}
