package com.workspace.nusali.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.workspace.nusali.Model.ScreenItemModel;
import com.workspace.nusali.R;

import java.util.List;

public class PagerIntroAdapter extends PagerAdapter {
    Context context;
    List<ScreenItemModel> listScreen;

    public PagerIntroAdapter(Context context, List<ScreenItemModel> listScreen) {
        this.context = context;
        this.listScreen = listScreen;
    }

    @Override
    public int getCount() {
        return listScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);

    }

    @NonNull
    @Override
    public Object instantiateItem (ViewGroup container, int position){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View screen = layoutInflater.inflate(R.layout.layout_screen, null);

        ImageView imgSlide =  screen.findViewById(R.id.intro_img);
        TextView title = screen.findViewById(R.id.intro_title);
        TextView desc = screen.findViewById(R.id.intro_description);

        title.setText(listScreen.get(position).getTitle());
        desc.setText(listScreen.get(position).getDescription());
        imgSlide.setImageResource(listScreen.get(position).getScreenImg());

        container.addView(screen);
        return  screen;
    }


}
