package com.workspace.adminpanels.Activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workspace.adminpanels.Adapter.incomeAdapter;
import com.workspace.adminpanels.Model.IncomeModel;
import com.workspace.adminpanels.R;

import java.util.ArrayList;

public class DataIncome extends AppCompatActivity {

    private Toolbar incomeToolbar;
    private RecyclerView rvIncome;
    private TextView textTotal;
    DatabaseReference incomeRef;
    ArrayList<IncomeModel> iList;
    incomeAdapter adapter;
    int totalIncome = 0 ;
    String totalHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_income);
        //inisiasi
        init();
        //RecyclerSettings
        recyclerSet();
        //RetrieveData
        retrieveData();
        //ToolbarSettings
        toolbarSet();
    }

    private void toolbarSet() {
        setSupportActionBar(incomeToolbar);
        incomeToolbar.setSubtitleTextColor(getResources().getColor(R.color.colorWhite));
        getSupportActionBar().setTitle("TOTAL INCOME");
        getSupportActionBar().setSubtitle("Laporan hasil pendapatan");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void retrieveData() {
        incomeRef = FirebaseDatabase.getInstance().getReference("Data").child("Pendapatan");
        incomeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String key = ds.getKey();
                    IncomeModel iMods = ds.getValue(IncomeModel.class);
                    iList.add(iMods);
                }
                for (int i = 0; i < iList.size(); i++){
                    totalIncome += iList.get(i).getTotal();
                    totalHarga = Integer.toString(totalIncome);
                }
                textTotal.setText("Rp " + totalHarga);
                adapter = new incomeAdapter(iList);
                rvIncome.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DataIncome.this, "Error " + databaseError, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recyclerSet() {
        rvIncome.setHasFixedSize(true);
        rvIncome.setLayoutManager(new LinearLayoutManager(this));
        iList = new ArrayList<>();
        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvIncome.addItemDecoration(divider);
    }

    private void init() {
        incomeToolbar = findViewById(R.id.toolbar_data_income);
        textTotal = findViewById(R.id.text_total_income_all);
        rvIncome = findViewById(R.id.rvd_income);
    }
}