package com.alex.koreanslanghandbook.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.alex.koreanslanghandbook.ListActivity;
import com.alex.koreanslanghandbook.R;
import com.alex.koreanslanghandbook.data.DataCache;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<Level> levelArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        DataCache dataCache = DataCache.getInstance();
        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        dataCache.setExtreme(pref.getBoolean("EXTREME", false));

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (levelArrayList.size() == 0) {
            if (!dataCache.isExtreme()) {
                levelArrayList.add(new Level("TABOO LVL:", "MILD SLANG", 1));
                levelArrayList.add(new Level("TABOO LVL:", "MEDIUM SLANG", 2));
                levelArrayList.add(new Level("TABOO LVL:", "SPICY SLANG", 3));
            }
            else {
                levelArrayList.add(new Level("TABOO LVL:", "MILD SLANG", 1));
                levelArrayList.add(new Level("TABOO LVL:", "MEDIUM SLANG", 2));
                levelArrayList.add(new Level("TABOO LVL:", "SPICY SLANG", 3));
                levelArrayList.add(new Level("TABOO LVL:", "EXTREME SLANG", 4));
            }

        }
        else {
            if (dataCache.isExtreme()) {
                if (levelArrayList.size() == 3) {
                    levelArrayList.add(new Level("LEVEL 4:", "EXTREME SLANG", 4));
                }
            }
        }
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Korean Slang Handbook");
        ListView listView = view.findViewById(R.id.list_view);
        LevelAdapter levelAdapter = new LevelAdapter();
        listView.setAdapter(levelAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("NUM_PEPPER", levelArrayList.get(position).getNumPepper());
                startActivity(intent);
            }
        });
        return view;
    }

    public class Level {
        Level(String level, String description, int numPepper) {
            setLevel(level);
            setDescription(description);
            setNumPepper(numPepper);
        }
        String level;
        public String getLevel() {
            return level;
        }
        public void setLevel(String level) {
            this.level = level;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        String description;
        public int getNumPepper() {
            return numPepper;
        }
        public void setNumPepper(int numPepper) {
            this.numPepper = numPepper;
        }

        int numPepper;
    }

    class LevelAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return levelArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.item_view, null);
            TextView level = convertView.findViewById(R.id.lvl_text);
            ImageView pepper2 = convertView.findViewById(R.id.pepper2);
            ImageView pepper3 = convertView.findViewById(R.id.pepper3);
            ImageView pepper4 = convertView.findViewById(R.id.pepper4);
            TextView description = convertView.findViewById(R.id.lvl_description_text);

            level.setText(levelArrayList.get(position).getLevel());
            if (levelArrayList.get(position).getNumPepper() == 1) {
                pepper2.setImageResource(0);
                pepper3.setImageResource(0);
                pepper4.setImageResource(0);
            }
            else if (levelArrayList.get(position).getNumPepper() == 2) {
                pepper3.setImageResource(0);
                pepper4.setImageResource(0);
            }
            else if (levelArrayList.get(position).getNumPepper() == 3) {
                pepper4.setImageResource(0);
            }
            description.setText(levelArrayList.get(position).getDescription());
            return convertView;
        }
    }
}