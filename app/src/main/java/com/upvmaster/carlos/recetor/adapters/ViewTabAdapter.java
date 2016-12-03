package com.upvmaster.carlos.recetor.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.upvmaster.carlos.recetor.fragments.TabReceiptAlph_Fragment;
import com.upvmaster.carlos.recetor.fragments.TabReceiptGroup_Fragment;

/**
 * Created by Carlos on 03/12/2016.
 */

public class ViewTabAdapter extends FragmentStatePagerAdapter {

    private int num_tabs;

    public ViewTabAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.num_tabs  = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                TabReceiptAlph_Fragment tab1 = new TabReceiptAlph_Fragment();
                return tab1;
            case 1:
                TabReceiptGroup_Fragment tab2 = new TabReceiptGroup_Fragment();
                return tab2;
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return num_tabs;
    }
}
