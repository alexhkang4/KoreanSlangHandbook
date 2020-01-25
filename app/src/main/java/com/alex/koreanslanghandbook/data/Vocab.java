package com.alex.koreanslanghandbook.data;

public class Vocab {
    private String korean;
    private String roman;
    private String definition;
    private String description;
    private String exampleSentence;

    Vocab(String korean, String roman,String definition, String description, String exampleSentence) {
        setKorean(korean);
        setRoman(roman);
        setDefinition(definition);
        setDescription(description);
        setExampleSentence(exampleSentence);
    }
    public String getKorean() {
        return korean;
    }
    public void setKorean(String korean) {
        this.korean = korean;
    }
    public String getRoman() {
        return roman;
    }
    public void setRoman(String roman) {
        this.roman = roman;
    }
    public String getDefinition() {
        return definition;
    }
    public void setDefinition(String definition) {
        this.definition = definition;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getExampleSentence() {
        return exampleSentence;
    }
    public void setExampleSentence(String exampleSentence) {
        this.exampleSentence = exampleSentence;
    }
}
