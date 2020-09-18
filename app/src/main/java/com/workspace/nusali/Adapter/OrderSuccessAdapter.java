package com.workspace.nusali.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workspace.nusali.Model.OrderModel;
import com.workspace.nusali.Model.OrderSelesai;
import com.workspace.nusali.R;

import java.util.ArrayList;

public class OrderSuccessAdapter extends RecyclerView.Adapter<OrderSuccessAdapter.MyViewHolder> {
    Context context;
    ArrayList<OrderSelesai> orderList;

    public OrderSuccessAdapter(Context c, ArrayList<OrderSelesai> p){
        context = c;
        orderList = p;
    }

    @NonNull
    @Override
    public OrderSuccessAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_row_order_success, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderSuccessAdapter.MyViewHolder holder, int position) {
        final OrderSelesai orderModel = orderList.get(position);
        holder.idOrder.setText(orderModel.getId().toString());
        holder.titleMenu.setText(orderModel.getJudul());
        holder.jumlahPesan.setText(orderModel.getJumlah().toString()+ "Pax.");
        holder.tanggal.setText(orderModel.getTanggal());
        holder.waktu.setText(orderModel.getWaktu());
        holder.status.setText(orderModel.getStatus());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idOrder, titleMenu, jumlahPesan, tanggal, waktu, status, harga;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idOrder = itemView.findViewById(R.id.nomer_order_c);
            titleMenu = itemView.findViewById(R.id.title_order_c);
            jumlahPesan = itemView.findViewById(R.id.quantity_order_c);
            tanggal = itemView.findViewById(R.id.date_delivery_c);
            waktu = itemView.findViewById(R.id.time_delivery_c);
            status = itemView.findViewById(R.id.delivery_order_c);
            harga = itemView.findViewById(R.id.price_order_c);
        }
    }
}
