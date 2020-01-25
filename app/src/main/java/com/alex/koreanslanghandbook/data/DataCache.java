package com.alex.koreanslanghandbook.data;

import android.content.SharedPreferences;

import java.util.ArrayList;

public class DataCache {
    private static DataCache instance;

    public static DataCache getInstance() {
        if(instance == null) {
            instance = new DataCache();
        }
        return instance;
    }

    public ArrayList<Vocab> getMildVocabLIst() {
        return mildVocabLIst;
    }
    public void setMildVocabLIst(ArrayList<Vocab> mildVocabLIst) {
        this.mildVocabLIst = mildVocabLIst;
    }
    public ArrayList<Vocab> getMediumVocabLIst() {
        return mediumVocabLIst;
    }
    public void setMediumVocabLIst(ArrayList<Vocab> mediumVocabLIst) {
        this.mediumVocabLIst = mediumVocabLIst;
    }
    public ArrayList<Vocab> getHotVocabLIst() {
        return hotVocabLIst;
    }
    public void setHotVocabLIst(ArrayList<Vocab> hotVocabLIst) {
        this.hotVocabLIst = hotVocabLIst;
    }
    public ArrayList<Vocab> getExtremeVocabLIst() {
        return extremeVocabLIst;
    }
    public void setExtremeVocabLIst(ArrayList<Vocab> extremeVocabLIst) {
        this.extremeVocabLIst = extremeVocabLIst;
    }
    public ArrayList<Vocab> getAllArrayList() {
        if(allArrayList != null) {
            setAllArrayList();
        }
        return allArrayList;
    }
    public void setAllArrayList() {
        ArrayList<Vocab> tempArrayList = new ArrayList<>();
        if (!isExtreme) {
            tempArrayList.addAll(mildVocabLIst);
            tempArrayList.addAll(mediumVocabLIst);
            tempArrayList.addAll(hotVocabLIst);
        }
        else {
            tempArrayList.addAll(mildVocabLIst);
            tempArrayList.addAll(mediumVocabLIst);
            tempArrayList.addAll(hotVocabLIst);
            tempArrayList.addAll(extremeVocabLIst);
        }
        this.allArrayList = tempArrayList;
    }
    public boolean isExtreme() {
        return isExtreme;
    }
    public void setExtreme(boolean extreme) {
        isExtreme = extreme;
    }
    private ArrayList<Vocab> mildVocabLIst;
    private ArrayList<Vocab> mediumVocabLIst;
    private ArrayList<Vocab> hotVocabLIst;
    private ArrayList<Vocab> extremeVocabLIst;
    private ArrayList<Vocab> allArrayList;
    private boolean isExtreme = false;

    private ArrayList<Vocab> favoriteVocabList;

    public void addToFavoties(Vocab vocab) {

    }
    public void removeFromFavroites(Vocab vocab) {

    }

    public boolean isFavorite(Vocab vocab) {
        return favoriteVocabList.contains(vocab);
    }

    public boolean isLightTheme() {
        return isLightTheme;
    }
    public void setLightTheme(boolean lightTheme) {
        isLightTheme = lightTheme;
    }
    private boolean isLightTheme = false;

}
