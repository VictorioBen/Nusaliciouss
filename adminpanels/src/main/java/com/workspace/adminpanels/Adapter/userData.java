package com.workspace.adminpanels.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workspace.adminpanels.Model.userModel;
import com.workspace.adminpanels.R;

import java.util.ArrayList;
import java.util.List;

public class userData extends RecyclerView.Adapter<userData.MviewHolder> implements Filterable {

    ArrayList<userModel> userModels;
    ArrayList<userModel> fullUser;

    public userData(ArrayList<userModel> userModels) {
        this.userModels = userModels;
        this.fullUser = new ArrayList<>(userModels);
    }

    @NonNull
    @Override
    public userData.MviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MviewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.carduser_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull userData.MviewHolder holder, int position) {

        userModel userMod = userModels.get(position);

        holder.textNamaUser.setText(userMod.getName());
        holder.textEmailUser.setText(userMod.getEmail());
        holder.textPhoneUser.setText(userMod.getPhone());
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<userModel> filterList = new ArrayList<>();
            if (charSequence == null || charSequence.length() ==0 ){
                filterList.addAll(fullUser);
            }else {
                String filterPatern = charSequence.toString().toLowerCase().trim();
                for (userModel item : fullUser){
                    if (item.getName().toLowerCase().contains(filterPatern) || item.getEmail().toLowerCase().contains(filterPatern) || item.getPhone().toLowerCase().contains(filterPatern)){
                        filterList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            userModels.clear();
            userModels.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class MviewHolder extends RecyclerView.ViewHolder {
        TextView textNamaUser, textEmailUser, textPhoneUser;
        public MviewHolder(@NonNull View itemView) {
            super(itemView);
            textNamaUser = itemView.findViewById(R.id.textNameUser);
            textEmailUser = itemView.findViewById(R.id.textEmailUser);
            textPhoneUser = itemView.findViewById(R.id.textPhoneUser);
        }
    }
}
