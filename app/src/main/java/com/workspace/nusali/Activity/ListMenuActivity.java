package com.workspace.nusali.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.workspace.nusali.Adapter.ListMenuAdapter;
import com.workspace.nusali.Model.ListMenuModel;
import com.workspace.nusali.R;

import java.util.ArrayList;

public class ListMenuActivity extends AppCompatActivity {

    RecyclerView recyclerListMenu;
    DatabaseReference referenceListMenu;
    ArrayList<ListMenuModel> listMenu;
    private ListMenuAdapter listMenuAdapter;

    String userIdKey = "";
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);


        recyclerListMenu = findViewById(R.id.recycler_list_menu);
        recyclerListMenu.setHasFixedSize(true);
        recyclerListMenu.setLayoutManager(new LinearLayoutManager(this));
        listMenu = new ArrayList<ListMenuModel>();
        Bundle bundle = getIntent().getExtras();
        String jenisMenu = bundle.getString("jenis_menu");
        getSupportActionBar().setTitle(jenisMenu);
        referenceListMenu = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Data").child("Menu");
        Query query = FirebaseDatabase.getInstance().getReference("Data").child("Menu")
                .orderByChild("kategori").equalTo(jenisMenu);
        query.addListenerForSingleValueEvent(valueEventListener);


//        referenceListMenu.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()){
//                    ListMenuModel Listmenu = ds.getValue(ListMenuModel.class);
//                    listMenu.add(Listmenu);
//                }
//                listMenuAdapter = new ListMenuAdapter(ListMenuActivity.this, listMenu);
//                recyclerListMenu.setAdapter(listMenuAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                ListMenuModel Listmenu = ds.getValue(ListMenuModel.class);
                listMenu.add(Listmenu);
            }
            listMenuAdapter = new ListMenuAdapter(ListMenuActivity.this, listMenu);
            recyclerListMenu.setAdapter(listMenuAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                    listMenuAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;

    }



}