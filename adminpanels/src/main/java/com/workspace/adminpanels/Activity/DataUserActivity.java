package com.workspace.adminpanels.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workspace.adminpanels.Adapter.userData;
import com.workspace.adminpanels.Model.userModel;
import com.workspace.adminpanels.R;

import java.util.ArrayList;

public class DataUserActivity extends AppCompatActivity {

    private Toolbar dToolbar;
    private RecyclerView rvdUser;
    private DatabaseReference dataRef;
    private userData userAdapter;
    private ArrayList<userModel> userModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_user);

        //Inisiasi id
        init();
        //Toolbar settings
        toolbarSet();
        //Recycler settings
        recyclerSet();
        //Get Data Firebase
        retrieveData();

    }
    //Inisiasi
    private void init() {
        rvdUser = findViewById(R.id.rvd_user);
        dToolbar = findViewById(R.id.dToolbar);

    }
    //ToolbarSet
    private void toolbarSet() {
        setSupportActionBar(dToolbar);
        getSupportActionBar().setTitle("Data User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    //RecyclerSet
    private void recyclerSet() {
        rvdUser.setHasFixedSize(true);
        rvdUser.setLayoutManager(new LinearLayoutManager(this));
        userModels = new ArrayList<userModel>();
        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvdUser.addItemDecoration(divider);
    }
    //Data Firebase
    private void retrieveData(){
        dataRef = FirebaseDatabase.getInstance().getReference("Data").child("User");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModels.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String data = dataSnapshot1.getKey();
                    userModel userMod = dataSnapshot1.child("pribadi").getValue(userModel.class);
                    userModels.add(userMod);
                }
                userAdapter = new userData(userModels);
                rvdUser.setAdapter(userAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       // searchData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search . . .");
        searchView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}