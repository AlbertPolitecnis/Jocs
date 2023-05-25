public class UserGame {
    private String gameName;
    private boolean tarifaPlana;
    private int numPartides;

    public UserGame (String gameName, boolean tarifaPlana, int numPartides) {
        this.gameName = gameName;
        this.tarifaPlana = tarifaPlana;
        this.numPartides = numPartides;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
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
