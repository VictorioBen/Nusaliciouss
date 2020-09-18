package com.workspace.adminpanels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.workspace.adminpanels.Activity.DataIncome;
import com.workspace.adminpanels.Activity.DataMenuActivity;
import com.workspace.adminpanels.Activity.DataOrderActivity;
import com.workspace.adminpanels.Activity.DataTransactionActivity;
import com.workspace.adminpanels.Activity.DataUserActivity;
import com.workspace.adminpanels.Activity.adminLogin;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridDashboard;
    private TextView mUsername;
    private DatabaseReference dRef;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setSingleEvent(gridDashboard);
        logoutClick();
        retrieveData();

        String currentDateTime = java.text.DateFormat.getDateInstance().format(new Date());
        mUsername.setText(currentDateTime);
    }

    private void retrieveData() {
    }

    private void logoutClick() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent log = new Intent(MainActivity.this, adminLogin.class);
                startActivity(log);
            }
        });

    }

    private void init() {
        mUsername = findViewById(R.id.textID);
        gridDashboard = findViewById(R.id.gridL);
        logout = findViewById(R.id.image_admin);
    }

    private void setSingleEvent(GridLayout gridDashboard) {
        for (int i = 0; i < gridDashboard.getChildCount(); i++) {
            CardView iCardview = (CardView) gridDashboard.getChildAt(i);
            final int finals = i;
            iCardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finals == 0) {
                        Intent dataMenu = new Intent(MainActivity.this, DataMenuActivity.class);
                        startActivity(dataMenu);
                    } else if (finals == 1) {
                        Intent dataUser = new Intent(MainActivity.this, DataUserActivity.class);
                        startActivity(dataUser);
                    } else if (finals == 2) {
                        Intent dataIncome = new Intent(MainActivity.this, DataIncome.class);
                        startActivity(dataIncome);
                    } else if (finals == 3) {
                        Intent transaction = new Intent(MainActivity.this, DataTransactionActivity.class);
                        startActivity(transaction);
                    }else if (finals == 4){
                        Intent order = new Intent(MainActivity.this, DataOrderActivity.class);
                        startActivity(order);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
