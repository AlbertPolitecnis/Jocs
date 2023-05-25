package com.example.joc.Objects;

import java.io.Serializable;

public class UserGame implements Serializable {
    private String gameName;
    private String img;
    private boolean tarifaPlana;
    private int numPartides;

    public UserGame (String gameName, String img, boolean tarifaPlana, int numPartides) {
        this.gameName = gameName;
        this.img = img;
        this.tarifaPlana = tarifaPlana;
        this.numPartides = numPartides;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isTarifaPlana() {
        return tarifaPlana;
    }

    public void setTarifaPlana(boolean tarifaPlana) {
        this.tarifaPlana = tarifaPlana;
    }

    public int getNumPartides() {
        return numPartides;
    }

    public void setNumPartides(int numPartides) {
        this.numPartides = numPartides;
    }
}
