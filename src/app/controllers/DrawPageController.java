package app.controllers;

import app.Main;
import app.models.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by samsung on 1/26/2016.
 */
public class DrawPageController implements Initializable, ControllerInterface {

    public VBox vbxContainer;
    public Button btnStartMatch;
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
        createTeams();
    }

    @Override
    public void setInterface(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
    }

    public void startMatch(ActionEvent actionEvent) {

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
            case DOUBLES:
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
                for(int i = 1; i <= numOfTeams; i++){
                    ArrayList<Player> players = new ArrayList<>();
                    players.add(ratedPlayers.get(i-1));
                    if(i > nonRatedPlayers.size()){
                        players.add(ratedPlayers.get(i));
                        i++;
                    }else {
                        players.add(nonRatedPlayers.get(i - 1));
                    }
                    Team team = new Team(i, players);
                    drawnTeams.add(team);
                }
            }else{
                for(int i = 1; i <= numOfTeams; i++){
                    ArrayList<Player> players = new ArrayList<>();
                    players.add(nonRatedPlayers.get(i-1));
                    if(i > ratedPlayers.size()){
                        players.add(nonRatedPlayers.get(i));
                        i++;
                    }else {
                        players.add(ratedPlayers.get(i - 1));
                    }
                    Team team = new Team(i, players);
                    drawnTeams.add(team);
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

        displayTeams();
    }

    private void displayTeams() {
        int x = 1;
        HBox containers[] = new HBox[numOfTeams];
        for(Team team : drawnTeams){
            //System.out.println("Team " + (x++) + " : ");

            HBox innerContainer = new HBox(15);
            innerContainer.setPrefWidth(vbxContainer.getWidth() / 2);
            innerContainer.setAlignment(Pos.CENTER_LEFT);
            //innerContainer.setMinWidth(vbxContainer.getWidth() / 2);

            Text txtTeamNumber = new Text();
            txtTeamNumber.setText("" + x);
            txtTeamNumber.setFont(new Font(50));

            HBox playerContainer = new HBox(15);
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

            containers[x-1] = innerContainer;
            x++;
        }

        vbxContainer.getChildren().addAll(containers);

    }

}
