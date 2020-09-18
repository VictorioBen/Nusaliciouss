package com.workspace.nusali.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workspace.nusali.Model.PaymentModel;
import com.workspace.nusali.R;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {
    Context context;
    ArrayList<PaymentModel> payList;

    public PaymentAdapter(Context c, ArrayList<PaymentModel> p){
        context = c;
        payList = p;
    }

    @NonNull
    @Override
    public PaymentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_row_payment, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.MyViewHolder holder, final int position) {
        final PaymentModel paymentModel = payList.get(position);
        holder.idOrder.setText(paymentModel.getId().toString());
        holder.nameUser.setText(paymentModel.getNamaPenerima());
        holder.phoneUser.setText(paymentModel.getNomerPenerima());
        holder.jumlahPesan.setText(paymentModel.getJumlah());
        holder.totalHarga.setText(paymentModel.getTotal());
        holder.metode.setText(paymentModel.getMetodeBayar());
        holder.hint.setText(paymentModel.getPetunjuk());
        holder.alamat.setText(paymentModel.getAlamatPenerima());

        boolean isExpanded = payList.get(position).isExpanded();
        holder.expandLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentModel.setExpanded(!paymentModel.isExpanded());
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return payList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idOrder, nameUser, phoneUser, jumlahPesan, totalHarga, metode, hint, alamat;
        LinearLayout expandLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idOrder = itemView.findViewById(R.id.tv_id);
            nameUser = itemView.findViewById(R.id.tv_nama_pembayaran);
            jumlahPesan = itemView.findViewById(R.id.tv_jumlah_pesanan_pembayaran);
            phoneUser = itemView.findViewById(R.id.tv_phone);
            totalHarga = itemView.findViewById(R.id.tv_total_pembayaran);
            metode = itemView.findViewById(R.id.tv_metode_bayar_pembayaran);
            hint = itemView.findViewById(R.id.tv_hint);
            alamat = itemView.findViewById(R.id.tv_alamat);
            expandLayout = itemView.findViewById(R.id.linear_layout_2);

        }
    }
}
