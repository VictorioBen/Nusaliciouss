package com.workspace.nusali.Fragment;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.workspace.nusali.Activity.DeliveryLocationActivity;
import com.workspace.nusali.Activity.SplashActivity;
import com.workspace.nusali.Activity.customerServiceActivity;
import com.workspace.nusali.Activity.termActivity;
import com.workspace.nusali.MainActivity;
import com.workspace.nusali.R;

import org.w3c.dom.Text;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class FragmentAccount extends Fragment implements View.OnClickListener {
    DatabaseReference referenceAccount;
    TextView namaUser, emailUser, teleponUser;
    LinearLayout delivery, cs, termcondition;
    ImageView imageUser;
    LinearLayout logoutUser;
    FirebaseAuth firebaseAuth;
    String USER = "";

    public FragmentAccount() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        namaUser = v.findViewById(R.id.textNama);
        emailUser = v.findViewById(R.id.textEmail);
        teleponUser = v.findViewById(R.id.textTelp);
        imageUser = v.findViewById(R.id.imageUser);
        delivery = v.findViewById(R.id.delivery_location);
        termcondition = v.findViewById(R.id.term_condition);
        cs = v.findViewById(R.id.customer_service);
        logoutUser = v.findViewById(R.id.exit_account);
        delivery.setOnClickListener(this);
        termcondition.setOnClickListener(this);
        cs.setOnClickListener(this);
        getDataUser();
        logoutUser.setOnClickListener(this);

        return v;
    }

    public void getDataUser() {
        //load data yang ada
        getUserID();
        referenceAccount = FirebaseDatabase.getInstance().getReference("Data").child("User").child(USER).child("pribadi");
        referenceAccount.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    namaUser.setText(dataSnapshot.child("name").getValue().toString());
                    emailUser.setText(dataSnapshot.child("email").getValue().toString());
                    teleponUser.setText(dataSnapshot.child("phone").getValue().toString());
                    Picasso.get().load(dataSnapshot.child("url_foto").getValue().toString()).into(imageUser);
                } else {

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


    public void logoutDataUser(){
        CharSequence[] options = new CharSequence[]
                {
                        "Batal",
                        "Keluar"
                };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Ingin Keluar? ");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                }
                if (i == 1) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getContext(), SplashActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

        });
        builder.show();

    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.delivery_location:
                    Intent move = new Intent(getActivity(), DeliveryLocationActivity.class);
                    startActivity(move);
                    break;
                case R.id.exit_account:
                   logoutDataUser();
                    break;
                case R.id.term_condition:
                    Intent term = new Intent(getContext(), termActivity.class);
                    startActivity(term);
                    break;
                case R.id.customer_service:
                    Intent customer = new Intent(getActivity(), customerServiceActivity.class);
                    startActivity(customer);
                    break;

            }


    }

//    public void rateMe() {
//        try {
//            startActivity(new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("market://details?id=" + Objects.requireNonNull(getActivity()).getPackageName())));
//        } catch (ActivityNotFoundException e) {
//            startActivity(new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("https://play.google.com/store/apps/details?id=com.workspace.nusali" + Objects.requireNonNull(getActivity()).getPackageName())));
//        }
//    }
    //
//    public void getUsernameLocal() {
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(userIdKey, MODE_PRIVATE);
//        userId = sharedPreferences.getString("firebaseKey", "");
//
//    }
}