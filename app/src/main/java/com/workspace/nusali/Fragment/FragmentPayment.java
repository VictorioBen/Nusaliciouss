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
import com.workspace.nusali.Adapter.PaymentAdapter;
import com.workspace.nusali.Model.PaymentModel;
import com.workspace.nusali.R;

import java.util.ArrayList;


public class FragmentPayment extends Fragment implements View.OnClickListener {
    LinearLayout intro;
    PaymentAdapter paymentAdapter;
    RecyclerView recyclerPayment;
    DatabaseReference referencePay;
    ArrayList<PaymentModel> payList;
    FirebaseAuth firebaseAuth;
    String USER = "";
    public FragmentPayment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_payment, container, false);
        getUserID();
        intro = v.findViewById(R.id.introPay);
        recyclerPayment = v.findViewById(R.id.recycler_list_pembayaran);
        Button btnBuy = v.findViewById(R.id.btnBuyPay);
        payList = new ArrayList<>();
        recyclerPayment.setHasFixedSize(true);
        recyclerPayment.setLayoutManager(new LinearLayoutManager(getContext()));
        intro.setVisibility(View.VISIBLE);
        loadPayment();
        btnBuy.setOnClickListener(this);

        return v;
    }

    public void loadPayment(){
        referencePay = FirebaseDatabase.getInstance().getReference("Data").child("Transaksi").child(USER).child("PembayaranSuccess");
        referencePay.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    intro.setVisibility(View.GONE);
                    recyclerPayment.setVisibility(View.VISIBLE);
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        PaymentModel dataPayment = ds.getValue(PaymentModel.class);
                        payList.add(dataPayment);
                        paymentAdapter = new PaymentAdapter(getActivity(), payList);
                        recyclerPayment.setAdapter(paymentAdapter);
                    }
                }
                else{
                    recyclerPayment.setVisibility(View.GONE);
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
        switch (v.getId()){
            case R.id.btnBuyPay:
                Intent intent = new Intent(getContext(), ListMenuActivity.class);
                intent.putExtra("jenis_menu", "Nasi Kotak");
                startActivity(intent);
                break;
        }
    }

    public void getUserID(){
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        USER = firebaseUser.getUid();
    }
}