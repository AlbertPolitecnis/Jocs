import WriteReadFiles.TextFiles;

import java.util.ArrayList;

public class Game {

    private String nameGame;
    private double priceTarifa;
    private double priceGame;

    public Game(String nameGame, double priceTarifa, double priceGame) {
        this.nameGame = nameGame;
        this.priceTarifa = priceTarifa;
        this.priceGame = priceGame;
    }

    public String getNameGame() {
        return nameGame;
    }

    public double getPriceTarifa() {
        return priceTarifa;
    }

    public double getPriceGame() {
        return priceGame;
    }
}
