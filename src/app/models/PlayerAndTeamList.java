package app.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldrinarciga on 4/6/2018.
 */
public class PlayerAndTeamList {
    private ArrayList<Player> players;
    private ArrayList<Team> teams;

    public PlayerAndTeamList(ArrayList<Player> players, ArrayList<Team> teams) {
        this.players = players;
        this.teams = teams;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
}
