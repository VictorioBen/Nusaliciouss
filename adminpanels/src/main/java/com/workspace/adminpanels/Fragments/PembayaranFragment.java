package com.workspace.adminpanels.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workspace.adminpanels.Adapter.pembayaranAdapter;
import com.workspace.adminpanels.Model.pembayaranModel;
import com.workspace.adminpanels.R;

import java.util.ArrayList;

public class PembayaranFragment extends Fragment {

    public static final String EXTRA_UNIX = "extra_unix";
    RecyclerView rvPembayaran;
    ArrayList<pembayaranModel> pembayaranList;
    DatabaseReference dbRef;
    pembayaranAdapter adapter;
    public String uid;

    public PembayaranFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pembayaran, container, false);

        init(v);
        //get Data Firebase
        retrieveData();
        retrieveIntent();
        //Recycler Setting
        recyclerSettings();
        return v;
    }
    private void init(View v) {
        rvPembayaran = v.findViewById(R.id.rvd_pembayaran);
    }
    private void recyclerSettings(){
        rvPembayaran.setHasFixedSize(true);
        rvPembayaran.setLayoutManager(new LinearLayoutManager(getContext()));
        pembayaranList = new ArrayList<>();
        RecyclerView.ItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvPembayaran.addItemDecoration(divider);
    }
    private void retrieveIntent() {
        Intent intent = ((Activity) getContext()).getIntent();
        String unix = intent.getStringExtra(EXTRA_UNIX);;
        uid = unix;
    }
    private void retrieveData(){

        dbRef = FirebaseDatabase.getInstance().getReference("Data").child("Transaksi");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pembayaranList.clear();
                for (DataSnapshot ds : dataSnapshot.child(uid).child("Pembayaran").getChildren()) {
                    pembayaranModel pembayaranMod = ds.getValue(pembayaranModel.class);
                    pembayaranList.add(pembayaranMod);
                }
                adapter = new pembayaranAdapter(pembayaranList, getContext());
                rvPembayaran.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error in : " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}