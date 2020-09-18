package com.workspace.adminpanels.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workspace.adminpanels.Activity.DataTransaksi;
import com.workspace.adminpanels.Model.idModel;
import com.workspace.adminpanels.R;

import java.util.ArrayList;

public class transactionAdapter extends RecyclerView.Adapter<transactionAdapter.myHolder> {
    ArrayList<idModel> callList;

    public transactionAdapter(ArrayList<idModel> mlist) {
        this.callList = mlist;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardtransaction_item, parent, false);
        myHolder myHolder = new myHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        final idModel IdMod = callList.get(position);
        holder.textId.setText(IdMod.getKey());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noUnix = IdMod.getKey();
                Intent send = new Intent(view.getContext(), DataTransaksi.class);
                Bundle bundle = new Bundle();
                bundle.putString("unix", noUnix);
                send.putExtra(DataTransaksi.EXTRA_UNIX, noUnix);
                send.putExtras(bundle);
                view.getContext().startActivity(send);
            }
        });
    }

    @Override
    public int getItemCount() {
        return callList.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {
        TextView textId;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.noUnix);
        }
    }
}
