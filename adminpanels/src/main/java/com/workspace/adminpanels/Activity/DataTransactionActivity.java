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
import com.workspace.adminpanels.Adapter.transactionAdapter;
import com.workspace.adminpanels.Model.idModel;
import com.workspace.adminpanels.R;

import java.util.ArrayList;

public class DataTransactionActivity extends AppCompatActivity {


    private idModel iMod;
    private Toolbar toolbarTransaction;
    private RecyclerView rvTransaction;
    private transactionAdapter adapters;
    private ArrayList<idModel> IdList;
    private DatabaseReference transactionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_transaction);
        init();
        toolbarSet();
        recyclerSet();
        dataSet();
    }

    private void dataSet() {
        transactionRef = FirebaseDatabase.getInstance().getReference("Data").child("Transaksi");
        transactionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                IdList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    iMod = ds.getValue(idModel.class);
                    iMod.key = ds.getKey();
                    IdList.add(iMod);
                    for (int i = 0; i < IdList.size(); i++){
                        iMod = IdList.get(i);
                    }
                }
                adapters = new transactionAdapter(IdList);
                rvTransaction.setAdapter(adapters);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DataTransactionActivity.this, "Error in : "+databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void recyclerSet() {
    rvTransaction.setHasFixedSize(true);
    rvTransaction.setLayoutManager(new LinearLayoutManager(this));
    IdList = new ArrayList<>();
    }

    private void toolbarSet() {
    setSupportActionBar(toolbarTransaction);
    getSupportActionBar().setTitle("Transaksi");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void init() {
    toolbarTransaction = findViewById(R.id.toolbar_transaction);
    rvTransaction = findViewById(R.id.rvd_transaction);
    }
}