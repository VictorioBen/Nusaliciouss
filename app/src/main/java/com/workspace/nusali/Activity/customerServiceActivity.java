package com.workspace.nusali.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.workspace.nusali.R;

public class customerServiceActivity extends AppCompatActivity {

    Toolbar toolbarcs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);

        toolbarcs = findViewById(R.id.toolbar_contact_us);
        setSupportActionBar(toolbarcs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Customer Service");
    }
}