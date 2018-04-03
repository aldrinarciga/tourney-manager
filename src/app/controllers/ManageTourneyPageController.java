package app.controllers;

import app.*;
import app.dialogs.*;
import app.models.*;
import com.belteshazzar.jquery.JQuery;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import net.sf.jasperreports.engine.JRException;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

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
    private PrintReport printReport;

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
                }
            }

            updateBtn(board, btn);

            boardsMap.put(boardId, board);
            saveMatch();
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
                printBoard(board);
                break;
            default:
                break;
        }
    }

    private void printBoard(Board board) {
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);
        printBoards(boards);
    }

    private void printBoards(List<Board> boards) {
        if(boards.isEmpty()) {
            return;
        }
        (new Thread(() -> {
            try {
                (new PrintReport()).showReport(boards, playersMap);
            } catch (JRException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        })).start();

        for (Board board : boards) {
            board.setPrinted(true);
        }
    }

    private void resetBoard(Board board, Button btn) {
        board.setPlayerTwo(0);
        board.setPlayerOne(0);
        board.setMatchNumber(0);
        board.setPrinted(false);
        updateBtn(board, btn);
        saveMatch();
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

    public void saveMatch(ActionEvent actionEvent) {
        saveMatch();
    }

    public void saveMatch() {
        MatchListMgr.setCurrentMatch(currentMatch);
        mainInterface.saveMatchOnNewThread();
    }

    public void searchMatch(ActionEvent actionEvent) {
        String query = SearchMatchDialog.display().toLowerCase();
        List<Board> results = new ArrayList<>();
        if( !query.isEmpty()) {
            int num = 0;
            try {
                num = Integer.parseInt(query);
            } catch (Exception ex) {
                num = 0;
            } finally {
                for(Board board : boardsMap.values()) {
                    if(board.hasCurrentMatch()) {
                        boolean isAdded = false;
                        if(num > 0) {
                            if(board.getMatchNumber() == num || board.getBoardNumber() == num) {
                                isAdded = true;
                                results.add(board);
                            }
                        }

                        if(!isAdded && (playersMap.get(board.getPlayerOne()).toLowerCase().contains(query) || playersMap.get(board.getPlayerTwo()).toLowerCase().contains(query))) {
                            results.add(board);
                        }
                    }
                }
            }
        }

        if(!results.isEmpty()) {
            if(results.size() > 1) {
                StringBuilder builder = new StringBuilder("Multiple results: \n");
                for(Board board : results) {
                    builder.append("- B" + board.getBoardNumber() + "/M" + board.getMatchNumber() + ": ");
                    builder.append(playersMap.get(board.getPlayerOne()) + " vs. " + playersMap.get(board.getPlayerTwo()));
                    builder.append("\n");
                }

                SearchResultDialog.display(builder.toString());
            } else {
                showBoardDetails(boardsMap.get(results.get(0).getBoardNumber()), (Button) boardContainer.getChildren().get(results.get(0).getBoardNumber() - 1));
            }
        } else {
            ErrorDialog.display("No active match found");
        }
    }

    public void printMultiple(ActionEvent actionEvent) {
        List<Board> activeBoards = getActiveBoards();
        if(activeBoards.size() > 0) {
            boolean showPrinted = YesNoDialog.display("Do you want to include already printed matches?");
            if(!showPrinted) {
                activeBoards = getUnprintedBoards();
            }
            if(activeBoards.size() > 0) {
                List<Integer> boardIds = PrintSelectionDialog.display(activeBoards);
                printBoards(getBoardsByIds(boardIds));
            } else {
                ErrorDialog.display("No active matches to print");
            }

        } else {
            ErrorDialog.display("No active matches to print");
        }

    }

    private List<Board> getBoardsByIds(List<Integer> boardIds) {
        List<Board> boards = new ArrayList<>();
        for(int id : boardIds) {
            Board board = boardsMap.get(id);
            if(board != null) {
                boards.add(board);
            }
        }
        return boards;
    }

    private List<Board> getActiveBoards() {
        List<Board> boards = new ArrayList<>();
        for(Board board : boardsMap.values()) {
            if(board.hasCurrentMatch()) {
                boards.add(board);
            }
        }
        return boards;
    }

    private List<Board> getUnprintedBoards() {
        List<Board> boards = new ArrayList<>();
        for(Board board : boardsMap.values()) {
            if(!board.isPrinted() && board.hasCurrentMatch()) {
                boards.add(board);
            }
        }
        return boards;
    }
}
