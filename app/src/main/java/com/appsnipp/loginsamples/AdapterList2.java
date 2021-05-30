package com.appsnipp.loginsamples;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterList2 extends RecyclerView.Adapter<AdapterList2.ViewHolder>{

    Context context;
    ArrayList<HashMap<String, String>> list_data;

    public AdapterList2(HomeDActivity homeDActivity, ArrayList<HashMap<String, String>> list_data) {
        this.context = homeDActivity;
        this.list_data = list_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtkerjaan.setText(list_data.get(position).get("kerjaan"));
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtkerjaan;

        public ViewHolder(View itemView) {
            super(itemView);

            txtkerjaan = (TextView) itemView.findViewById(R.id.txtkerjaan);

        }
    }
}

