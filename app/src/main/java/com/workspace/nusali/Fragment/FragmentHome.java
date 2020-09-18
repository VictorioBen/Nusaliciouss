package com.workspace.nusali.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.anjlab.android.iab.v3.BillingProcessor;

import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import com.workspace.nusali.Activity.ListMenuActivity;

import com.workspace.nusali.Activity.TopupActivity;
import com.workspace.nusali.Model.UserModel;
import com.workspace.nusali.R;

public class FragmentHome extends Fragment{
//    String userIdKey = "";
//    String userId = "";
    Context context;
    FirebaseAuth firebaseAuth;
    TextView nameUser, saldoUser;
    DatabaseReference referenceUser;
    Task<Void> referenceUpdate;
    UserModel userModel;
    String USER = "";
    Integer saldoAwal = 0;

    private int[] mImages = new int[]{
            R.drawable.spanduk1, R.drawable.spanduk2, R.drawable.spanduk3
    };
    GridLayout homeGrid;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        nameUser = v.findViewById(R.id.name_user);
        saldoUser = v.findViewById(R.id.saldo_user);
        Button btnTopUp = v.findViewById(R.id.btn_top_up);
        getSaldo();

        btnTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent topups = new Intent(getContext(), TopupActivity.class);
                startActivity(topups);
            }
        });

        CarouselView carouselView = v.findViewById(R.id.carousel);
        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);
            }
        });
        //Set GridLayout
        homeGrid = v.findViewById(R.id.homeGrid);
        setSingleEvent(homeGrid);

        return v;
    }

    //Gridlayout Click Listener
    private void setSingleEvent(GridLayout homeGrid) {
        for (int i = 0; i < homeGrid.getChildCount(); i++) {
            final CardView mCardView = (CardView) homeGrid.getChildAt(i);
            final int finall = i;
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finall == 0) {
                        Intent intent = new Intent(getContext(), ListMenuActivity.class);
                        intent.putExtra("jenis_menu", "Nasi Kotak");
                        startActivity(intent);
                    } else if (finall == 1) {
                        Intent intent = new Intent(getContext(), ListMenuActivity.class);
                        intent.putExtra("jenis_menu", "Prasmanan");
                        startActivity(intent);
                    } else if (finall == 2) {
                        Intent intent = new Intent(getContext(), ListMenuActivity.class);
                        intent.putExtra("jenis_menu", "Kantoran");
                        startActivity(intent);
                    } else if (finall == 3) {
                        Intent intent = new Intent(getContext(), ListMenuActivity.class);
                        intent.putExtra("jenis_menu", "Drink");
                        startActivity(intent);
                    } else if (finall == 4) {
                        Intent intent = new Intent(getContext(), ListMenuActivity.class);
                        intent.putExtra("jenis_menu", "Tumpeng");
                        startActivity(intent);
                    } else if (finall == 5) {
                        Intent intent = new Intent(getContext(), ListMenuActivity.class);
                        intent.putExtra("jenis_menu", "Rantang");
                        startActivity(intent);
                    } else if (finall == 6) {
                        Intent intent = new Intent(getContext(), ListMenuActivity.class);
                        intent.putExtra("jenis_menu", "Tradisional");
                        startActivity(intent);
                    } else if (finall == 7) {
                        Toast.makeText(getContext(), "Cooming Soon", Toast.LENGTH_SHORT).show();
                        mCardView.setEnabled(false);
                    }
                }
            });
        }
    }

    public void getSaldo() {
        getUserID();
        referenceUser = FirebaseDatabase.getInstance().getReference("Data").child("User").child(USER).child("pribadi");

        referenceUser.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                saldoUser.setText("Rp."+userModel.getSaldo().toString());
                nameUser.setText("Hi, "+userModel.getName());
                saldoAwal = userModel.getSaldo();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void getUserID(){
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        USER = firebaseUser.getUid();
    }

}
