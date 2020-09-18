package com.workspace.nusali.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workspace.nusali.Adapter.ChartAdapter;
import com.workspace.nusali.MainActivity;
import com.workspace.nusali.Model.ChartModel;
import com.workspace.nusali.Model.OrderModel;
import com.workspace.nusali.Model.UserModel;
import com.workspace.nusali.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {
    String USER = "";
    FirebaseAuth firebaseAuth;
    ArrayList<ChartModel> chartList;

    DatabaseReference referenceOrder, referenceChart, referenceUser;
    DatabaseReference referencePayment;
    Task<Void> referenceRemove;
    TextView totalTagihan, jumlahPesanan, totalBayar, saldoUser, bayarBank;
    String idTransaksi, totalHarga, jumlahItem, namaPenerima, alamatPenerima, nomerPenerima, petunjuk;
    RecyclerView recyclerViewChart;
    ChartAdapter chartAdapter;
    Button btnGpay, btnMpay;
    Integer userBalance = 0;
    Integer priceTotal = 0;
    Integer sisaBalance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getUserID();
        totalTagihan = findViewById(R.id.total_tagihan_payment);
        jumlahPesanan = findViewById(R.id.jumlah_item_payment4);
        totalBayar = findViewById(R.id.total_bayar_payment);
        saldoUser = findViewById(R.id.saldo_anda);
        bayarBank = findViewById(R.id.total_bank_payment);

        Bundle bundle = getIntent().getExtras();
        idTransaksi = bundle.getString("idTrans");
//        idbayar.setText(idTransaksi);
        totalHarga = bundle.getString("total");
        jumlahItem = bundle.getString("pesanan");
        namaPenerima = bundle.getString("namaPenerima");
        alamatPenerima = bundle.getString("alamatPenerima");
        nomerPenerima = bundle.getString("nomerPenerima");
        petunjuk = bundle.getString("petunjuk");
        totalTagihan.setText(totalHarga);
        jumlahPesanan.setText(jumlahItem);
        totalBayar.setText(totalHarga);
        bayarBank.setText(totalHarga);
        getSaldoUser();

        chartList = new ArrayList<>();
         btnGpay = findViewById(R.id.btn_g_pay);
        Button mPay = findViewById(R.id.btn_m_pay);
        btnMpay = findViewById(R.id.btn_m_pay);
        // inisiasi harga
        priceTotal = Integer.parseInt(totalHarga);

        btnMpay.setOnClickListener(this);

        btnGpay.setOnClickListener(this);

        //set Recycler
        recyclerViewChart = findViewById(R.id.CheckoutRecycler);
        recyclerViewChart.setHasFixedSize(true);
        recyclerViewChart.setLayoutManager(new LinearLayoutManager(this));
        chartList = new ArrayList<>();
       recycler();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_g_pay:
                 {
                     goToPesanan();
                }
                break;

            case R.id.btn_m_pay:
                Toast.makeText(PaymentActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void getSaldoUser(){
        referenceUser = FirebaseDatabase.getInstance().getReference("Data").child("User").child(USER).child("pribadi");

        referenceUser.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               UserModel userModel = dataSnapshot.getValue(UserModel.class);
               saldoUser.setText(userModel.getSaldo().toString());
               userBalance = userModel.getSaldo();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void recycler(){
        //LOAD RECYCLER KERANJANG
        referenceChart = FirebaseDatabase.getInstance().getReference("Data").child("Keranjang").child(USER);
        referenceChart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ChartModel chartModel = dataSnapshot1.getValue(ChartModel.class);
                    chartList.add(chartModel);
                    chartAdapter = new ChartAdapter(PaymentActivity.this, chartList);
                    recyclerViewChart.setAdapter(chartAdapter);

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void goToPesanan() {
            if( priceTotal > userBalance){
                Toast.makeText(PaymentActivity.this, "Saldo tidak cukup", Toast.LENGTH_SHORT).show();
            }
            else if (priceTotal <= userBalance){
                referenceOrder = FirebaseDatabase.getInstance().getReference("Data").child("Transaksi").child(USER);
                referenceOrder.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String saveCurrentTime, saveCurrentDate;

                        Calendar calForDate = Calendar.getInstance();
                        final SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                        saveCurrentDate = currentDate.format(calForDate.getTime());

                        final SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                        saveCurrentTime = currentTime.format(calForDate.getTime());
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            ChartModel chartModel = dataSnapshot1.getValue(ChartModel.class);
                            chartList.add(chartModel);
                            for (int i = 0; i < chartList.size(); i++) {
                                Integer idMenu = chartList.get(i).getId();
                                String menuId = String.valueOf(idMenu);
                                String judul = chartList.get(i).getJudul();
                                final Integer jumlah = chartList.get(i).getJumlah();
                                String katering = chartList.get(i).getKatering();
                                String tanggal = chartList.get(i).getTanggal();
                                Integer total = chartList.get(i).getTotal();
                                String waktu = chartList.get(i).getWaktu();

                                OrderModel orderModel = new OrderModel(idMenu, judul, jumlah, katering, tanggal, total, waktu);
                                referenceOrder.child("Pesanan").child(idTransaksi).child(menuId).setValue(orderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        referencePayment = FirebaseDatabase.getInstance().getReference("Data").child("Transaksi").child(USER).child("Pembayaran");
                                        final HashMap<String, Object> paymentMap = new HashMap<>();
                                        Integer idPembayaran = Integer.parseInt(idTransaksi);
                                        paymentMap.put("id", idPembayaran);
                                        paymentMap.put("jumlah", jumlahItem);
                                        paymentMap.put("total", totalHarga);
                                        paymentMap.put("namaPenerima", namaPenerima);
                                        paymentMap.put("alamatPenerima", alamatPenerima);
                                        paymentMap.put("nomerPenerima", nomerPenerima);
                                        paymentMap.put("petunjuk", petunjuk);
                                        String metodeBayar = "Gpay";
                                        paymentMap.put("metodeBayar", metodeBayar);
                                        paymentMap.put("tanggalBayar", saveCurrentDate);
                                        paymentMap.put("jamBayar", saveCurrentTime);
                                        referencePayment.child(idTransaksi).updateChildren(paymentMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                referenceRemove = FirebaseDatabase.getInstance().getReference("Data").child("Keranjang").child(USER).removeValue();

                                            }

                                        });
                                    }
                                });



                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                referenceUser = FirebaseDatabase.getInstance().getReference("Data").child("User").child(USER).child("pribadi").child("saldo");
                sisaBalance = userBalance - priceTotal;
                referenceUser.setValue(sisaBalance);
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(PaymentActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }


    }





    public void getUserID(){
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        USER = firebaseUser.getUid();
    }

//    public void getUsernameLocal() {
//        SharedPreferences sharedPreferences = getSharedPreferences(userIdKey, MODE_PRIVATE);
//        userId = sharedPreferences.getString("firebaseKey", "");
//
//    }
//    public void getDataPembayaran() {
//
//        referencePayment = FirebaseDatabase.getInstance().getReference().child("Transaksi").child(USER).child("Pembayaran").child(idTransaksi);
//        referencePayment.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                jumlahPesanan.setText(dataSnapshot.child("jumlah").getValue().toString());
//                totalTagihan.setText(dataSnapshot.child("total").getValue().toString());
//                totalBayar.setText(dataSnapshot.child("total").getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }
}