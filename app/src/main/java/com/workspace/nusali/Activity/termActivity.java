package com.workspace.nusali.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.workspace.nusali.R;

public class termActivity extends AppCompatActivity {

    Toolbar toolbarTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        toolbarTerm = findViewById(R.id.toolbar_term);
        setSupportActionBar(toolbarTerm);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Term and Condition");
    }
}