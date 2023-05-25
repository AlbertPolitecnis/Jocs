import WriteReadFiles.TextFiles;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class UserAction {

    private static final int MAX_GAMES = 10;

    public static User fillUser(String userNickname, String password) {

        int exists = -1;

        User toCheck = new User(userNickname);

        exists = checkUserExists(password, toCheck);

        if (exists != -1) {

            ArrayList<String> users = TextFiles.readFileUsers();
            String[] toSave = getLineSplit(users.get(exists));

            int length = toSave.length;

            for (int actualData = 1; actualData < length - 1; actualData++) {
                switch (actualData) {
                    case 1:
                        toCheck.setName(toSave[actualData]);
                        break;
                    case 2:
                        toCheck.setSurnames(toSave[actualData]);
                        break;
                    case 3:
                        toCheck.setEmail(toSave[actualData]);
                        break;
                    case 4:
                        toCheck.setCurrentAccount(toSave[actualData]);
                        break;
                    case 5:
                        toCheck.setPassword(toSave[actualData]);
                        break;
                    case 6:
                        toCheck.setMoney(Double.parseDouble(toSave[actualData]));
                        break;
                    case 7:
                        ArrayList<UserGame> fillUserGameList = new ArrayList();
                        String gameName = null;
                        boolean tarifaPlana = false;
                        int numGames = 0;
                        UserGame game;
                        for (int index = actualData; index < toSave.length; index++) {
                            String[] userGamesLst = getUserGameList(toSave[index]);
                            for (int j = 0; j < userGamesLst.length; j++) {
                                switch (j) {
                                    case 0:
                                        gameName = userGamesLst[j];
                                        break;
                                    case 1:
                                        tarifaPlana = Boolean.parseBoolean(userGamesLst[j]);
                                        break;
                                    case 2:
                                        numGames = Integer.parseInt(userGamesLst[j]);
                                        break;
                                }

                            }
                            game = new UserGame(gameName, tarifaPlana, numGames);
                            fillUserGameList.add(game);
                        }
                        toCheck.setUserGamesList(fillUserGameList);
                        break;
                    default:
                        System.out.println("Opció no disponible");
                }
            }
        }

        return toCheck;
    }

    public static void registerNewUser(User toRegister) {

        String user;
        ArrayList<String> users = TextFiles.readFileUsers();
        if (toRegister.getNickname().isEmpty() || toRegister.getPassword().isEmpty() || toRegister.getName().isEmpty() || toRegister.getSurnames().isEmpty() ||
                toRegister.getCurrentAccount().isEmpty() || toRegister.getEmail().isEmpty()) {

            System.out.println("L'usuari no es pot registrar");

        } else {

            user = toRegister.getNickname() + ":" + toRegister.getName() + ":" + toRegister.getSurnames() + ":" + toRegister.getEmail()
                    + ":" + toRegister.getCurrentAccount() + ":" + toRegister.getPassword() + ":" + toRegister.getMoney();
            for (int i = 0; i < toRegister.getUserGamesList().size(); i++) {
                user = user + ":" + toRegister.getUserGamesList().get(i).getGameName() + ";" + toRegister.getUserGamesList().get(i).isTarifaPlana() + ";" + toRegister.getUserGamesList().get(i).getNumPartides();
            }
            users.add(user);
            TextFiles.writeFileUsers(users);

            System.out.println("Usuari afegit!");
        }

    }

    public static boolean checkUserNicknameExists(User user) {

        ArrayList<String> users = TextFiles.readFileUsers();
        boolean exists = false;
        int i = 0;

        do {
            String[] read = getLineSplit(users.get(i));
            if (read[0].equals(user.getNickname())) {
                exists = true;
            }
            i++;
        } while (i < users.size() && !exists);

        return exists;
    }

    public static int checkUserExists(String toCheck, User user) {

        ArrayList<String> users = TextFiles.readFileUsers();
        boolean stop = false;
        int i = 0, exists = -1;

        do {
            String[] read = getLineSplit(users.get(i));
            if (read[0].equals(user.getNickname()) && BCrypt.checkpw(toCheck, read[5])) {
                exists = i;
                stop = true;
            }
            i++;
        } while (i < users.size() && !stop);


        if (!stop) {

            System.out.println("L'ususari no existeix");

        }

        return exists;
    }

    public static void manageGames(ArrayList<Game> gameList, User toManage) {

        boolean correct = false;
        int option = 0;

        System.out.println("GESTIONAR JOCS");

        for (int i = 0; i < toManage.getUserGamesList().size(); i++) {
            System.out.println((i + 1) + ". " + toManage.getUserGamesList().get(i).getGameName());
        }
        System.out.print("0. Sortir\n" +
                "Opció: ");

        do {
            try {
                option = Keyboard.readInt();
                correct = true;
            } catch (InputMismatchException e) {
                System.out.print("Dada incorrecte \n\nProva un altre cop: ");
            }
        } while (!correct);

        if (option > 0) {
            manageGame(option - 1, gameList, toManage);
        }
    }

    public static void manageGame(int option, ArrayList<Game> gameList, User toManage) {

        boolean correct = false;
        int action = 0;

        do {
            System.out.print("Que vols comprar?"
                    + "\n1. Partides" + "\n2. Tarifa plana" + "\n0. Res\n" +
                    "Opció: ");

            do {
                try {
                    action = Keyboard.readInt();
                    correct = true;
                } catch (InputMismatchException e) {
                    System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                }
            } while (!correct);

            correct = false;

            if (!toManage.getUserGamesList().get(option).isTarifaPlana()) {
                switch (action) {
                    case 0:
                        System.out.println("No has comprat res");
                        break;
                    case 1:

                        int games = 0;

                        System.out.println("Quantes partides vols comprar?");

                        do {
                            try {
                                games = Keyboard.readInt();
                                correct = true;
                            } catch (InputMismatchException e) {
                                System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                            }
                        } while (!correct);

                        correct = false;

                        double price = games * gameList.get(option).getPriceGame();

                        if ((toManage.getUserGamesList().get(option).getNumPartides() + games) > MAX_GAMES) {
                            System.out.println("No pots comprar tantes partides");
                        } else if (toManage.getMoney() < price) {
                            System.out.println("No tens tants diners");
                        } else {
                            toManage.getUserGamesList().get(option).setNumPartides(toManage.getUserGamesList().get(option).getNumPartides() + games);
                            toManage.setMoney(toManage.getMoney() - price);
                            System.out.println("Saldo restant " + toManage.getMoney());
                            updateUser(toManage);
                        }
                        break;
                    case 2:
                        double priceTarifa = gameList.get(option).getPriceTarifa();
                        if (toManage.getMoney() < priceTarifa) {
                            System.out.println("No tens tants diners");
                        } else if (toManage.getUserGamesList().get(option).isTarifaPlana()) {
                            System.out.println("Ja tens comprada la tarifa plana");
                        } else {
                            toManage.getUserGamesList().get(option).setTarifaPlana(true);
                            toManage.setMoney(toManage.getMoney() - priceTarifa);
                            System.out.println("Has comprat la tarifa plana");
                            System.out.println("Saldo restant " + toManage.getMoney());
                            updateUser(toManage);
                        }

                        break;
                    default:
                        System.out.println("Opció no disponible");
                }
            } else {
                System.out.println("Ja tens la tarifa plana tens partides il·limitades");
            }
        } while (action != 0);
    }

    public static void manageMoney(User toManage) {

        boolean correct = false;
        int option  = 0;

        do {
            System.out.print("GESTIONAR SALDO\n" +
                    "Saldo actual: " + toManage.getMoney() + "\n" +
                    "1. Retirar\n" +
                    "2. Afegir\n" +
                    "0. Sortir\n" +
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

            switch (option) {
                case 0:
                    System.out.println("Has parat de gestionar el saldo");
                    break;
                case 1:

                    int toRetire = 0;

                    System.out.println("Quants diners vols retirar?");

                    do {
                        try {
                            toRetire = Keyboard.readInt();
                            correct = true;
                        } catch (InputMismatchException e){
                            System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                        }
                    } while (!correct);

                    correct = false;

                    if (toRetire > toManage.getMoney()) {
                        System.out.println("No tens tants diners dins del prgrama");
                    } else {
                        toManage.setMoney(toManage.getMoney() - toRetire);
                    }

                    updateUser(toManage);
                    break;
                case 2:

                    double toAdd = 0;

                    System.out.println("Quants diners vols afegir?");

                    do {
                        try {
                            toAdd = Keyboard.readDouble();
                            correct = true;
                        } catch (InputMismatchException e){
                            System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                        }
                    } while (!correct);

                    correct = false;

                    toManage.setMoney(toManage.getMoney() + toAdd);
                    updateUser(toManage);
                    break;
                default:
                    System.out.println("Opció no disponible");
            }
        } while (option != 0);
    }

    public static void manageUser(User toManage) {

        boolean correct = false;
        int option = 0;

        do {
            System.out.print("GESTIÓ D'USUARI\n" +
                    "1. Nom: " + toManage.getName() + "\n" +
                    "2. Cognoms: " + toManage.getSurnames() + "\n" +
                    "3. Email: " + toManage.getEmail() + "\n" +
                    "4. Compte Corrent: " + toManage.getCurrentAccount() + "\n" +
                    "5. Contrasenya\n" +
                    "0. Sortir\n" +
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

            switch (option) {
                case 0:
                    System.out.println("Has sortit");
                    break;
                case 1:

                    String newName = null;

                    System.out.print("Introdueix el nou nom: ");

                    do {
                        try {
                            newName = Keyboard.readString();
                            correct = true;
                        } catch (InputMismatchException e){
                            System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                        }
                    } while (!correct);

                    correct = false;

                    if (!newName.isEmpty() || !newName.contains(":") || !newName.contains(";")) {
                        toManage.setName(newName);
                        updateUser(toManage);
                    } else {
                        System.out.println("El nom no pot estar vuit ni incloure : o ;");
                    }
                    break;
                case 2:

                    String newSurnames = null;

                    System.out.print("Introdueix els nous cognoms: ");

                    do {
                        try {
                            newSurnames = Keyboard.readString();
                            correct = true;
                        } catch (InputMismatchException e){
                            System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                        }
                    } while (!correct);

                    correct = false;

                    if (!newSurnames.isEmpty() || !newSurnames.contains(":") || !newSurnames.contains(";")) {
                        toManage.setSurnames(newSurnames);
                        updateUser(toManage);
                    } else {
                        System.out.println("El nom no pot estar vuit ni incloure : o ;");
                    }
                    break;
                case 3:

                    String newEmail = null;

                    System.out.print("Introdueix el nou email: ");

                    do {
                        try {
                            newEmail = Keyboard.readString();
                            correct = true;
                        } catch (InputMismatchException e){
                            System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                        }
                    } while (!correct);

                    correct = false;

                    if (!newEmail.isEmpty() || !newEmail.contains(":") || !newEmail.contains(";")) {
                        toManage.setEmail(newEmail);
                        updateUser(toManage);
                    } else {
                        System.out.println("El nom no pot estar vuit ni incloure : o ;");
                    }
                    break;
                case 4:

                    String newCurrentAccount = null;

                    System.out.print("Introdueix el nou comte corrent: ");

                    do {
                        try {
                            newCurrentAccount = Keyboard.readString();
                            correct = true;
                        } catch (InputMismatchException e){
                            System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                        }
                    } while (!correct);

                    correct = false;

                    if (!newCurrentAccount.isEmpty() || !newCurrentAccount.contains(":") || !newCurrentAccount.contains(";")) {
                        toManage.setCurrentAccount(newCurrentAccount);
                        updateUser(toManage);
                    } else {
                        System.out.println("El nom no pot estar vuit ni incloure : o ;");
                    }
                    break;
                case 5:

                    String oldPassword = null, newPassword = null, checkNewPassword = null;

                    System.out.print("Introdueix l'antiga contrasenya: ");

                    do {
                        try {
                            oldPassword = Keyboard.readString();
                            correct = true;
                        } catch (InputMismatchException e){
                            System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                        }
                    } while (!correct);

                    correct = false;

                    if (BCrypt.checkpw(oldPassword, toManage.getPassword())) {
                        System.out.print("Introdueix la nova contrasenya: ");

                        do {
                            try {
                                newPassword = Keyboard.readString();
                                correct = true;
                            } catch (InputMismatchException e){
                                System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                            }
                        } while (!correct);

                        correct = false;

                        System.out.print("Introdueix la nova contrasenya: ");

                        do {
                            try {
                                checkNewPassword = Keyboard.readString();
                                correct = true;
                            } catch (InputMismatchException e){
                                System.out.print("Dada incorrecte \n\nProva un altre cop: ");
                            }
                        } while (!correct);

                        correct = false;

                        if (checkNewPassword.equals(newPassword)) {
                            if (!newPassword.isEmpty() || !newPassword.contains(":") || !newPassword.contains(";")) {
                                newPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                                toManage.setPassword(newPassword);
                                updateUser(toManage);
                            } else {
                                System.out.println("El nom no pot estar vuit ni incloure : o ;");
                            }
                        }
                    } else {
                        System.out.println("Les contasenyes no coincideixen");
                    }

                    break;
                default:
                    System.out.println("Aquesta opció no es pot dur a terme");
            }

        } while (option != 0);
    }

    public static void updateUser(User toUpdate) {
        String user;
        ArrayList<String> users = TextFiles.readFileUsers();
        int pos = 0;

        for (int i = 0; i < users.size(); i++) {
            String[] read = getLineSplit(users.get(i));
            if (read[0].equals(toUpdate.getNickname())) {
                pos = i;
            }
        }

        user = toUpdate.getNickname() + ":" + toUpdate.getName() + ":" + toUpdate.getSurnames() + ":" + toUpdate.getEmail() + ":"
                + toUpdate.getCurrentAccount() + ":" + toUpdate.getPassword() + ":" + toUpdate.getMoney();
        for (int i = 0; i < toUpdate.getUserGamesList().size(); i++) {
            user = user + ":" + toUpdate.getUserGamesList().get(i).getGameName() + ";" + toUpdate.getUserGamesList().get(i).isTarifaPlana()
                    + ";" + toUpdate.getUserGamesList().get(i).getNumPartides();
        }
        users.remove(pos);
        users.add(user);
        TextFiles.writeFileUsers(users);
    }

    private static String[] getLineSplit(String line) {
        return line.split("\\:");
    }

    public static String[] getUserGameList(String line) {
        return line.split("\\;");
    }
}