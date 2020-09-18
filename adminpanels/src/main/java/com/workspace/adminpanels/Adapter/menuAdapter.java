package com.workspace.adminpanels.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;
import com.workspace.adminpanels.Model.menuModel;
import com.workspace.adminpanels.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class menuAdapter extends RecyclerView.Adapter<menuAdapter.myHolder>{

    private Context ctx;
    private ArrayList<menuModel> menulist;
    private DatabaseReference dRef;

    public menuAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public menuAdapter(ArrayList<menuModel> menulist) {
        this.menulist = menulist;
    }

    @NonNull
    @Override
    public menuAdapter.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardmenu_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final menuAdapter.myHolder holder, final int position) {
        final menuModel menuMod = menulist.get(position);
        Picasso.get().load(menuMod.getGambar()).fit().into(holder.picPreview);
        holder.txNama.setText(menuMod.getJudul());
        holder.txDesc.setText(menuMod.getDesc());
        holder.txHarga.setText("Harga : Rp " + menuMod.getHarga().toString());
        holder.txMinimum.setText(menuMod.getMinimal().toString());
        holder.txKet.setText(menuMod.getKeterangan().toString());
        holder.txKategori.setText("Kategori : " + menuMod.getKategori());
        holder.txKatering.setText(menuMod.getKatering());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(view.getContext())
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new ViewHolder(R.layout.dialog_edit_menu))
                        .setExpanded(false)
                        .create();

                View holderView = (LinearLayout) dialog.getHolderView();
                final TextInputEditText judul = holderView.findViewById(R.id.te_nama_paket);
                final TextInputEditText desc = holderView.findViewById(R.id.te_desc);
                final TextInputEditText harga = holderView.findViewById(R.id.te_harga);
                Button btnsave = holderView.findViewById(R.id.btn_save_ubah);

                judul.setText(menuMod.getJudul());
                desc.setText(menuMod.getDesc());
                harga.setText(menuMod.getHarga().toString());

                btnsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Map<String, Object> map = new HashMap<>();
                        map.put("judul", judul.getText().toString().trim());
                        map.put("desc", desc.getText().toString().trim());
                        map.put("harga", Integer.valueOf(harga.getText().toString()));

                        dRef = FirebaseDatabase.getInstance().getReference("Data").child("Menu");
                        Query query = dRef.orderByChild("judul").equalTo(menuMod.getJudul());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String uid = ds.getRef().getKey();

                                    FirebaseDatabase.getInstance().getReference("Data").child("Menu").child(uid)
                                            .updateChildren(map);
                                    dialog.dismiss();
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

        final String currentJudul = menuMod.getJudul();
        dRef = FirebaseDatabase.getInstance().getReference("Data").child("Menu");
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Query query = dRef.orderByChild("judul").equalTo(currentJudul);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(view.getContext(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return menulist.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {
        TextView txNama, txDesc, txHarga, txKategori, txKatering, txMinimum, txKet;
        ImageView picPreview;
        Button btnDelete;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            txNama = itemView.findViewById(R.id.textNamaMenu);
            txDesc = itemView.findViewById(R.id.textDescMenu);
            txHarga = itemView.findViewById(R.id.textHargaMenu);
            txMinimum = itemView.findViewById(R.id.textMinimum);
            txKet = itemView.findViewById(R.id.textKeterangann);
            txKategori = itemView.findViewById(R.id.textCat);
            txKatering = itemView.findViewById(R.id.textCatering);
            picPreview = itemView.findViewById(R.id.imgMenuItem2);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
