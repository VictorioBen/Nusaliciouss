package com.workspace.adminpanels.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workspace.adminpanels.Adapter.riwayatAdapter;
import com.workspace.adminpanels.Model.riwayat2Model;
import com.workspace.adminpanels.R;

import java.util.ArrayList;

public class RiwayatFragment extends Fragment {
    public static final String EXTRA_UNIX = "extra_unix";
    private RecyclerView rvRiwayat;
    private ArrayList<riwayat2Model> riwayatList;
    private riwayatAdapter adapter;
    private DatabaseReference riwayatRef;
    private String uid;

    public RiwayatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);

        recyclerSet(view);
        retrieveData();
        retrieveIntent();
        return view;
    }

    private void retrieveIntent() {
        Intent intent = ((Activity) getContext()).getIntent();
        String unix = intent.getStringExtra(EXTRA_UNIX);;
        uid = unix;
    }

    private void retrieveData() {
    riwayatRef = FirebaseDatabase.getInstance().getReference("Data").child("Transaksi");
    riwayatRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            riwayatList.clear();
            for (DataSnapshot dataSnapshot1 : dataSnapshot.child(uid).child("Riwayat").getChildren()){
                String uniq = dataSnapshot1.getKey();
                for (DataSnapshot ds : dataSnapshot.child(uid).child("Riwayat").child(uniq).getChildren()){
                    riwayat2Model riwayatMod = ds.getValue(riwayat2Model.class);
                    riwayatList.add(riwayatMod);
                }
            }
            adapter = new riwayatAdapter(riwayatList, getContext());
            rvRiwayat.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(getContext(), "Error in : " + databaseError, Toast.LENGTH_SHORT).show();
        }
    });
    }

    private void recyclerSet(View view) {
        rvRiwayat = view.findViewById(R.id.rvd_riwayat_pesanan);
        rvRiwayat.setHasFixedSize(true);
        rvRiwayat.setLayoutManager(new LinearLayoutManager(getContext()));
        riwayatList = new ArrayList<>();
    }
}