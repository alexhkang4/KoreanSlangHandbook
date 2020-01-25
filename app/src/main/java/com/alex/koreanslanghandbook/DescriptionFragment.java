package com.alex.koreanslanghandbook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.koreanslanghandbook.data.DataCache;
import com.alex.koreanslanghandbook.data.Vocab;

import java.util.ArrayList;


public class DescriptionFragment extends Fragment {
    private String koreanStr;
    private String romanStr;
    private String definitionStr;
    private String descriptionStr;
    private String exampleSentenceStr;
    private int pos;
    private int value;
    private DataCache dataCache;
    private ArrayList<Vocab> vocabArrayList;

    public DescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            koreanStr = getArguments().getString("KOREAN");
            romanStr = getArguments().getString("ROMAN");
            definitionStr = getArguments().getString("DEFINITION");
            descriptionStr = getArguments().getString("DESCRIPTION");
            exampleSentenceStr = getArguments().getString("EXAMPLE_SENTENCE");
            pos = getArguments().getInt("POSITION");
            value = getArguments().getInt("VALUE");
            dataCache = DataCache.getInstance();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        TextView korean = view.findViewById(R.id.korean_description);
        TextView roman = view.findViewById(R.id.roman_description);
        TextView definition = view.findViewById(R.id.definition_description);
        TextView description = view.findViewById(R.id.description_description);
        TextView exampleSentence = view.findViewById(R.id.example_sentence_description);
        ImageButton voice = view.findViewById(R.id.voice_button_description);

        korean.setText(koreanStr);
        roman.setText(romanStr);
        definition.setText(definitionStr);
        description.setText(descriptionStr);
        exampleSentence.setText(exampleSentenceStr);
        ImageButton leftArrow = view.findViewById(R.id.left_arrow);
        ImageButton rightArrow = view.findViewById(R.id.right_arrow);
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos - 1 >= 0 ) {
                    moveLeft();

                }
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos + 1 < vocabArrayList.size() ) {
                    moveRight();
                }
            }
        });
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Coming soon...", Toast.LENGTH_SHORT).show();
            }
        });
        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        float dX = e1.getX() - e2.getX();
                        if (Math.abs(dX) > 100 && Math.abs(velocityX) > 100) {
                            if (dX > 0) {
                                if (pos + 1 < vocabArrayList.size() ) {
                                    moveRight();
                                }

                            }
                            else {
                                if (pos - 1 >= 0 ) {
                                    moveLeft();

                                }
                            }
                        }
                        return true;
                    }
                });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
        return view;
    }

    private void moveRight() {
        Fragment fragment = new DescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", pos + 1);
        bundle.putInt("VALUE", value);
        bundle.putString("KOREAN", vocabArrayList.get(pos + 1).getKorean());
        bundle.putString("ROMAN", vocabArrayList.get(pos + 1).getRoman());
        bundle.putString("DEFINITION", vocabArrayList.get(pos + 1).getDefinition());
        bundle.putString("DESCRIPTION", vocabArrayList.get(pos + 1).getDescription());
        bundle.putString("EXAMPLE_SENTENCE", vocabArrayList.get(pos + 1).getExampleSentence());
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.enter, R.anim.exit);
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    private void moveLeft() {
        Fragment fragment = new DescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", pos - 1);
        bundle.putInt("VALUE", value);
        bundle.putString("KOREAN", vocabArrayList.get(pos - 1).getKorean());
        bundle.putString("ROMAN", vocabArrayList.get(pos - 1).getRoman());
        bundle.putString("DEFINITION", vocabArrayList.get(pos - 1).getDefinition());
        bundle.putString("DESCRIPTION", vocabArrayList.get(pos - 1).getDescription());
        bundle.putString("EXAMPLE_SENTENCE", vocabArrayList.get(pos - 1).getExampleSentence());
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit);
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }
}
