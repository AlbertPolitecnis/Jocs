package com.example.joc.Objects;

import java.util.ArrayList;

public class Game {

    private String nameGame;

    private String img;
    private double priceTarifa;
    private double priceGame;

    public Game(String nameGame, String img, double priceTarifa, double priceGame) {
        this.nameGame = nameGame;
        this.img = img;
        this.priceTarifa = priceTarifa;
        this.priceGame = priceGame;
    }

    public Game (){}

    public String getNameGame() {
        return nameGame;
    }

    public String getImg() {
        return img;
    }

    public double getPriceTarifa() {
        return priceTarifa;
    }

    public double getPriceGame() {
        return priceGame;
    }
}
