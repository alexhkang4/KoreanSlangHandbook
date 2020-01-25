package com.alex.koreanslanghandbook.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.alex.koreanslanghandbook.R;
import com.alex.koreanslanghandbook.data.DataCache;

public class SettingsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        final DataCache dataCache = DataCache.getInstance();
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Switch extremeSwitch = view.findViewById(R.id.extreme_slang_toggle);
        if (dataCache.isExtreme()) {
            extremeSwitch.setChecked(true);
        }
        extremeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                if (isChecked) {
                    dataCache.setExtreme(true);
                    editor.putBoolean("EXTREME", true);
                    editor.apply();
                }
                else {
                    dataCache.setExtreme(false);
                    editor.putBoolean("EXTREME", false);
                    editor.apply();
                }
            }
        });
        ConstraintLayout changeTheme = view.findViewById(R.id.change_theme_layout_id);
        changeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Coming soon...", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}