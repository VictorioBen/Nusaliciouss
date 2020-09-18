package com.workspace.nusali.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import com.workspace.nusali.Fragment.FragmentDatePicker;
import com.workspace.nusali.Fragment.FragmentDialogDetailMenu;
import com.workspace.nusali.Model.ListMenuModel;
import com.workspace.nusali.R;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;
//ih
public class DetailMenuActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String USER = "";
    FirebaseAuth firebaseAuth;
    Toolbar detailToolbar;
    CardView cardTanggal;
    TextView judulHalaman, namaKatering, jenisMenu, judulMenu, descMenu, hargaMenu, totalHarga, minimalBeli, keteranganMenu;
    EditText tanggal, waktu, jumlahPesanan;
    ImageView fotoMenu, btnMinus, btnPlus;
    Button btnKeranjang;
    Integer jumlahBeli = 1;
    Integer total = 0;
    Integer belanjaID = new Random().nextInt();
    String idMenu = belanjaID.toString();
    LoadingDialog loadingDialog;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);

        //Toolbar
        detailToolbar = findViewById(R.id.toolbar_detail_menu);
        setSupportActionBar(detailToolbar);
        getSupportActionBar().setTitle("Makanan");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getUserID();
         loadingDialog = new LoadingDialog(DetailMenuActivity.this);
        //TextView
        //judulHalaman = findViewById(R.id.tv_judul_menu);
        namaKatering = findViewById(R.id.nama_katering);
        jenisMenu = findViewById(R.id.jenis_menu);
        judulMenu = findViewById(R.id.judul_detail_menu);
        descMenu = findViewById(R.id.desc_detail_menu);
        hargaMenu = findViewById(R.id.harga_detail_menu);
        totalHarga = findViewById(R.id.total_price);
        minimalBeli = findViewById(R.id.tv_minimal_beli);
        keteranganMenu = findViewById(R.id.text_keterangan_menu);
        //Edit Text
        tanggal = findViewById(R.id.tv_tanggal);
        waktu = findViewById(R.id.tv_time);
        jumlahPesanan = findViewById(R.id.jumlah_pesanan);
        //ImageView
        fotoMenu = findViewById(R.id.image_detail_menu);
        btnMinus = findViewById(R.id.btn_minus);
        btnPlus = findViewById(R.id.btn_plus);
        //Button
        btnKeranjang = findViewById(R.id.btn_chart);
        //CardView
        cardTanggal = findViewById(R.id.card_tanggal);


        //PARCELABLE
        Intent intent = getIntent();
        ListMenuModel listMenuModel = intent.getParcelableExtra("judul");
        //Setting judul
        String judul = Objects.requireNonNull(listMenuModel).getJudul();
        judulMenu.setText(judul);
        //Setting nama katering
        String katering = listMenuModel.getKatering();
        namaKatering.setText(katering);
        //setting harga
        String harga = listMenuModel.getHarga().toString();
        final int hargaDetailMenu = Integer.parseInt(harga);
        hargaMenu.setText("Rp." +Integer.toString(hargaDetailMenu)+ ",-");
        //setting keterangan
        String keterangan = listMenuModel.getKeterangan();
        keteranganMenu.setText(keterangan);
        //setting desc
        String desc = listMenuModel.getDesc();
        descMenu.setText(desc);
        //setting fotoMenu
        String gambar = listMenuModel.getGambar();
        Picasso.get().load(gambar).into(fotoMenu);
        //setting jumlah beli
        String minimalOrder = listMenuModel.getMinimal().toString();
        final int minimalBelanja = Integer.parseInt(minimalOrder);
        final int detailMinimalOrder = Integer.parseInt(minimalOrder);
        minimalBeli.setText("Min" +Integer.toString(detailMinimalOrder)+ " Porsi");
        //Setting Jumlah Pesanan
        btnMinus.setEnabled(false);
        waktu.setEnabled(false);
        tanggal.setEnabled(false);

        //atur jumlah beli
        jumlahPesanan.setText(String.valueOf(detailMinimalOrder));
        jumlahPesanan.setEnabled(false);
        String jumlahEdit = jumlahPesanan.getText().toString();
        jumlahBeli = Integer.parseInt(jumlahEdit);
        //setting total harga
        total = jumlahBeli * hargaDetailMenu;
        totalHarga.setText(Integer.toString(total));

        //Setting tanggal
       cardTanggal.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DialogFragment datePicker = new FragmentDatePicker();
               datePicker.show(getSupportFragmentManager(),"date picker");
           }
       });

        //Setting button tambah pesanan
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlahBeli += 1;
                jumlahPesanan.setText(jumlahBeli.toString());
                if (jumlahBeli > 1) {
                    btnMinus.setEnabled(true);
                }

                total = jumlahBeli * hargaDetailMenu;
                totalHarga.setText(Integer.toString(total));

            }
        });

        //Setting button minus pesanan
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlahBeli -= 1;
                jumlahPesanan.setText(jumlahBeli.toString());
                if (jumlahBeli <= detailMinimalOrder)
                    btnMinus.setEnabled(false);
                total = jumlahBeli * hargaDetailMenu;
                totalHarga.setText(Integer.toString(total));
            }

        });

        //Setting button masuk keranjang dengan mangambil uid User dan memberikan uid item keranjang
        btnKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggalKirim = tanggal.getText().toString();
                String waktuKirim = waktu.getText().toString();
                if(tanggalKirim.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Tanggal Kosong ! ", Toast.LENGTH_SHORT).show();
                }
                else if (waktuKirim.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Waktu Kosong ! ", Toast.LENGTH_SHORT).show();
                }
                else if (jumlahBeli < minimalBelanja){
                    Toast.makeText(getApplicationContext(), "Minimal Beli kurang ! ", Toast.LENGTH_SHORT).show();
                }
                else if(jumlahBeli <= minimalBelanja){
                    addToCartList();
                }
                else{
                    addToCartList();
                }
            }
        });




    }

    private void addToCartList() {
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference("Data").child("Keranjang").child(USER).child(idMenu);
        Integer menuId = Integer.parseInt(idMenu);
        cartListRef.getRef().child("id").setValue(menuId);
        cartListRef.getRef().child("judul").setValue(judulMenu.getText().toString());
        cartListRef.getRef().child("jumlah").setValue(jumlahBeli);
        String hargaTotal = (String) totalHarga.getText();
        Integer totalBayar = Integer.parseInt(hargaTotal);
        cartListRef.getRef().child("total").setValue(totalBayar);
        cartListRef.getRef().child("katering").setValue(namaKatering.getText().toString());
        cartListRef.getRef().child("tanggal").setValue(tanggal.getText().toString());
        cartListRef.getRef().child("waktu").setValue(waktu.getText().toString());
//        cartListRef.getRef().child("dateorder").setValue(saveCurrentDate);
//        cartListRef.getRef().child("timeorder").setValue(saveCurrentTime);

        openDialog();

//        final HashMap<String, Object> cartMap = new HashMap<>();
//        cartMap.put("id", idMenu);
//        cartMap.put("judul", judulMenu.getText().toString());
//        cartMap.put("jumlah", jumlahPesanan.getText().toString());
//        cartMap.put("total", totalHarga.getText());
//        cartMap.put("tanggal", tanggal.getText().toString());
//        cartMap.put("waktu", waktu.getText().toString());
//        cartMap.put("katering", namaKatering.getText().toString());
//        cartMap.put("dateOrder", saveCurrentDate);
//        cartMap.put("timeOrder", saveCurrentTime);
//
//        cartListRef.child(userId).child(idMenu).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                openDialog();
//            }
//        });

    }

    public void openDialog(){
        loadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        }, 1000);
        FragmentDialogDetailMenu fragmentDialogDetailMenu = new FragmentDialogDetailMenu();
        fragmentDialogDetailMenu.show(getSupportFragmentManager(), "Success");

    }


    public void getUserID(){
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        USER = firebaseUser.getUid();
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        tanggal.setText(currentDateString);
    }

    public void checkButton(View v){
        boolean checked =  ((RadioButton) v).isChecked();

        switch (v.getId()){
            case R.id.radio_siang:
                if(checked){
                    waktu.setText("09.00-12.00");
                }
                break;
            case R.id.radio_sore:
                if(checked){
                    waktu.setText("16.00-19.00");
                }
                break;
        }
    }
    //    public void getUsernameLocal() {
//
//        SharedPreferences sharedPreferences = getSharedPreferences(userIdKey, MODE_PRIVATE);
//        userId = sharedPreferences.getString("firebaseKey", "");
//
//    }
}