package app.models;

import java.util.ArrayList;

/**
 * Created by samsung on 1/25/2016.
 */
public class Team {
    private int teamNumber;
    private ArrayList<Player> players;
    private ArrayList<Position> history;

    public Team(int teamNumber, ArrayList<Player> players) {
        this.teamNumber = teamNumber;
        this.players = players;
        this.history = new ArrayList<Position>();
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Position> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Position> history) {
        this.history = history;
    }
}
