package com.workspace.nusali.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.workspace.nusali.Activity.DetailMenuActivity;
import com.workspace.nusali.Model.ListMenuModel;
import com.workspace.nusali.R;

import java.util.ArrayList;
import java.util.List;

public class ListMenuAdapter extends RecyclerView.Adapter<ListMenuAdapter.MyViewHolder>implements Filterable {
    Context context;
    ArrayList<ListMenuModel> menuList;
    private List<ListMenuModel> listMenuFull;

    public ListMenuAdapter(Context context, ArrayList<ListMenuModel> menuList) {
        this.context = context;
        this.menuList = menuList;
        listMenuFull = new ArrayList<>(menuList);
    }

    @NonNull
    @Override
    public ListMenuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_menu, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListMenuAdapter.MyViewHolder holder, final int position) {
        final ListMenuModel listMenuModel = menuList.get(position);
        holder.judulMenu.setText(listMenuModel.getJudul());
        holder.hargaMenu.setText("Rp."+listMenuModel.getHarga().toString());
        Picasso.get().load(listMenuModel.getGambar()).into(holder.fotoMenu);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMenuActivity.class);
                intent.putExtra("judul", menuList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView fotoMenu;
        TextView judulMenu, hargaMenu;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fotoMenu = itemView.findViewById(R.id.image_list_menu);
            judulMenu = itemView.findViewById(R.id.judul_list_menu);
            hargaMenu = itemView.findViewById(R.id.harga_list_menu);

        }
    }
    @Override
    public Filter getFilter() {
        return menuFilter;
    }

    private Filter menuFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ListMenuModel>filterMenu = new ArrayList<>();

            if(constraint == null || constraint.length() == 0)
                filterMenu.addAll(listMenuFull);
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(ListMenuModel item : listMenuFull){
                    if (item.getJudul().toLowerCase().contains(filterPattern)){
                        filterMenu.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterMenu;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                menuList.clear();
                menuList.addAll((List)results.values);
                notifyDataSetChanged();
        }
    };
}
