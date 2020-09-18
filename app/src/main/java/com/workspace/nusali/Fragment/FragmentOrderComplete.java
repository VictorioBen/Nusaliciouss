package com.workspace.nusali.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workspace.nusali.Activity.ListMenuActivity;
import com.workspace.nusali.Adapter.OrderPendingAdapter;
import com.workspace.nusali.Adapter.OrderSuccessAdapter;
import com.workspace.nusali.Model.OrderModel;
import com.workspace.nusali.Model.OrderSelesai;
import com.workspace.nusali.R;

import java.util.ArrayList;


public class FragmentOrderComplete extends Fragment implements View.OnClickListener {
    DatabaseReference referenceOrder;
    OrderSuccessAdapter orderAdapter;
    Button btnBuy;
    FirebaseAuth firebaseAuth;
    String USER = "";
    LinearLayout intro;
    RecyclerView recyclerRiwayat;
    ArrayList<OrderSelesai> orderList;
    public FragmentOrderComplete() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_order_complete, container, false);
        getUserID();
        btnBuy = v.findViewById(R.id.btnBuyComplete);
         intro = v.findViewById(R.id.introC);
         recyclerRiwayat = v.findViewById(R.id.recycler_riwayat);
        orderList = new ArrayList<>();
         recyclerRiwayat.setHasFixedSize(true);
         recyclerRiwayat.setLayoutManager(new LinearLayoutManager(getContext()));
         intro.setVisibility(View.VISIBLE);
        btnBuy.setOnClickListener(this);
        loadData();
        return v;
    }

    public void loadData() {
        referenceOrder = FirebaseDatabase.getInstance().getReference("Data").child("Transaksi").child(USER).child("Riwayat");
        referenceOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                if (dataSnapshot.exists()) {
                    intro.setVisibility(View.GONE);
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String key = ds.getKey();
                        for (DataSnapshot ds1 : dataSnapshot.child(key).getChildren()) {
                            OrderSelesai dataOrder = ds1.getValue(OrderSelesai.class);
                            orderList.add(dataOrder);
                            orderAdapter = new OrderSuccessAdapter(getActivity(), orderList);
                            recyclerRiwayat.setAdapter(orderAdapter);
                        }
                    }
                } else {
                    recyclerRiwayat.setVisibility(View.GONE);
                    intro.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuyComplete:
                Intent intent = new Intent(getContext(), ListMenuActivity.class);
                intent.putExtra("jenis_menu", "Nasi Kotak");
                startActivity(intent);
                break;
        }
    }

    public void getUserID() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        USER = firebaseUser.getUid();
    }
}