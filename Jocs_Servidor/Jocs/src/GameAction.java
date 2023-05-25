import WriteReadFiles.TextFiles;

import java.util.ArrayList;

public class GameAction {

    public static ArrayList<Game> getGameList() {
        ArrayList<Game> gameList = new ArrayList();
        ArrayList<String> getList = TextFiles.readFileGames();
        String name = null;
        double tarifa = 0, game = 0;
        Game toAdd;

        for (int i = 0; i < getList.size(); i++) {
            String[] list = getLineSplit(getList.get(i));
            for (int j = 0; j < list.length; j++) {
                switch (j) {
                    case 0:
                        name = list[j];
                        break;

                    case 1:
                        tarifa = Double.parseDouble(list[j]);
                        break;

                    case 2:
                        game = Double.parseDouble(list[j]);
                        break;
                }
            }

            toAdd = new Game(name, tarifa, game);
            gameList.add(toAdd);

        }

        return gameList;
    }

    private static String[] getLineSplit(String line) {
        return line.split("\\:");
    }

}
