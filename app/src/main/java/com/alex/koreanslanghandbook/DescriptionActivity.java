package com.alex.koreanslanghandbook;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alex.koreanslanghandbook.data.DataCache;
import com.alex.koreanslanghandbook.data.Vocab;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class DescriptionActivity extends AppCompatActivity {
    private ArrayList<Vocab> vocabArrayList;
    private int pos;
    private int value;
    DataCache dataCache;
    private Toolbar toolbar;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataCache = DataCache.getInstance();
        if (dataCache.isLightTheme()) {
            getTheme().applyStyle(R.style.AppTheme_NoActionBar, true);

        }
        setContentView(R.layout.activity_description);
        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("POSITION");
        value = extras.getInt("VALUE");
        toolbar = findViewById(R.id.toolbar_description);
        setSupportActionBar(toolbar);
        if (value == 1) {
            vocabArrayList = dataCache.getMildVocabLIst();
            getSupportActionBar().setTitle("LEVEL 1 : MILD");
        }
        else if (value == 2) {
            vocabArrayList = dataCache.getMediumVocabLIst();
            getSupportActionBar().setTitle("LEVEL 2 : MEDIUM");
        }
        else if (value == 3) {
            vocabArrayList = dataCache.getHotVocabLIst();
            getSupportActionBar().setTitle("LEVEL 3 : HOT");
        }
        else if (value == 4) {
            vocabArrayList = dataCache.getExtremeVocabLIst();
            getSupportActionBar().setTitle("LEVEL 4 : EXTREME");
        }
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new DescriptionFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("POSITION", pos);
            bundle.putInt("VALUE", value);
            bundle.putString("KOREAN", vocabArrayList.get(pos).getKorean());
            bundle.putString("ROMAN", vocabArrayList.get(pos).getRoman());
            bundle.putString("DEFINITION", vocabArrayList.get(pos).getDefinition());
            bundle.putString("DESCRIPTION", vocabArrayList.get(pos).getDescription());
            bundle.putString("EXAMPLE_SENTENCE", vocabArrayList.get(pos).getExampleSentence());
            fragment.setArguments(bundle);
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
        TextView title = findViewById(R.id.toolbar_title_text);
        title.setText("");
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
                Intent intent = new Intent(DescriptionActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        adView = findViewById(R.id.adView_description);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void customAlertDialogue() {
        final Dialog dialog = new Dialog(DescriptionActivity.this);
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
}
