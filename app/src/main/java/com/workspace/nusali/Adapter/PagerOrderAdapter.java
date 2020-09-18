package com.workspace.nusali.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.workspace.nusali.Fragment.FragmentOrderComplete;
import com.workspace.nusali.Fragment.FragmentOrderPending;

public class PagerOrderAdapter extends FragmentStatePagerAdapter {
    int jmlTab;

    public PagerOrderAdapter(@NonNull FragmentManager fm, int tabCount) {
        super(fm);
        this.jmlTab = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FragmentOrderPending fragmentOrderPending = new FragmentOrderPending();
                return fragmentOrderPending;
            case 1:
                FragmentOrderComplete fragmentOrderComplete = new FragmentOrderComplete();
                return fragmentOrderComplete;
        }

        return null;
    }

    @Override
    public int getCount() {
        return jmlTab;
    }
}
