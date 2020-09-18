package com.workspace.adminpanels.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workspace.adminpanels.MainActivity;
import com.workspace.adminpanels.R;

public class adminRegister extends AppCompatActivity {

    private DatabaseReference regisRef;
    private Button regisAdm;
    private TextInputEditText txName, txUsername, txPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        init();
        regisOnclick();
    }

    private void init() {
        regisAdm = findViewById(R.id.btn_regisAdmin);
        txName = findViewById(R.id.txtSurename);
        txUsername = findViewById(R.id.txtUsername);
        txPasswd = findViewById(R.id.txtPassword);
    }

    private void regisOnclick() {
    regisAdm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String surename = txName.getText().toString();
            String username = txUsername.getText().toString();
            String password = txPasswd.getText().toString();

            if (TextUtils.isEmpty(surename)){
                Toast.makeText(adminRegister.this, "Input Name", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(username)){
                Toast.makeText(adminRegister.this, "Input Username", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(password) || password.length() <6){
                Toast.makeText(adminRegister.this, "Input Password", Toast.LENGTH_LONG).show();
            }else {

                regisRef = FirebaseDatabase.getInstance().getReference("Data").child("Admin").child(username);
                regisRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("surename").setValue(txName.getText().toString().trim());
                        dataSnapshot.getRef().child("username").setValue(txUsername.getText().toString().trim());
                        dataSnapshot.getRef().child("password").setValue(txPasswd.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(adminRegister.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                Intent regis = new Intent(adminRegister.this, MainActivity.class);
                startActivity(regis);
                finish();
            }

        }
    });
    }
}