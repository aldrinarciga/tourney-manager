package app.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsung on 1/25/2016.
 */
public class MatchListMgr {

    private static final String MATCHES_FILE_NAME = "matches.json";
    static ArrayList<Match> matches = new ArrayList<>();
    static Match currentMatch;

    public static List<String> getMatchList(){
        List<String> list = new ArrayList<>();
        loadMatches();
        for(Match match : matches){
            list.add(match.getTitle());
        }
        return list;
    }

    public static void saveMatches(){
        String json = (new Gson()).toJson(matches);
        try {
            OtherUtils.writeFile(MATCHES_FILE_NAME, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Match> getMatches(){
        loadMatches();
        return matches;
    }

    public static void loadMatches() {
        try {
            String json = OtherUtils.readFile(MATCHES_FILE_NAME);
            matches = (new Gson()).fromJson(json, new TypeToken<ArrayList<Match>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Match getCurrentMatch() {
        return currentMatch;
    }

    public static boolean saveCurrentMatch(){
        Match match = currentMatch;
        if(match != null){
            String json = (new Gson()).toJson(match);
            try {
                OtherUtils.writeFile(match.getFileName(), json);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static void setCurrentMatch(Match currentMatch) {
        MatchListMgr.currentMatch = currentMatch;
    }
}
