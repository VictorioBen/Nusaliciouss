package com.workspace.adminpanels.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workspace.adminpanels.Adapter.menuAdapter;
import com.workspace.adminpanels.Model.menuModel;
import com.workspace.adminpanels.R;

import java.util.ArrayList;

public class DataMenuActivity extends AppCompatActivity {

    private Toolbar toolbarMenu;
    private RecyclerView rvdMenu;
    private FloatingActionButton fabMenu;
    private DatabaseReference menuRef;
    private ArrayList<menuModel> menuList;
    private menuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_menu);

        init();
        toolbarSet();
        recyclerSet();
        retrieveData();
        fabListener();

    }

    private void recyclerSet() {
        rvdMenu.setHasFixedSize(true);
        rvdMenu.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvdMenu.addItemDecoration(divider);

        menuList = new ArrayList<menuModel>();
    }

    private void toolbarSet() {
        setSupportActionBar(toolbarMenu);
        getSupportActionBar().setTitle("Menu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void init() {
        toolbarMenu = findViewById(R.id.dToolbarMenu);
        fabMenu = findViewById(R.id.fab_addMenu);
        rvdMenu = findViewById(R.id.rvd_Menu);
    }
    private void retrieveData(){
        menuRef = FirebaseDatabase.getInstance().getReference("Data").child("Menu");
        menuRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menuList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    menuModel menuMod = ds.getValue(menuModel.class);
                        menuList.add(menuMod);
                }
                adapter = new menuAdapter(menuList);
                rvdMenu.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void fabListener(){
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(DataMenuActivity.this, DataAddMenuActivity.class);
                startActivity(add);
            }
        });
    }
}