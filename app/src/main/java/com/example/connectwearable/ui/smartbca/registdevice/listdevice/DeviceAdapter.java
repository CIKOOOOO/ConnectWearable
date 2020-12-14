package com.example.connectwearable.ui.smartbca.registdevice.listdevice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connectwearable.R;
import com.example.connectwearable.model.Smartwatch;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.Holder> {

    private List<Smartwatch> stringList;
    private onDevicesClick onDevicesClick;
    private int lastPosition;
    private Context context;

    public DeviceAdapter(DeviceAdapter.onDevicesClick onDevicesClick) {
        this.onDevicesClick = onDevicesClick;
        stringList = new ArrayList<>();
        lastPosition = -1;
    }

    public void setStringList(List<Smartwatch> stringList) {
        this.stringList = stringList;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public interface onDevicesClick {
        void onCLick(Smartwatch smartwatch);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_devices, parent, false);
        setContext(parent.getContext());
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Smartwatch s = stringList.get(position);
        holder.tvName.setText(s.getName());

        int background = s.isChoose() ? R.drawable.rectangle_half_stroke_rounded_gray : R.drawable.rectangle_half_stroke_rounded_blue;

        holder.tvName.setBackground(context.getResources().getDrawable(background));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastPosition != -1) {
                    Smartwatch smartwatch = stringList.get(lastPosition);
                    smartwatch.setChoose(false);
                    stringList.set(lastPosition, smartwatch);
                    notifyItemChanged(lastPosition);
                }
                s.setChoose(true);
                stringList.set(position, s);
                lastPosition = position;
                notifyItemChanged(lastPosition);
                onDevicesClick.onCLick(s);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private TextView tvName;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.recycler_tv_name_devices_list);
        }
    }
}
