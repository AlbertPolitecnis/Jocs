import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Menu {

    public static void menuUsuari() {
        int chose = 0;
        boolean correct = false;
        User toCheck;

        do {

            System.out.print("ACCÉS A LA GESTIÓ D'ARXIUS\n" +
                    "1. Registre\n" +
                    "2. Accedir\n" +
                    "0. Sortir\n" +
                    "Opció: ");
            do {
                try {
                    chose = Keyboard.readInt();
                    correct = true;
                } catch (InputMismatchException e){
                    System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                }
            } while (!correct);

            correct = false;

            switch (chose) {

                case 0:
                    System.out.println("Has sortit del programa");
                    break;

                case 1:
                    newUser();
                    break;

                case 2:
                    int exists = -1;
                    String userNickname = null, password = null;
                    System.out.print("Introdueix el teu nom d'usuari: ");
                    do {
                        try {
                            userNickname = Keyboard.readString();
                            correct = true;
                        } catch (InputMismatchException e){
                            System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                        }
                    } while (!correct);

                    correct = false;

                    System.out.print("Introdueix la contrasenya: ");

                    do {
                        try {
                            password = Keyboard.readString();
                            correct = true;
                        } catch (InputMismatchException e){
                            System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                        }
                    } while (!correct);
                    correct = false;

                    toCheck = new User(userNickname);

                    exists = UserAction.checkUserExists(password, toCheck);

                    if (exists != -1) {

                        toCheck = UserAction.fillUser(userNickname, password);
                        showGamesMenu(toCheck);

                    }

                    break;

                default:
                    System.out.println("No existeix aquesta opció");
                    break;
            }
        } while (chose != 0);
    }

    private static void newUser() {

        boolean correct = false, exists;
        String nickname = null, password = null, checkPassword = null, name = null, surnames = null, currentAccount = null, email = null;

        ArrayList<Game> gameList = GameAction.getGameList();
        ArrayList<UserGame> gamesUser = new ArrayList();
        UserGame game;

        for (int i = 0; i < gameList.size(); i++) {
            game = new UserGame(gameList.get(i).getNameGame(), false, 0);
            gamesUser.add(game);
        }

        System.out.print("INICIAR REGISTRE\n" +
                "Nom d'usuari:");


        do {
            try {
                nickname = Keyboard.readString();
                correct = true;
            } catch (InputMismatchException e){
                System.out.print("Dada incorrecte \n\nProva un altre cop: ");
            }
        } while (!correct);

        correct = false;

        User check = new User(nickname);
        exists = UserAction.checkUserNicknameExists(check);

        if (!exists) {
            System.out.print("Contrasenya:");

            do {
                try {
                    password = Keyboard.readString();
                    correct = true;
                } catch (InputMismatchException e){
                    System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                }
            } while (!correct);

            correct = false;

            System.out.print("Repetir contrasenya:");

            do {
                try {
                    checkPassword = Keyboard.readString();
                    correct = true;
                } catch (InputMismatchException e){
                    System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                }
            } while (!correct);

            correct = false;

            if (checkPassword.equals(password)) {

                password = BCrypt.hashpw(password, BCrypt.gensalt());

                System.out.print("Nom:");

                do {
                    try {
                        name = Keyboard.readString();
                        correct = true;
                    } catch (InputMismatchException e){
                        System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                    }
                } while (!correct);

                correct = false;

                System.out.print("Cognoms:");

                do {
                    try {
                        surnames = Keyboard.readString();
                        correct = true;
                    } catch (InputMismatchException e){
                        System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                    }
                } while (!correct);

                correct = false;

                System.out.print("Compte corrent:");

                do {
                    try {
                        currentAccount = Keyboard.readString();
                        correct = true;
                    } catch (InputMismatchException e){
                        System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                    }
                } while (!correct);

                correct = false;

                System.out.print("email:");

                do {
                    try {
                        email = Keyboard.readString();
                        correct = true;
                    } catch (InputMismatchException e){
                        System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                    }
                } while (!correct);

                correct = false;

                User toRegister = new User(nickname, password, name, surnames, currentAccount, email, 1000, gamesUser);

                UserAction.registerNewUser(toRegister);
            } else {

                System.out.println("La contrassenya no coincideix");

            }
        } else {

            System.out.println("El nom d'usuari ja està en ús");

        }


    }

    private static void showGamesMenu(User player) {

        boolean correct = false;
        int chose = 0;

        ArrayList<Game> gameList = GameAction.getGameList();
        ArrayList<UserGame> gamesUser = new ArrayList();
        UserGame game;

        for (int i = 0; i < gameList.size(); i++) {
            game = new UserGame(gameList.get(i).getNameGame(), false, 0);
            gamesUser.add(game);
        }

        do {

            System.out.print("JOCS ONLINE\n" +
                    "1. Jugar\n" +
                    "2. Gestionar jocs\n" +
                    "3. Gestionar saldo\n" +
                    "4. Gestionar dades d'usuari\n" +
                    "0. Tornar al menú d'inici\n" +
                    "Opció: ");

            do {
                try {
                    chose = Keyboard.readInt();
                    correct = true;
                } catch (InputMismatchException e){
                    System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                }
            } while (!correct);

            correct = false;

            switch (chose) {

                case 0:
                    System.out.println("Has sortit del menú de jocs");
                    break;

                case 1:
                    showGamesList(player, gameList);
                    break;

                case 2:
                    UserAction.manageGames(gameList, player);
                    break;

                case 3:
                    UserAction.manageMoney(player);
                    break;

                case 4:
                    UserAction.manageUser(player);
                    break;

                default:
                    System.out.println("No existeix aquesta opció");
                    break;
            }
        } while (chose != 0);

    }

    private static void showGamesList(User player, ArrayList<Game> listGames) {

        boolean correct = false;
        int option = 0;

        do {
            for (int i = 0; i < player.getUserGamesList().size(); i++) {
                if (player.getUserGamesList().get(i).isTarifaPlana() == true) {

                    System.out.println((i + 1) + ": " + listGames.get(i).getNameGame() + "(Tarifa plana=");

                } else {

                    System.out.println((i + 1) + ": " + listGames.get(i).getNameGame() + " (" + player.getUserGamesList().get(i).getNumPartides() + ")");

                }
            }
            System.out.print("0. Sortir\n" +
                    "Opció: ");

            do {
                try {
                    option = Keyboard.readInt();
                    correct = true;
                } catch (InputMismatchException e){
                    System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                }
            } while (!correct);

            correct = false;

            if (option == 0) {
                System.out.println("No has jugat a res");
            } else {

                int chosen = option - 1;

                if (player.getUserGamesList().get(chosen).getNumPartides() > 0 || player.getUserGamesList().get(chosen).isTarifaPlana()) {
                    System.out.println("Has jugat a " + listGames.get(chosen).getNameGame());
                    if (player.getUserGamesList().get(chosen).getNumPartides() > 0) {
                        player.getUserGamesList().get(chosen).setNumPartides(player.getUserGamesList().get(chosen).getNumPartides() - 1);
                    }
                    UserAction.updateUser(player);
                } else {
                    System.out.println("No pots jugar a " + player.getUserGamesList().get(chosen).getGameName() + " has de comprar més partides");
                }
            }
        } while (option != 0);
    }

}
