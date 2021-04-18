package com.example.moodmapper;

import java.util.Date;

public class Record {
    public Record(String date, int bored, int lethargic, int productive) {
        this.date = date;
        this.bored = bored;
        this.lethargic = lethargic;
        this.productive = productive;
    }

    private String date;
    private int bored;
    private int lethargic;
    private int productive;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBored() {
        return bored;
    }

    public void setBored(int bored) {
        this.bored = bored;
    }

    public int getLethargic() {
        return lethargic;
    }

    public void setLethargic(int lethargic) {
        this.lethargic = lethargic;
    }

    public int getProductive() {
        return productive;
    }

    public void setProductive(int productive) {
        this.productive = productive;
    }
}
