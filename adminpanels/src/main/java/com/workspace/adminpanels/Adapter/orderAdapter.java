package com.workspace.adminpanels.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.workspace.adminpanels.Model.pembayaran2Model;
import com.workspace.adminpanels.R;

import java.util.ArrayList;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.myViewHolder> {

    private DatabaseReference orderRef;
    private ArrayList<pembayaran2Model> list;

    public orderAdapter(ArrayList<pembayaran2Model> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public orderAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderAdapter.myViewHolder holder, int position) {
        pembayaran2Model pembayaranMod = list.get(position);
        holder.uid.setText(pembayaranMod.getUid());
        holder.idpembayar.setText(String.valueOf(pembayaranMod.getId()));
        holder.penerima.setText(pembayaranMod.getNamaPenerima());
        holder.jumlah.setText(pembayaranMod.getJumlah());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView uid, idpembayar, penerima, jumlah;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            uid = itemView.findViewById(R.id.text_uid);
            idpembayar = itemView.findViewById(R.id.text_id_pembayaran);
            penerima = itemView.findViewById(R.id.text_penerima);
            jumlah = itemView.findViewById(R.id.text_jumlah_pesan);
        }
    }
}
