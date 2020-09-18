package com.workspace.nusali.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import com.workspace.nusali.Activity.DeliveryLocationActivity;
import com.workspace.nusali.Activity.ListMenuActivity;
import com.workspace.nusali.Activity.PaymentActivity;
import com.workspace.nusali.Adapter.ChartAdapter;
import com.workspace.nusali.Model.ChartModel;
import com.workspace.nusali.Model.OrderModel;

import com.workspace.nusali.Model.PaymentModel;
import com.workspace.nusali.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Random;

import static android.content.Context.MODE_PRIVATE;


public class FragmentChart extends Fragment implements View.OnClickListener {
    TextView namaPenerima, alamatPenerima, nomerPenerima, hint, ubahAlamat;
    DatabaseReference referenceChart, referenceDataDelivery;
    DatabaseReference referenceOrder;
    DatabaseReference referencePay;
    FirebaseAuth firebaseAuth;
    Task<Void> referenceRemove;
    RecyclerView recyclerViewChart;
    ArrayList<ChartModel> chartList;
    ChartAdapter chartAdapter;
    int totalKeranjang = 0;
    int jumlahPesan = 0;
    Integer belanjaID = new Random().nextInt();
    String idTransaksi = belanjaID.toString();
    String totalHarga;
    String jumlahBeli;
    String USER = "";
    private LinearLayout intro;
    ScrollView frameChart1;
    CardView frameChart2;
    Button btnBuy;
    public FragmentChart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_chart, container, false);

        getUserID();

        //set button
        intro = v.findViewById(R.id.intro);
        frameChart1 = v.findViewById(R.id.frame_chart1);
        frameChart2 = v.findViewById(R.id.frame_chart2);
        final Button btnProses = v.findViewById(R.id.btn_proses_chart);
        namaPenerima = v.findViewById(R.id.nama_penerima);
        alamatPenerima = v.findViewById(R.id.alamat_penerima);
        nomerPenerima = v.findViewById(R.id.nomer_penerima);
        hint = v.findViewById(R.id.hint);
        ubahAlamat = v.findViewById(R.id.change_location);
        btnBuy = v.findViewById(R.id.btnBuy);
        frameChart1.setVisibility(View.GONE);
        frameChart2.setVisibility(View.GONE);
        btnProses.setVisibility(View.GONE);
        intro.setVisibility(View.VISIBLE);
        getDataDelivery();
        //ganti alamat
        ubahAlamat.setOnClickListener(this);
        //PROSES PEMBAYARAN
        btnProses.setOnClickListener(this);
        //ketika fragment kosong
        btnBuy.setOnClickListener(this);
        //set Recycler
        recyclerViewChart = v.findViewById(R.id.CheckoutRecycler);
        recyclerViewChart.setHasFixedSize(true);
        recyclerViewChart.setLayoutManager(new LinearLayoutManager(getContext()));
        chartList = new ArrayList<>();
        //LOAD RECYCLER KERANJANG
        referenceChart = FirebaseDatabase.getInstance().getReference("Data").child("Keranjang").child(USER);
        referenceChart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    intro.setVisibility(View.GONE);
                    frameChart1.setVisibility(View.VISIBLE);
                    frameChart2.setVisibility(View.VISIBLE);
                    btnProses.setVisibility(View.VISIBLE);

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ChartModel chartModel = dataSnapshot1.getValue(ChartModel.class);
                        chartList.add(chartModel);
                        chartAdapter = new ChartAdapter(getActivity(), chartList);
                        recyclerViewChart.setAdapter(chartAdapter);

                    }

                    for (int i = 0; i < chartList.size(); i++) {
                        totalKeranjang += chartList.get(i).getTotal();
                        TextView tvTotal = v.findViewById(R.id.tv_total_chart);
                        totalHarga = Integer.toString(totalKeranjang);
                        tvTotal.setText(totalHarga);
                        jumlahPesan += chartList.get(i).getJumlah();
                        TextView jumlahItem = v.findViewById(R.id.jumlah_pesanan_chart);
                        jumlahBeli = Integer.toString(jumlahPesan);
                        jumlahItem.setText(jumlahBeli);

                    }

                }
                else{
                    frameChart1.setVisibility(View.GONE);
                    frameChart2.setVisibility(View.GONE);
                    btnProses.setVisibility(View.GONE);
                    intro.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



                return v;

        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


        }


        public void gotoPembayaran() {
            referenceChart = FirebaseDatabase.getInstance().getReference("Data").child("Keranjang").child(USER);
            referenceChart.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Intent intent = new Intent(getContext(), PaymentActivity.class);
                        intent.putExtra("idTrans", idTransaksi);
                        intent.putExtra("pesanan", jumlahBeli);
                        intent.putExtra("total", totalHarga);
                        intent.putExtra("namaPenerima", namaPenerima.getText().toString());
                        intent.putExtra("alamatPenerima", alamatPenerima.getText().toString());
                        intent.putExtra("nomerPenerima", nomerPenerima.getText().toString());
                        intent.putExtra("petunjuk", hint.getText().toString());
                        startActivity(intent);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        public void getDataDelivery() {
            //load data yang ada
            referenceDataDelivery = FirebaseDatabase.getInstance().getReference("Data").child("User").child(USER).child("pengiriman");
            referenceDataDelivery.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        namaPenerima.setText(dataSnapshot.child("namaPenerima").getValue().toString());
                        nomerPenerima.setText(dataSnapshot.child("nomerPenerima").getValue().toString());
                        alamatPenerima.setText(dataSnapshot.child("alamatPenerima").getValue().toString());
                        hint.setText(dataSnapshot.child("petunjuk").getValue().toString());
                    } else {
                        namaPenerima.setText("wegy");
                        nomerPenerima.setText("087788661921");
                        alamatPenerima.setText("jl hj naim");
                        hint.setText("no 32");
                    }
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


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnBuy:
                    Intent intent = new Intent(getContext(), ListMenuActivity.class);
                    intent.putExtra("jenis_menu", "Nasi Kotak");
                    startActivity(intent);
                    break;
                case R.id.change_location:
                    Intent intent1 = new Intent(getContext(), DeliveryLocationActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.btn_proses_chart:
                    gotoPembayaran();
                    break;
            }
    }



    public void goToPesanan() {
        final String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        final SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        final SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());
        referenceOrder = FirebaseDatabase.getInstance().getReference().child("Transaksi").child(USER);
        referenceOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Integer totalBayar = Integer.parseInt(totalHarga);
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ChartModel chartModel = dataSnapshot1.getValue(ChartModel.class);
                    chartList.add(chartModel);
                    for (int i = 0; i < chartList.size(); i++) {
                        Integer idMenu = chartList.get(i).getId();
                        String menuId = String.valueOf(idMenu);
                        String judul = chartList.get(i).getJudul();
                        Integer jumlah = chartList.get(i).getJumlah();
                        String katering = chartList.get(i).getKatering();
                        String tanggal = chartList.get(i).getTanggal();
                        Integer total = chartList.get(i).getTotal();
                        String waktu = chartList.get(i).getWaktu();
                        //                      jumlahPesan += Integer.valueOf(jumlah);

//                                jumlahPesan += chartList.get(i).getTotal();
//                        dataSnapshot.getRef().child("idMenu").setValue(idMenu);
//                        dataSnapshot.getRef().child("judul").setValue(judul);
//                        dataSnapshot.getRef().child("jumlah").setValue(jumlah);
//                        dataSnapshot.getRef().child("katering").setValue(katering);
//                        dataSnapshot.getRef().child("tanggal").setValue(tanggal);
//                        dataSnapshot.getRef().child("total").setValue(total);
//                        dataSnapshot.getRef().child("waktu").setValue(waktu);
                        OrderModel orderModel = new OrderModel(idMenu, judul, jumlah, katering, tanggal, total, waktu);

                        referenceOrder.child("Pesanan").child(idTransaksi + " " + saveCurrentDate + " " + saveCurrentTime).child(menuId).setValue(orderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(getContext(), PaymentActivity.class);
                                intent.putExtra("idTrans", idTransaksi);
                                intent.putExtra("pesanan", jumlahBeli);
                                intent.putExtra("total", totalHarga);
                                startActivity(intent);

//                                referenceRemove = FirebaseDatabase.getInstance().getReference().child("Keranjang").child(userId).removeValue();
//                                Intent intent = new Intent(getContext(), PaymentActivity.class);
//                                startActivity(intent);
//                                intent.putExtra("idTrans", idTransaksi);
//                                intent.putExtra("pesanan", jumlahPesan);
//                                intent.putExtra("total", totalChart);
//                                intent.putExtra("alamatPenerima", namaPenerima.getText().toString());
//                                intent.putExtra("alamatPenerima", alamatPenerima.getText().toString());
//                                intent.putExtra("nomerPenerima", nomerPenerima.getText().toString());
//                                intent.putExtra("petunjuk", hint.getText().toString());

                            }
                        });


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

//
//    public void getUsernameLocal() {
////        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(userIdKey, MODE_PRIVATE);
////        userId = sharedPreferences.getString("firebaseKey", "");
////
////    }

//    public void goToOrder(){
//
//        final String saveCurrentTime, saveCurrentDate;
//
//        Calendar calForDate = Calendar.getInstance();
//        final SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
//        saveCurrentDate = currentDate.format(calForDate.getTime());
//
//        final SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
//        saveCurrentTime = currentTime.format(calForDate.getTime());
//
//
//        referenceOrder = FirebaseDatabase.getInstance().getReference().child("Transaksi").child(userId).child("Pesanan");
//
//        final HashMap<String, Object> orderMap = new HashMap<>();
//        DataSnapshot dataSnapshot = null;
//        referenceOrder = (DatabaseReference) dataSnapshot.getChildren();
//        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//            ChartModel chartModel = dataSnapshot1.getValue(ChartModel.class);
//            chartList.add(chartModel);
//            for (int i = 0; i < chartList.size(); i++) {
//                Integer idMenu = chartList.get(i).getId();
//                String menuId = String.valueOf(idMenu);
//                String judul = chartList.get(i).getJudul();
//                Integer jumlah = chartList.get(i).getJumlah();
//                String jumlahBeli = String.valueOf(jumlah);
//                String katering = chartList.get(i).getKatering();
//                String tanggal = chartList.get(i).getTanggal();
//                Integer total = chartList.get(i).getTotal();
//                String total2 = String.valueOf(total);
//                String waktu = chartList.get(i).getWaktu();
//
//                orderMap.put("idMenu", menuId);
//                orderMap.put("judul", judul);
//                orderMap.put("jumlah", jumlahBeli);
//                orderMap.put("katering", katering);
//                orderMap.put("tanggal", tanggal);
//                orderMap.put("total", total2);
//                orderMap.put("waktu", waktu);
//
//                referenceOrder.child(idTransaksi + " " + saveCurrentDate).child(menuId + " " + saveCurrentTime).updateChildren(orderMap);
//                Intent intent = new Intent(getContext(), PaymentActivity.class);
//                startActivity(intent);
//            }
//        }
//    }


}