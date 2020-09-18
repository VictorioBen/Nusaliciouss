package com.workspace.adminpanels.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.workspace.adminpanels.MainActivity;
import com.workspace.adminpanels.Model.riwayat2Model;
import com.workspace.adminpanels.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class riwayatAdapter extends RecyclerView.Adapter<riwayatAdapter.myViewHolder> {
    private DatabaseReference dataRef;
    private ArrayList<riwayat2Model> list;
    private Context context;

    public riwayatAdapter(ArrayList<riwayat2Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public riwayatAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardriwayat_pesanan_item, parent, false);
        myViewHolder myHolder = new myViewHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final riwayatAdapter.myViewHolder holder, final int position) {
        final riwayat2Model riMod = list.get(position);
        holder.idPem.setText("#" + riMod.getXkey());
        holder.noPem.setText(riMod.getId().toString());
        holder.namaPaket.setText(riMod.getJudul());
        holder.statusRiwayat.setText(riMod.getStatus());
        holder.tanggalKirim.setText(riMod.getTanggal());
        holder.waktuKirim.setText(riMod.getWaktu());

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(view.getContext())
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new ViewHolder(R.layout.dialog_set_status))
                        .setExpanded(false)
                        .create();

                View holderView = (LinearLayout) dialog.getHolderView();
                final Spinner mSpin = holderView.findViewById(R.id.spin_status);
                TextView selectSpin = holderView.findViewById(R.id.select_status);
                Button btnSave = holderView.findViewById(R.id.btn_save_datapesanan);

                //SpinSettings
                List<String> mUpdate = new ArrayList<>();
                mUpdate.add(0, "Choose status");
                mUpdate.add("Pesanan dalam proses");
                mUpdate.add("Pesanan sudah dikirim");
                mUpdate.add("Pesanan telah selesai");
                ArrayAdapter<String> updateAdapter;
                updateAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, mUpdate);
                updateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpin.setAdapter(updateAdapter);

                Intent intent = ((Activity) context).getIntent();
                final String unix = intent.getStringExtra("unix");

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataRef = FirebaseDatabase.getInstance().getReference("Data").child("Transaksi").child(unix);
                        dataRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dsn : dataSnapshot.getChildren()){
                                    String xkey = riMod.getXkey();
                                    Integer id = riMod.getId();
                                    String idMenu = String.valueOf(id);
                                    String status = mSpin.getSelectedItem().toString();

                                    Map<String, Object> map = new HashMap<>();
                                    map.put("status", status);

                                   FirebaseDatabase.getInstance().getReference("Data").child("Transaksi").child(unix).child("Riwayat")
                                            .child(xkey).child(idMenu).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           Intent main = new Intent(context, MainActivity.class);
                                           context.startActivity(main);
                                        dialog.dismiss();
                                       }
                                   })
                                   .addOnFailureListener(new OnFailureListener() {
                                       @Override
                                       public void onFailure(@NonNull Exception e) {
                                           Toast.makeText(context, "Failed : " + e, Toast.LENGTH_SHORT).show();
                                       }
                                   });
                                }
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView idPem, noPem, namaPaket, statusRiwayat, tanggalKirim, waktuKirim;
        Button btnUpdate;
        ProgressBar pbR;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            idPem = itemView.findViewById(R.id.text_idpembayaran_riwayat);
            noPem = itemView.findViewById(R.id.text_nopem_riwayat);
            namaPaket = itemView.findViewById(R.id.text_napakt_riwayat);
            statusRiwayat = itemView.findViewById(R.id.text_status_riwayat);
            tanggalKirim = itemView.findViewById(R.id.text_tanggal_riwayat);
            waktuKirim = itemView.findViewById(R.id.text_waktu_riwayat);
            btnUpdate = itemView.findViewById(R.id.btn_update_riwayatpesanan);
            pbR = itemView.findViewById(R.id.progressBar_riwayat);
        }
    }
}
