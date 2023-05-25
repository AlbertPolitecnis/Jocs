package WriteReadFiles;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TextFiles {

    static String fileGames = "admin/games.txt";
    static String fileUsers = "admin/users.txt";

    public static ArrayList<String> readFileUsers() {
        ArrayList<String> lines = new ArrayList<>();
        File file = new File(fileUsers);
        Scanner readFile = null;

        try {

            readFile = new Scanner(file);

            while (readFile.hasNextLine()) {

                lines.add(readFile.nextLine());

            }
        } catch (Exception e) {

            System.out.println(e.toString());

        } finally {
            try {
                readFile.close();
            } catch (Exception e2) {
                System.out.println(e2.toString());
            }
        }

        return lines;

    }

    public static void writeFileUsers (ArrayList<String> lines) {
        FileWriter path = null;
        PrintWriter pw = null;
        try {
            path = new FileWriter(fileUsers);
            pw = new PrintWriter(path);
            for (int i = 0; i<lines.size(); i++) {
                pw.println(lines.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != path)
                    path.close();
            } catch (Exception e2) {
                System.out.println(e2.toString());
                ;
            }
        }
    }

    public static ArrayList<String> readFileGames() {
        ArrayList<String> lines = new ArrayList<>();
        File file = new File(fileGames);
        Scanner readFile = null;

        try {

            readFile = new Scanner(file);

            while (readFile.hasNextLine()) {

                lines.add(readFile.nextLine());

            }
        } catch (Exception e) {

            System.out.println(e.toString());

        } finally {
            try {
                readFile.close();
            } catch (Exception e2) {
                System.out.println(e2.toString());
            }
        }

        return lines;

    }

    public static void writeFileGames (ArrayList<String> lines) {
        FileWriter path = null;
        PrintWriter pw = null;
        try {
            path = new FileWriter(fileGames);
            pw = new PrintWriter(path);
            for (int i = 0; i<lines.size(); i++) {
                pw.println(lines.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != path)
                    path.close();
            } catch (Exception e2) {
                System.out.println(e2.toString());
                ;
            }
        }
    }

}
