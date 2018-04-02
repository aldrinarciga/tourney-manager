package app.models;

/**
 * Created by aldrinarciga on 4/2/2018.
 */
public class Board {
    private int boardNumber;
    private int matchNumber;
    private int playerOne;
    private int playerTwo;

    public Board(int boardNumber) {
        this.boardNumber = boardNumber;
    }

    public int getBoardNumber() {
        return boardNumber;
    }

    public void setBoardNumber(int boardNumber) {
        this.boardNumber = boardNumber;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(int playerOne) {
        this.playerOne = playerOne;
    }

    public int getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(int playerTwo) {
        this.playerTwo = playerTwo;
    }

    public boolean hasCurrentMatch() {
        return matchNumber > 0 && playerOne > 0 && playerTwo > 0;
    }
}
