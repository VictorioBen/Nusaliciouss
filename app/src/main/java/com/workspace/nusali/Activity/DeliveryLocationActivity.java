package com.workspace.nusali.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workspace.nusali.MainActivity;
import com.workspace.nusali.R;

import java.util.HashMap;

public class DeliveryLocationActivity extends AppCompatActivity {
   FirebaseAuth firebaseAuth;
    String USER = "";
    TextView namaPenerima, nomerPenerima, alamatPenerima, petunjuk;
    DatabaseReference referenceDelivery, referenceData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_location);
        getUserID();
        namaPenerima = findViewById(R.id.nama_delivery);
        nomerPenerima = findViewById(R.id.nomer_delivery);
        alamatPenerima = findViewById(R.id.alamat_delivery);
        petunjuk = findViewById(R.id.hint_delivery);

        final Button btnUpdate = findViewById(R.id.btn_delivery);


        //load data yang ada
        referenceData = FirebaseDatabase.getInstance().getReference("Data").child("User").child(USER).child("pengiriman");
        referenceData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    namaPenerima.setText(dataSnapshot.child("namaPenerima").getValue().toString());
                    nomerPenerima.setText(dataSnapshot.child("nomerPenerima").getValue().toString());
                    alamatPenerima.setText(dataSnapshot.child("alamatPenerima").getValue().toString());
                    petunjuk.setText(dataSnapshot.child("petunjuk").getValue().toString());
                }
                else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                btnUpdate.setEnabled(true);
            }
        });
    }

    public void save(){
        referenceDelivery = FirebaseDatabase.getInstance().getReference("Data").child("User");

        final HashMap<String, Object> deliveryMap = new HashMap<>();
        deliveryMap.put("namaPenerima", namaPenerima.getText().toString());
        deliveryMap.put("nomerPenerima", nomerPenerima.getText().toString());
        deliveryMap.put("alamatPenerima", alamatPenerima.getText().toString());
        deliveryMap.put("petunjuk", petunjuk.getText().toString());

        referenceDelivery.child(USER).child("pengiriman").updateChildren(deliveryMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        CharSequence[] options = new CharSequence[]
                        {
                                "Ok"
                        };
                AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryLocationActivity.this);
                builder.setTitle("Update Berhasil !");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DeliveryLocationActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                });
                builder.show();
            }
        });

    }

    public void getUserID(){
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        USER = firebaseUser.getUid();
    }

//    public void getUsernameLocal() {
//
//        SharedPreferences sharedPreferences = getSharedPreferences(userIdKey, MODE_PRIVATE);
//        userId = sharedPreferences.getString("firebaseKey", "");
//
//    }
}