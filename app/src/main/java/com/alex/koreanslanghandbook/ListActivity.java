package com.alex.koreanslanghandbook;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.koreanslanghandbook.data.DataCache;
import com.alex.koreanslanghandbook.data.Parser;
import com.alex.koreanslanghandbook.data.Vocab;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Vocab> vocabArrayList;
    private ListAdapter listAdapter;
    private Toolbar toolbar;
    private int value;
    DataCache dataCache;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataCache = DataCache.getInstance();
        if (dataCache.isLightTheme()) {
            getTheme().applyStyle(R.style.AppTheme_NoActionBar, true);
        }
        setContentView(R.layout.activity_list);
        value = getIntent().getExtras().getInt("NUM_PEPPER");
        setUpList(value);
        recyclerView = findViewById(R.id.recycler_view_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listAdapter = new ListAdapter(vocabArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        toolbar = findViewById(R.id.toolbar_list);
        setSupportActionBar(toolbar);
        TextView title = findViewById(R.id.toolbar_title_text);
        if (value == 1) {
            title.setText("TABOO LVL : MILD");
        }
        else if (value == 2) {
            title.setText("TABOO LVL: MEDIUM");
        }
        else if (value == 3) {
            title.setText("TABOO LVL: HOT");
        }
        else if (value == 4) {
            title.setText("TABOO LVL: EXTREME");
        }
        ImageButton infoButton =findViewById(R.id.toolbar_info_button);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customAlertDialogue();
            }
        });
        ImageButton homeButton =findViewById(R.id.toolbar_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        adView = findViewById(R.id.adView_list);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);
    }

    private void customAlertDialogue() {
        final Dialog dialog = new Dialog(ListActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_info);
        TextView title = dialog.findViewById(R.id.title_info);
        TextView description = dialog.findViewById(R.id.description_info);
        if (value == 1) {
            title.setText("TABOO LVL: MILD");
            description.setText("\tWords in this category is considered polite in almost any casual situations. Feel free to use them as much as you want!");
        }
        else if (value == 2) {
            title.setText("TABOO LVL: MEDIUM");
            description.setText("\tWords in this category is considered fine in most casual situations. Some of the words are not the nicest words, but you probably won't offend people");
        }
        else if (value == 3) {
            title.setText("TABOO LVL: HOT");
            description.setText("\tThis category is considered rude and vulgar. Do not use these words to strangers, and be careful even around friends!");
        }
        else if (value == 4) {
            title.setText("TABOO LVL: EXTREME");
            description.setText("\tFlat out rude, vulgar, offensive, obscene, profane... DO NOT USE THEM!");
        }
        dialog.show();
    }

    private void setUpList(int value) {
        new Parser(getApplicationContext());
        DataCache dataCache = DataCache.getInstance();
        if (value == 1) {
            vocabArrayList = dataCache.getMildVocabLIst();
        }
        else if (value == 2) {
            vocabArrayList = dataCache.getMediumVocabLIst();
        }
        else if (value == 3) {
            vocabArrayList = dataCache.getHotVocabLIst();
        }
        else if (value == 4) {
            vocabArrayList = dataCache.getExtremeVocabLIst();
        }
    }

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {
        private List<Vocab> vocabList;
        ListAdapter(List<Vocab> vocabList) {
            this.vocabList = vocabList;
        }
        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vocab_view, parent, false);
            return new ListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            Vocab currentVocab = vocabList.get(position);
            holder.bind(currentVocab, position);
        }

        @Override
        public int getItemCount() {
            return vocabList.size();
        }

        private class ListHolder extends RecyclerView.ViewHolder {
            TextView korean;
            TextView roman;
            TextView definition;
            ImageView voice;
            private int pos;

            public ListHolder(@NonNull View itemView) {
                super(itemView);
                korean = itemView.findViewById(R.id.vocab_korean);
                roman = itemView.findViewById(R.id.vocab_roman);
                definition = itemView.findViewById(R.id.vocab_definition);
                voice = itemView.findViewById(R.id.voice_button);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListActivity.this, DescriptionActivity.class);
                        intent.putExtra("VALUE", value);
                        intent.putExtra("POSITION", pos);
                        startActivity(intent);
                    }
                });
                voice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String audioName = roman.getText().toString().replaceAll("[^A-Za-z]+", "");
                        int resID = getResources().getIdentifier(audioName, "raw", getPackageName());
                        if (resID != 0) {
                            MediaPlayer mediaPlayer = MediaPlayer.create(ListActivity.this, resID);
                            if (!mediaPlayer.isPlaying()) {
                                mediaPlayer.start();
                            }
                        }
                    }
                });
            }

            public void bind(Vocab currentVocab, int position) {
                pos = position;
                korean.setText(currentVocab.getKorean());
                roman.setText(currentVocab.getRoman());
                definition.setText(currentVocab.getDefinition());
                //star?

            }
        }
    }
}
