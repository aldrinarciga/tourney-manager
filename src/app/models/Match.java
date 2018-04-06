package app.models;

import java.util.ArrayList;

/**
 * Created by samsung on 1/25/2016.
 */
public class Match {
    public enum MatchType{
        CLASSIFIED_DOUBLES, OPEN_DOUBLES, SINGLES
    }

    public enum Status {
        CREATED, DRAWN, STARTED, ENDED
    }

    private String title;
    private String fileName;
    private MatchType matchType;
    private Status status;
    private boolean doubleElimination;
    private ArrayList<Team> teams;
    private ArrayList<Player> players;
    private ArrayList<Board> boards;

    public Match(String title, String fileName, MatchType matchType, Status status, boolean doubleElimination) {
        this.title = title;
        this.fileName = fileName;
        this.matchType = matchType;
        this.status = status;
        this.doubleElimination = doubleElimination;
    }

    public Match getSimpleMatch() {
        return new Match(title, fileName, matchType, status, doubleElimination);
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

    public boolean isDoubleElimination() {
        return doubleElimination;
    }

    public void setDoubleElimination(boolean doubleElimination) {
        this.doubleElimination = doubleElimination;
    }

    public int getNumberOfBoards() {
        return boards != null ? boards.size() : 0;
    }

    public ArrayList<Board> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<Board> boards) {
        this.boards = boards;
    }
}
