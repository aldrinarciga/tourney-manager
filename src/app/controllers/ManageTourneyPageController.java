package app.controllers;

import app.BoardDetailsDialog;
import app.BoardMakerDialog;
import app.YesNoDialog;
import app.models.*;
import com.belteshazzar.jquery.JQuery;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

import static com.belteshazzar.jquery.JQuery.$;

/**
 * Created by aldrinarciga on 4/2/2018.
 */
public class ManageTourneyPageController implements Initializable, ControllerInterface {

    public WebView webMain;
    public HBox boardContainer;
    private WebEngine webEngine;
    private MainInterface mainInterface;
    private Match currentMatch;
    private HashMap<Integer, String> playersMap;
    private TreeMap<Integer, Board> boardsMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentMatch = MatchListMgr.getCurrentMatch();

        loadWebView();
        loadBoards();
        preparePlayers();
    }

    private void preparePlayers() {
        playersMap = new HashMap<>();
        for(Team team : currentMatch.getTeams()) {
            StringBuilder playerName = new StringBuilder("");
            for(int x = 0; x < team.getPlayers().size(); x++) {
                Player player = team.getPlayers().get(x);
                playerName.append(player.getFirstName() + " " + player.getLastName());
                if(x != team.getPlayers().size() - 1) {
                    playerName.append(" / ");
                }
            }
            playersMap.put(team.getTeamNumber(), playerName.toString());
        }
    }

    private void loadBoards() {
        int btnHeight = 30;
        int btnWidth = 80;
        boardsMap = new TreeMap<>();
        for(Board board : currentMatch.getBoards()) {
            boardsMap.put(board.getBoardNumber(), board);

            Button btn = new Button("" + board.getBoardNumber());
            btn.setMinSize(btnWidth, btnHeight);
            btn.setPrefSize(btnWidth, btnHeight);
            btn.setMaxSize(btnWidth, btnHeight);
            btn.setOnMouseClicked(event -> {
                processBoard(board.getBoardNumber(), btn);
            });
            updateBtn(board, btn);
            boardContainer.getChildren().add(btn);
        }
    }

    private void updateBtn(Board board, Button btn) {
        if(!board.hasCurrentMatch()) {
            btn.setStyle("-fx-text-fill:white; -fx-background-color: green");
            btn.setText("" + board.getBoardNumber());
        } else {
            btn.setStyle("-fx-text-fill:white; -fx-background-color: red");
            btn.setText("B" + board.getBoardNumber() + " - M" + board.getMatchNumber());
        }
    }

    private void processBoard(int boardId, Button btn) {
        Board board = boardsMap.get(boardId);
        if(board != null) {
            if(board.hasCurrentMatch()) {
                showBoardDetails(board, btn);
            } else {
                Board newBoard = BoardMakerDialog.display(boardId);
                if(newBoard != null && newBoard.getMatchNumber() > 0 &&playersMap.get(newBoard.getPlayerOne()) != null && playersMap.get(newBoard.getPlayerTwo()) != null) {
                    board.setMatchNumber(newBoard.getMatchNumber());
                    board.setPlayerOne(newBoard.getPlayerOne());
                    board.setPlayerTwo(newBoard.getPlayerTwo());
                    showBoardDetails(board, btn);
                } else {
                    ErrorDialog.display("Match or player seed number doesn't exist");
                }
            }

            updateBtn(board, btn);

            boardsMap.put(boardId, board);
        }

    }

    private void showBoardDetails(Board board, Button btn) {
        BoardDetailsDialog.BoardResult result = BoardDetailsDialog.display(board, playersMap.get(board.getPlayerOne()), playersMap.get(board.getPlayerTwo()));
        switch (result.boardAction) {
            case CLOSE:
                if(YesNoDialog.display("Are you sure you want to finish this match?")) {
                    resetBoard(board, btn);
                }
                break;
            case PRINT:
                break;
            default:
                break;
        }
    }

    private void resetBoard(Board board, Button btn) {
        board.setPlayerTwo(0);
        board.setPlayerOne(0);
        board.setMatchNumber(0);
        updateBtn(board, btn);
    }

    private void loadWebView() {
        webEngine = webMain.getEngine();
        JQuery.setEngine(webEngine);

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue != Worker.State.SUCCEEDED ) {
                return;
            }

            $(() -> {
                System.out.print("LOADED");
                $(".corner-ax").hide();
                $(".ax-bar").hide();
                $(".footer").hide();
                $("#footer").hide();
                $("#push").hide();
                $(".title-stripe").hide();
                $(".-with-content-gutters").removeClass(".-with-content-gutters");
            });
        });

        webEngine.setJavaScriptEnabled(true);
        webEngine.load("https://challonge.com/");


    }


    @Override
    public void setInterface(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
    }

}
