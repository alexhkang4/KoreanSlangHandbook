package com.alex.koreanslanghandbook.data;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Parser {
    private ArrayList<Vocab> mildArrayList;
    private Context context;
    private DataCache dataCache;
    public Parser(Context context) {
        mildArrayList = new ArrayList<>();
        this.context = context;
        dataCache = DataCache.getInstance();
        try {
            ParseVocabList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ParseVocabList() throws IOException {
        Gson gson = new Gson();
        InputStream inputStream = context.getAssets().open("mild.json");
        AllVocabList mildVocabList = gson.fromJson(readString(inputStream), AllVocabList.class);
        dataCache.setMildVocabLIst(mildVocabList.getData());
        inputStream = context.getAssets().open("medium.json");
        AllVocabList mediumVocabList = gson.fromJson(readString(inputStream), AllVocabList.class);
        dataCache.setMediumVocabLIst(mediumVocabList.getData());
        inputStream = context.getAssets().open("hot.json");
        AllVocabList hotVocabList = gson.fromJson(readString(inputStream), AllVocabList.class);
        dataCache.setHotVocabLIst(hotVocabList.getData());
        inputStream = context.getAssets().open("extreme.json");
        AllVocabList extremeVocabList = gson.fromJson(readString(inputStream), AllVocabList.class);
        dataCache.setExtremeVocabLIst(extremeVocabList.getData());
        dataCache.setAllArrayList();
    }

    public static String readString(InputStream is) throws IOException{
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);

        char[] buf = new char[1024];
        int len;

        while ((len = sr.read(buf)) > 0){
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
