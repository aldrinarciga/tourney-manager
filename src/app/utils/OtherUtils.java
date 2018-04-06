package app.utils;

import app.models.Player;
import app.models.Team;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by samsung on 1/25/2016.
 */
public class OtherUtils {

    public static void writeFile(String fileName, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(fileName));
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }

    public static String readFile(String filename) throws Exception {
        String result;
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }
        result = sb.toString();
        return result;
    }

    public static String getJsonFileName(String title){
        return title.toLowerCase().replaceAll(" ","-").concat(".json");
    }

    public static Player parseNameToPlayer(String name) {
        name = name.trim();
        if(name.isEmpty()) {
            return null;
        }
        String firstName = "";
        String lastName = "";
        boolean isRated = false;
        String[] nameSplit = name.split(" ");
        if(nameSplit.length > 0) {
            if(nameSplit.length > 1) {
                lastName = nameSplit[nameSplit.length - 1];
                lastName = lastName.trim();
                for(int x = 0; x < nameSplit.length - 1; x++) {
                    firstName += nameSplit[x];
                }
            } else  {
                firstName = nameSplit[0];
            }

            firstName = firstName.trim();

            if(firstName.length()  > 0 && firstName.charAt(0) == '*') {
                isRated = true;
                firstName = firstName.replace("*", "");
            }
        }

        return new Player(firstName, lastName, isRated);
    }

    public static Team parseNameToTeam(String name, int seed, String splitter) {
        String[] names = name.split(splitter);
        Team team = null;
        if (names.length > 1) {
            Player player1 = parseNameToPlayer(names[0]);
            Player player2 = parseNameToPlayer(names[1]);
            if(player1 != null && player2 != null) {
                ArrayList<Player> players = new ArrayList<>();
                players.add(player1);
                players.add(player2);
                team = new Team(seed, players);
            }
        }
        return team;
    }

}
