package com.workspace.adminpanels.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.workspace.adminpanels.Fragments.PembayaranFragment;
import com.workspace.adminpanels.Fragments.PesananFragment;
import com.workspace.adminpanels.Fragments.RiwayatFragment;
import com.workspace.adminpanels.R;

import java.util.ArrayList;
import java.util.Arrays;

public class DataTransaksi extends AppCompatActivity {

    public static final String EXTRA_UNIX = "extra_unix";
    private Toolbar mToolbar;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_transaksi);

        init();
        toolbarSet();
        mTransaction();
    }

    private void toolbarSet() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        getSupportActionBar().setTitle("Transaksi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbarPesanan);
        spinner = findViewById(R.id.spin_transaksi);
    }

    private void mTransaction() {
        String[] mList = {"PEMBAYARAN","PESANAN", "RIWAYAT"};
        ArrayList<String> aL = new ArrayList<>(Arrays.asList(mList));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spin_layout, aL);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Fragment selectFragment = null;
                switch (adapterView.getSelectedItemPosition()){
                    case 0:
                        selectFragment = new PembayaranFragment();
                        break;
                    case 1:
                        selectFragment = new PesananFragment();
                        break;
                    case 2 :
                        selectFragment = new RiwayatFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout, selectFragment).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
            });
    }
}