package com.example.connectwearable.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.wear.widget.WearableRecyclerView;

import com.example.connectwearable.R;
import com.example.connectwearable.utils.Constant;

public class MainAdapter extends WearableRecyclerView.Adapter<MainAdapter.Holder> {

    private String[] menuList;
    private onItemClick onItemClick;

    public MainAdapter(MainAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
        menuList = Constant.menuList;
    }

    public interface onItemClick {
        void onClickAt(int pos);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_menu, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String s = menuList[position];
        holder.tvMenu.setText(s);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClickAt(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.length;
    }

    static class Holder extends WearableRecyclerView.ViewHolder {
        private TextView tvMenu;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvMenu = itemView.findViewById(R.id.recycler_tv_menu);
        }
    }
}
