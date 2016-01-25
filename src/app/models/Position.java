package app.models;

/**
 * Created by samsung on 1/25/2016.
 */
public class Position {
    public static enum Bracket{
        WINNERS, LOSERS;
    }

    private Bracket bracket;
    private int round;
    private int matchNumber;

    public Position(Bracket bracket, int round, int matchNumber) {
        this.bracket = bracket;
        this.round = round;
        this.matchNumber = matchNumber;
    }

    public Bracket getBracket() {
        return bracket;
    }

    public void setBracket(Bracket bracket) {
        this.bracket = bracket;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }
}
