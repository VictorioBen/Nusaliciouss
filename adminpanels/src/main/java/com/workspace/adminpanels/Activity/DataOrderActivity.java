package com.workspace.adminpanels.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workspace.adminpanels.Adapter.orderAdapter;
import com.workspace.adminpanels.Model.pembayaran2Model;
import com.workspace.adminpanels.R;

import java.util.ArrayList;

public class DataOrderActivity extends AppCompatActivity {
    
    private Toolbar toolbarOrder;
    private RecyclerView rvdOrder;
    private ArrayList<pembayaran2Model> pemlist;
    private orderAdapter adapter;
    private DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        
        init();
        toolbarSet();
        recyclerSet();
        retrieveData();
    }

    private void retrieveData() {
        orderRef = FirebaseDatabase.getInstance().getReference("Data").child("Transaksi");
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String usrId = ds.getKey();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child(usrId).child("Pembayaran").getChildren()){
                        pembayaran2Model pemMod = dataSnapshot1.getValue(pembayaran2Model.class);
                        pemMod.uid = usrId;
                        pemlist.add(pemMod);
                    }
                }
                adapter = new orderAdapter(pemlist);
                adapter.notifyDataSetChanged();
                rvdOrder.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DataOrderActivity.this, "Error in : "+ databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void recyclerSet() {
        rvdOrder.setHasFixedSize(true);
        rvdOrder.setLayoutManager(new LinearLayoutManager(this));
        pemlist = new ArrayList<>();
    }

    private void toolbarSet() {
        setSupportActionBar(toolbarOrder);
        getSupportActionBar().setTitle("Order");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        toolbarOrder = findViewById(R.id.toolbar_order);
        rvdOrder = findViewById(R.id.rvd_order);
    }
}