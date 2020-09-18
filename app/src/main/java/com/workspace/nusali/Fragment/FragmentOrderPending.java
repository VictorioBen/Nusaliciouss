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
import com.workspace.nusali.Model.OrderModel;
import com.workspace.nusali.R;

import java.util.ArrayList;


public class FragmentOrderPending extends Fragment implements View.OnClickListener {
    LinearLayout intro;
    OrderPendingAdapter orderAdapter;
    RecyclerView recyclerOrder;
    DatabaseReference referenceOrder;
    ArrayList<OrderModel> orderList;
    FirebaseAuth firebaseAuth;
    String USER = "";

    public FragmentOrderPending() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_order_pending, container, false);
        getUserID();
        intro = v.findViewById(R.id.intro);
        Button btnBuy = v.findViewById(R.id.btnBuyOrder);
        recyclerOrder = v.findViewById(R.id.recycler_list_order);
        orderList = new ArrayList<>();
        recyclerOrder.setHasFixedSize(true);
        recyclerOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        btnBuy.setOnClickListener(this);
        intro.setVisibility(View.VISIBLE);
        loadData();
        return v;
    }

    public void loadData() {
        referenceOrder = FirebaseDatabase.getInstance().getReference("Data").child("Transaksi").child(USER).child("Pesanan");
        referenceOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                if (dataSnapshot.exists()) {
                    intro.setVisibility(View.GONE);
                    recyclerOrder.setVisibility(View.VISIBLE);
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String key = ds.getKey();
                        for (DataSnapshot ds1 : dataSnapshot.child(key).getChildren()) {
                            OrderModel dataOrder = ds1.getValue(OrderModel.class);
                            orderList.add(dataOrder);
                            orderAdapter = new OrderPendingAdapter(getActivity(), orderList);
                            recyclerOrder.setAdapter(orderAdapter);
                        }
                    }
                } else {
                    recyclerOrder.setVisibility(View.GONE);
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
            case R.id.btnBuyOrder:
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