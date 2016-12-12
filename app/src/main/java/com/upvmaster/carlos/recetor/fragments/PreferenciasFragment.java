package com.upvmaster.carlos.recetor.fragments;


import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.upvmaster.carlos.recetor.R;


public class PreferenciasFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.mis_preferencias);
    }
}
