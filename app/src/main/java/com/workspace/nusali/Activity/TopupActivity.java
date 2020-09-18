package com.workspace.nusali.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workspace.nusali.Fragment.FragmentHome;
import com.workspace.nusali.MainActivity;
import com.workspace.nusali.Model.UserModel;
import com.workspace.nusali.R;

import java.util.ArrayList;
import java.util.List;

public class TopupActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    private Button btn_5000;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private Toolbar toolbarT;
    private ImageButton backs;
    BillingProcessor bp;
    Task<Void> updateRef;
    UserModel userModel;
    String USER = "";
    Integer saldoAwal = 0;
    TextView saldo, nama,termTopUp;
    LinearLayout term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);

        init();
        checkSaldo();
        btnBack();

        //Billing Processor
        bp = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6XYKgUmqbH7ice5eoYUWL6+cgfSwxpoc6y1xVdsKUnrWWn/rrwggHmiSIqQ+Z4OwnnSDk4Vs6L5PqURejLxyMHG7cOYvnsE4v1Dsk38q8x7o3o867lbRYBWJR3AXgj9u9oZTxZqP8NZwtpEEyMe+nTOLKQWeJVwHyQOaq8Tp9S/RXBJD4J2tplhcFqWrtEkcEFAuLR6m3CoB9BlnHszUc2BEkALFkAj1qK4e6tTlea3ioPFpCylXiV/0UFh+lHU8GJ3Bp65Qx2MJS96oUC4QtBEo3KVyyhu0Gg+DJsPiWYPLIz4qtD86cwl7CszW04JdyE4t6vACB1fXWUkpuZw0CwIDAQAB", this);
        bp.initialize();

        minSaldo();
    }

    private void minSaldo() {
        btn_5000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp.purchase(TopupActivity.this, "1000000");
                bp.consumePurchase("1000000");
            }
        });
    }



    private void init() {
        backs = findViewById(R.id.btn_back_topup);
        nama = findViewById(R.id.name_activity);
        saldo = findViewById(R.id.saldo_activity);
        btn_5000 = findViewById(R.id.btn_topup_5000);
        termTopUp = findViewById(R.id.term_top_up);
        term = findViewById(R.id.term_top_up2);
    }

    private void btnBack() {
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(TopupActivity.this, FragmentHome.class);
                startActivity(home);
            }
        });
    }

    private void checkSaldo() {
        getUser();
        userRef = FirebaseDatabase.getInstance().getReference("Data").child("User").child(USER).child("pribadi");

        userRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                saldo.setText("Rp."+userModel.getSaldo().toString());
                nama.setText("Hi, "+userModel.getName());
                saldoAwal = userModel.getSaldo();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUser() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        USER = firebaseUser.getUid();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        if (bp.isPurchased(productId))
        {
            bp.consumePurchase("1000000");
            Integer tambah = 1000000;
            Integer saldoUpdate = saldoAwal + tambah;
            updateRef = FirebaseDatabase.getInstance().getReference("Data").child("User").child(USER).child("pribadi").child("saldo").setValue(saldoUpdate);
        }
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        Toast.makeText(this, "Topup Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Toast.makeText(this, "Failed Topup", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {
    }

    @Override
    protected void onDestroy() {
        if (bp != null)
        {
            bp.release();
        }
        super.onDestroy();
    }
}