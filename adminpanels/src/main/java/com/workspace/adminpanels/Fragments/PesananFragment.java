package com.workspace.adminpanels.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workspace.adminpanels.Adapter.pesananAdapter;
import com.workspace.adminpanels.Model.dataPesanModel;
import com.workspace.adminpanels.R;

import java.util.ArrayList;

public class PesananFragment extends Fragment {

    public static final String EXTRA_UNIX = "extra_unix";
    private RecyclerView rvPesanan;
    private ArrayList<dataPesanModel> pesanlist;
    private pesananAdapter adapterPesanan;
    private DatabaseReference pesananRef;
    public String uid;

    public PesananFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pesanan, container, false);

        init(v);
        recyclerSet();
        dataSet();
        return v;
    }

    private void dataSet() {
        Intent intent = ((Activity) getContext()).getIntent();
        String unix = intent.getStringExtra(EXTRA_UNIX);;
        uid = unix;
        pesananRef = FirebaseDatabase.getInstance().getReference("Data").child("Transaksi");
        pesananRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pesanlist.clear();
                for (DataSnapshot ds:dataSnapshot.child(uid).child("Pesanan").getChildren()){
                    String keym = ds.getKey();
                    for (DataSnapshot dsn : dataSnapshot.child(uid).child("Pesanan").child(keym).getChildren()){
                        dataPesanModel dataMod = dsn.getValue(dataPesanModel.class);
                        dataMod.key = keym;
                        pesanlist.add(dataMod);
                    }
                }
                adapterPesanan = new pesananAdapter(pesanlist, getContext());
                rvPesanan.setAdapter(adapterPesanan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void recyclerSet() {
    rvPesanan.setHasFixedSize(true);
    rvPesanan.setLayoutManager(new LinearLayoutManager(getContext()));
    pesanlist = new ArrayList<>();
    }

    private void init(View v) {
    rvPesanan = v.findViewById(R.id.rvd_pesanan);
    }

}