package com.upvmaster.carlos.recetor.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.upvmaster.carlos.recetor.entities.Dieta;
import com.upvmaster.carlos.recetor.fragments.TabDieta_Fragment;

/**
 * Created by Carlos on 03/12/2016.
 */

public class ViewTabDietaAdapter extends FragmentStatePagerAdapter {

    private int num_tabs;
    private Dieta dieta;

    public ViewTabDietaAdapter(FragmentManager fm, int numTabs, Dieta dieta) {
        super(fm);
        this.num_tabs  = numTabs;
        this.dieta = dieta;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                TabDieta_Fragment tab1 = new TabDieta_Fragment();
                tab1.setDiaDieta(dieta.getLst_dias().get(0));
                return tab1;
            case 1:
                TabDieta_Fragment tab2 = new TabDieta_Fragment();
                tab2.setDiaDieta(dieta.getLst_dias().get(1));
                return tab2;
            case 2:
                TabDieta_Fragment tab3 = new TabDieta_Fragment();
                tab3.setDiaDieta(dieta.getLst_dias().get(2));
                return tab3;
            case 3:
                TabDieta_Fragment tab4 = new TabDieta_Fragment();
                tab4.setDiaDieta(dieta.getLst_dias().get(3));
                return tab4;
            case 4:
                TabDieta_Fragment tab5 = new TabDieta_Fragment();
                tab5.setDiaDieta(dieta.getLst_dias().get(4));
                return tab5;
            case 5:
                TabDieta_Fragment tab6 = new TabDieta_Fragment();
                tab6.setDiaDieta(dieta.getLst_dias().get(5));
                return tab6;
            case 6:
                TabDieta_Fragment tab7 = new TabDieta_Fragment();
                tab7.setDiaDieta(dieta.getLst_dias().get(6));
                return tab7;
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return num_tabs;
    }
}
