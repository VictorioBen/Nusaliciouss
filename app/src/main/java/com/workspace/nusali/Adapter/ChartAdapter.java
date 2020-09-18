package com.workspace.nusali.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.workspace.nusali.Activity.LoginActivity;
import com.workspace.nusali.Fragment.FragmentDialogDetailMenu;
import com.workspace.nusali.MainActivity;
import com.workspace.nusali.Model.ChartModel;
import com.workspace.nusali.R;

import java.util.ArrayList;


public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.MyViewHolder> {
//    String userIdKey = "";
    //    String userId = "";
    public Context context;
    private ArrayList<ChartModel> chartList;
    Task<Void> referenceDelete;
    FirebaseAuth firebaseAuth;
    String USER = "";

    public ChartAdapter(Context c, ArrayList<ChartModel> p) {
        context = c;
        chartList = p;
    }

    @NonNull
    @Override
    public ChartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_row_chart, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ChartAdapter.MyViewHolder holder, final int position) {
        getUserID();
        final ChartModel chartModel = chartList.get(position);
        holder.judul.setText(chartModel.getJudul());
        holder.jumlah.setText(chartModel.getJumlah().toString()+"Pax.");
        holder.harga.setText(chartModel.getTotal().toString());
        holder.tanggal.setText(chartModel.getTanggal());
        holder.waktu.setText(chartModel.getWaktu());
        holder.katering.setText(chartModel.getKatering());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence[] options = new CharSequence[]
                        {
                                "Remove"
                        };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Options: ");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       referenceDelete = FirebaseDatabase.getInstance().getReference("Data").child("Keranjang").child(USER).child(String.valueOf(chartModel.getId())).removeValue();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                       Toast.makeText(context, "Removed  " +chartModel.getJudul(), Toast.LENGTH_SHORT).show();

                   }

                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return chartList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView judul, harga, jumlah, tanggal, waktu, katering;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.title_chart);
            harga = itemView.findViewById(R.id.price_chart);
            jumlah = itemView.findViewById(R.id.quantity_chart);
            tanggal = itemView.findViewById(R.id.date_chart);
            waktu = itemView.findViewById(R.id.time_chart);
            katering = itemView.findViewById(R.id.katering_chart);
        }
    }


    public void getUserID(){
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        USER = firebaseUser.getUid();
    }
//
//    public void getUsernameLocal() {
//
//        SharedPreferences sharedPreferences = context.getSharedPreferences(userIdKey, Context.MODE_PRIVATE);
//        userId = sharedPreferences.getString("firebaseKey", "");
//
//    }




}
