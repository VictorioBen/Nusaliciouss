package com.workspace.adminpanels.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workspace.adminpanels.Model.IncomeModel;
import com.workspace.adminpanels.R;

import java.util.ArrayList;

public class incomeAdapter extends RecyclerView.Adapter<incomeAdapter.myViewHolder> {
    private Context context;
    private ArrayList<IncomeModel> incomeList;

    public incomeAdapter(Context context) {
        this.context = context;
    }

    public incomeAdapter(ArrayList<IncomeModel> incomeList) {
        this.incomeList = incomeList;
    }

    @NonNull
    @Override
    public incomeAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_data_income, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull incomeAdapter.myViewHolder holder, int position) {
        IncomeModel iMod = incomeList.get(position);
        holder.textId.setText(iMod.getIdPembayaran().toString());
        holder.textTotal.setText("Rp " + iMod.getTotal().toString());
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView textTotal, textId, textTanggal;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.text_id_income);
            textTotal = itemView.findViewById(R.id.text_total_income);

        }
    }
}
