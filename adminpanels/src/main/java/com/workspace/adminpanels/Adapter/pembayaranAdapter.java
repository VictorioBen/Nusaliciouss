package com.workspace.adminpanels.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.workspace.adminpanels.Model.incomePembayaran;
import com.workspace.adminpanels.Model.pembayaran2Model;
import com.workspace.adminpanels.Model.pembayaranModel;
import com.workspace.adminpanels.R;

import java.util.ArrayList;
import java.util.List;

public class pembayaranAdapter extends RecyclerView.Adapter<pembayaranAdapter.pembayaranHolder> implements Filterable {

    private DatabaseReference pembayaranRef;
    private ArrayList<pembayaranModel> pembayaranList;
    private ArrayList<pembayaranModel> listFull;
    private Context context;

    public pembayaranAdapter(ArrayList<pembayaranModel> pembayaranList, Context context) {
        this.pembayaranList = pembayaranList;
        this.context = context;
    }

    @NonNull
    @Override
    public pembayaranAdapter.pembayaranHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardpembayaran_item, parent, false);
        pembayaranHolder mHolder = new pembayaranHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final pembayaranAdapter.pembayaranHolder holder, final int position) {
        final pembayaranModel pembayaranMod = pembayaranList.get(position);

        holder.IdBayar.setText("#" + pembayaranMod.getId());
        holder.namaPembayar.setText(pembayaranMod.getNamaPenerima());
        holder.nomorPembayar.setText(pembayaranMod.getnomerPenerima());
        holder.jumlahBayar.setText(pembayaranMod.getJumlah());
        holder.totalBayar.setText("Rp " + pembayaranMod.getTotal());
        holder.metodeBayar.setText(pembayaranMod.getMetodeBayar());
        holder.petunjukBayar.setText(pembayaranMod.getPetunjuk());
        holder.alamatBayar.setText(pembayaranMod.getAlamatPenerima());
        holder.btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new ViewHolder(R.layout.dialog_set))
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();

                View holderView = (LinearLayout) dialog.getHolderView();
                Button btnSave = holderView.findViewById(R.id.btn_save_datapesanan);
                Button btnDelete = holderView.findViewById(R.id.btn_delete_datapesanan);

                Intent intent = ((Activity) context).getIntent();
                final String unix = intent.getStringExtra("unix");

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pembayaranRef = FirebaseDatabase.getInstance().getReference("Data").child("Transaksi").child(unix);
                        pembayaranRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Integer idPembayaran = pembayaranMod.getId();
                                    final String idPem = String.valueOf(idPembayaran);
                                    String namaPembayar = pembayaranMod.getNamaPenerima();
                                    String nomorPembayar = pembayaranMod.getnomerPenerima();
                                    String jumlah = pembayaranMod.getJumlah();
                                    String totalBayar = pembayaranMod.getTotal();
                                    Integer total = Integer.valueOf(totalBayar);
                                    String metodeBayar = pembayaranMod.getMetodeBayar();
                                    String petunjuk = pembayaranMod.getPetunjuk();
                                    String alamatBayar = pembayaranMod.getAlamatPenerima();
                                    String tanggalBayar = pembayaranMod.getTanggalBayar();

                                    pembayaran2Model pemMod = new pembayaran2Model(idPembayaran, namaPembayar, nomorPembayar, alamatBayar, petunjuk, jumlah, totalBayar, metodeBayar, tanggalBayar);

                                    FirebaseDatabase.getInstance().getReference("Data").child("Transaksi").child(unix)
                                            .child("PembayaranSuccess").child(idPem).setValue(pemMod);

                                    incomePembayaran inMod = new incomePembayaran(idPembayaran, total, tanggalBayar);
                                    FirebaseDatabase.getInstance().getReference("Data").child("Pendapatan").child(idPem).setValue(inMod);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        dialog.dismiss();
                    }
                });
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pembayaranRef = FirebaseDatabase.getInstance().getReference("Data").child("Transaksi").child(unix).child("Pembayaran");
                        pembayaranRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    Integer idPembayaran = pembayaranMod.getId();
                                    final String idPem = String.valueOf(idPembayaran);
                                    FirebaseDatabase.getInstance().getReference("Data").child("Transaksi").child(unix).child("Pembayaran").child(idPem).removeValue();
                                }
                                dialog.dismiss();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                dialog.show();
            }
        });

        boolean isExpanded = pembayaranList.get(position).isExpanded();
        holder.expandLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pembayaranModel mPembayaranMod = pembayaranList.get(position);
                mPembayaranMod.setExpanded(!mPembayaranMod.isExpanded());
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pembayaranList.size();
    }

    @Override
    public Filter getFilter() {
        return filters;
    }
    private Filter filters = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<pembayaranModel> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() ==0){
                filteredList.addAll(listFull);
            }else {
                String filterPatern = charSequence.toString().toLowerCase().trim();
                for (pembayaranModel item : listFull){
                    if (item.getId().toString().contains(filterPatern) || item.getNamaPenerima().toLowerCase().contains(filterPatern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            pembayaranList.clear();
            pembayaranList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class pembayaranHolder extends RecyclerView.ViewHolder {
        TextView namaPembayar, nomorPembayar, jumlahBayar, metodeBayar, alamatBayar, petunjukBayar, totalBayar, IdBayar, btnSelesai;
        LinearLayout expandLayout;
        CardView cardView;
        public pembayaranHolder(@NonNull View itemView) {
            super(itemView);
            namaPembayar = itemView.findViewById(R.id.textNamaPembayaran);
            nomorPembayar = itemView.findViewById(R.id.textNoTelpPembayaran);
            jumlahBayar = itemView.findViewById(R.id.textJumlahPembayaran);
            metodeBayar = itemView.findViewById(R.id.textMetodePembayaran);
            totalBayar = itemView.findViewById(R.id.textTotalPembayaran);
            alamatBayar = itemView.findViewById(R.id.textAlamatPembayaran);
            petunjukBayar = itemView.findViewById(R.id.textPetunjukPembayaran);
            expandLayout = itemView.findViewById(R.id.linear_layout_2);
            IdBayar = itemView.findViewById(R.id.textID);
            cardView = itemView.findViewById(R.id.card_pembayaran_item);
            btnSelesai = itemView.findViewById(R.id.btn_selesai);
        }
    }
}
