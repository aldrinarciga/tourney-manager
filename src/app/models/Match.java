package app.models;

import java.util.ArrayList;

/**
 * Created by samsung on 1/25/2016.
 */
public class Match {
    public static enum MatchType{
        CLASSIFIED_DOUBLES, DOUBLES, SINGLES;
    }

    public static enum Status {
        CREATED, DRAWN, STARTED, ENDED;
    }

    private String title;
    private String fileName;
    private MatchType matchType;
    private Status status;
    private ArrayList<Team> teams;
    private ArrayList<Player> players;

    public Match(String title, String fileName, MatchType matchType, Status status) {
        this.title = title;
        this.fileName = fileName;
        this.matchType = matchType;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
