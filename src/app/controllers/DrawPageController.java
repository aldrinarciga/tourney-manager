package app.controllers;

import app.YesNoDialog;
import app.models.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * Created by samsung on 1/26/2016.
 */
public class DrawPageController implements Initializable, ControllerInterface {

    public VBox vbxContainer;
    public Button btnStartMatch;
    public TextArea txtSearch;
    public Button btnRedraw;
    public Button btnCopyPlayers;
    public Button btnManage;
    private MainInterface mainInterface;

    private Match currentMatch;
    private int numOfPlayers;
    private int numOfTeams;

    private ArrayList<Player> ratedPlayers;
    private ArrayList<Player> nonRatedPlayers;
    private ArrayList<Team> drawnTeams;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentMatch = MatchListMgr.getCurrentMatch();
        if(currentMatch.getTeams() == null) {
            createTeams();
        }else{
            drawnTeams = currentMatch.getTeams();
            numOfTeams = drawnTeams.size();

            displayTeams();
        }

        updateButtonsBasedOnStatus();
    }

    @Override
    public void setInterface(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
    }

    public void startMatch(ActionEvent actionEvent) {
        if(YesNoDialog.display("You cannot redraw after starting, are you sure?")) {
            currentMatch.setStatus(Match.Status.STARTED);
            MatchListMgr.getCurrentMatch().setStatus(Match.Status.STARTED);
            MatchListMgr.saveCurrentMatch();

            updateButtonsBasedOnStatus();
        }
    }

    private void updateButtonsBasedOnStatus() {
        boolean isStarted = currentMatch.getStatus() == Match.Status.STARTED;
        btnRedraw.setVisible(!isStarted);
        btnStartMatch.setVisible(!isStarted);
        btnCopyPlayers.setVisible(isStarted);
        btnManage.setVisible(isStarted);
    }

    private void createTeams(){
        numOfTeams = currentMatch.getPlayers().size();
        ratedPlayers = new ArrayList<>();
        nonRatedPlayers = new ArrayList<>();
        drawnTeams = new ArrayList<>();

        for(Player player : currentMatch.getPlayers()){
            if(player.isRated()){
                ratedPlayers.add(player);
            }else{
                nonRatedPlayers.add(player);
            }
        }

        Collections.shuffle(ratedPlayers);
        Collections.shuffle(nonRatedPlayers);

        switch (currentMatch.getMatchType()){
            case CLASSIFIED_DOUBLES:
            case OPEN_DOUBLES:
                numOfPlayers = 2;
                numOfTeams /= 2;
                drawForDoubles();
                break;
            case SINGLES:
                numOfPlayers = 1;
                drawForSingles();
                break;
            default:
                break;
        }

    }

    private void drawForSingles() {
        ArrayList<Player> curPlayers = currentMatch.getPlayers();
        for(int i = 1; i <= numOfTeams; i++){
            ArrayList<Player> players = new ArrayList<>();
            players.add(curPlayers.get(i-1));
            Team team = new Team(i, players);
            drawnTeams.add(team);
        }
        displayTeams();
    }

    private void drawForDoubles() {
        if(currentMatch.getMatchType() == Match.MatchType.CLASSIFIED_DOUBLES){
            if(ratedPlayers.size() == nonRatedPlayers.size()){
                for(int i = 1; i <= numOfTeams; i++){
                    ArrayList<Player> players = new ArrayList<>();
                    players.add(ratedPlayers.get(i-1));
                    players.add(nonRatedPlayers.get(i-1));
                    Team team = new Team(i, players);
                    drawnTeams.add(team);
                }
            }else if(ratedPlayers.size() > nonRatedPlayers.size()){
                int i = 1, teamNum = 1;
                while (drawnTeams.size() !=  numOfTeams){
                    ArrayList<Player> players = new ArrayList<>();
                    players.add(ratedPlayers.get(i-1));
                    if(i > nonRatedPlayers.size()){
                        players.add(ratedPlayers.get(i));
                        i++;
                    }else {
                        players.add(nonRatedPlayers.get(i - 1));
                    }
                    Team team = new Team(teamNum++, players);
                    drawnTeams.add(team);
                    i++;
                }

            }else{
                int i = 1, teamNum = 1;
                while (drawnTeams.size() !=  numOfTeams){
                    ArrayList<Player> players = new ArrayList<>();
                    players.add(nonRatedPlayers.get(i-1));
                    if(i > ratedPlayers.size()){
                        players.add(nonRatedPlayers.get(i));
                        i++;
                    }else {
                        players.add(ratedPlayers.get(i - 1));
                    }
                    Team team = new Team(teamNum++, players);
                    drawnTeams.add(team);
                    i++;
                }
            }
        }else{
            ArrayList<Player> curPlayers = currentMatch.getPlayers();
            Collections.shuffle(curPlayers);

            for(int i = 1; i <= numOfTeams; i+=2){
                ArrayList<Player> players = new ArrayList<>();
                players.add(curPlayers.get(i-1));
                players.add(curPlayers.get(i));
                Team team = new Team(i, players);
                drawnTeams.add(team);
            }
        }

        for(int x = 0; x < 10; x++) {
            Collections.shuffle(drawnTeams);
        }
        for(int x = 1; x <= drawnTeams.size(); x++){
            Team team = drawnTeams.get(x - 1);
            team.setTeamNumber(x);
            drawnTeams.set((x - 1), team);
        }

        MatchListMgr.getCurrentMatch().setTeams(drawnTeams);
        MatchListMgr.getCurrentMatch().setStatus(Match.Status.DRAWN);
        MatchListMgr.saveCurrentMatch();

        displayTeams();
    }

    private void displayTeams() {
        int x = 1;
        HBox containers[] = new HBox[numOfTeams];
        for(Team team : drawnTeams){
            //System.out.println("Team " + (x++) + " : ");
            //System.out.println("Team : " + team.getPlayers().get(0).getFirstName() + " & " + team.getPlayers().get(1).getFirstName());
            HBox innerContainer = new HBox(15);
            innerContainer.setPrefWidth(vbxContainer.getWidth() / 2);
            innerContainer.setAlignment(Pos.CENTER_LEFT);
            //innerContainer.setMinWidth(vbxContainer.getWidth() / 2);

            Text txtTeamNumber = new Text();
            txtTeamNumber.setText("" + team.getTeamNumber());
            txtTeamNumber.setFont(new Font(50));

            HBox playerContainer = new HBox(15);
            playerContainer.setFillHeight(true);
            for(int i = 1 ; i <= team.getPlayers().size(); i++){
                Player player = team.getPlayers().get(i-1);
                //System.out.println(player.getFirstName() + " " + player.getLastName());
                Text txtPlayer = new Text();
                txtPlayer.setText(player.getFirstName() + " " + player.getLastName());
                txtPlayer.setFill(player.isRated() ? Color.BLACK : Color.BLACK);
                txtPlayer.setFont(new Font(30));
                txtPlayer.setTextAlignment(TextAlignment.CENTER);

                Text txtAmp = new Text();
                txtAmp.setText("&");
                txtAmp.setFill(Color.BLUE);
                txtAmp.setFont(new Font(30));
                txtAmp.setTextAlignment(TextAlignment.CENTER);

                playerContainer.getChildren().add(txtPlayer);
                if(i < team.getPlayers().size()) {
                    playerContainer.getChildren().add(txtAmp);
                }
            }

            playerContainer.setAlignment(Pos.CENTER);

            innerContainer.getChildren().addAll(txtTeamNumber, playerContainer);
            innerContainer.getStyleClass().add("hbox");
            int c = x - 1;
            containers[c] = innerContainer;
            x++;
        }
        vbxContainer.getChildren().clear();
        vbxContainer.getChildren().addAll(containers);


    }

    private void displayTeamsWithQuery(String query) {
        int x = 1;
        query = query.toLowerCase();
        ArrayList<Team> queriedTeam = new ArrayList<>();
        for(Team team : drawnTeams){
            boolean hasPlayer = false;
            for(Player player : team.getPlayers()){
                if(player.getFirstName().toLowerCase().contains(query) || player.getLastName().toLowerCase().contains(query)){
                    hasPlayer = true;
                    break;
                }
            }
            if(hasPlayer){
                queriedTeam.add(team);
            }
        }
        HBox containers[] = new HBox[queriedTeam.size()];
        for(Team team : queriedTeam){
            //System.out.println("Team " + (x++) + " : ");
            //System.out.println("Team : " + team.getPlayers().get(0).getFirstName() + " & " + team.getPlayers().get(1).getFirstName());
            HBox innerContainer = new HBox(15);
            innerContainer.setPrefWidth(vbxContainer.getWidth() / 2);
            innerContainer.setAlignment(Pos.CENTER_LEFT);
            //innerContainer.setMinWidth(vbxContainer.getWidth() / 2);

            Text txtTeamNumber = new Text();
            txtTeamNumber.setText(team.getTeamNumber() + "");
            txtTeamNumber.setFont(new Font(50));

            HBox playerContainer = new HBox(15);
            playerContainer.setFillHeight(true);
            for(int i = 1 ; i <= team.getPlayers().size(); i++){
                Player player = team.getPlayers().get(i-1);
                //System.out.println(player.getFirstName() + " " + player.getLastName());
                Text txtPlayer = new Text();
                txtPlayer.setText(player.getFirstName() + " " + player.getLastName());
                txtPlayer.setFill(player.isRated() ? Color.RED : Color.BLACK);
                txtPlayer.setFont(new Font(30));
                txtPlayer.setTextAlignment(TextAlignment.CENTER);

                Text txtAmp = new Text();
                txtAmp.setText("&");
                txtAmp.setFill(Color.BLUE);
                txtAmp.setFont(new Font(30));
                txtAmp.setTextAlignment(TextAlignment.CENTER);

                playerContainer.getChildren().add(txtPlayer);
                if(i < team.getPlayers().size()) {
                    playerContainer.getChildren().add(txtAmp);
                }
            }

            playerContainer.setAlignment(Pos.CENTER);

            innerContainer.getChildren().addAll(txtTeamNumber, playerContainer);
            innerContainer.getStyleClass().add("hbox");
            int c = x - 1;
            containers[c] = innerContainer;
            x++;
        }
        vbxContainer.getChildren().clear();
        if(containers.length > 0) {
            vbxContainer.getChildren().addAll(containers);
        }


    }

    public void search(Event event) {
        displayTeamsWithQuery(txtSearch.getText());
        if(txtSearch.getText().equals("")){
            displayTeams();
        }
    }

    public void redrawPlayers(ActionEvent actionEvent) {
        if(YesNoDialog.display("Would you like to redraw pairings/seeds?")) {
            createTeams();
        }
    }

    public void showCopyPlayers(ActionEvent actionEvent) {
        StringBuilder builder = new StringBuilder("");
        for(Team team : currentMatch.getTeams()) {
            for(int x = 0; x < team.getPlayers().size(); x++) {
                Player player = team.getPlayers().get(x);
                builder.append(player.getFirstName() + " " + player.getLastName());
                if(x != team.getPlayers().size() - 1) {
                    builder.append(" / ");
                }
            }
            builder.append("\n");
        }
        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(
                        new StringSelection(builder.toString()),
                        null
                );

    }

    public void manageTourney(ActionEvent actionEvent) {

    }
}
