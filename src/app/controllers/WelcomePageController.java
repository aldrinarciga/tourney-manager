package app.controllers;

import app.MatchMakerDialog;
import app.YesNoDialog;
import app.models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WelcomePageController implements Initializable, ControllerInterface {

    public Button btnClassified;
    public Button btnDoubles;
    public ListView listMatches;
    public Text txtStatus;
    public Button btnSingles;

    private MainInterface mainInterface;

    protected ListProperty<String> listProperty = new SimpleListProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listMatches.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        resetList();
    }

    public void createNewClassifiedDoubles(ActionEvent actionEvent) {
        createNewMatch(Match.MatchType.CLASSIFIED_DOUBLES);
    }

    public void createNewDoubles(ActionEvent actionEvent) {
        createNewMatch(Match.MatchType.OPEN_DOUBLES);
    }

    public void createNewSingles(ActionEvent actionEvent) {
        createNewMatch(Match.MatchType.SINGLES);
    }

    public void loadTourney(Event event) {
        String title = listMatches.getSelectionModel().getSelectedItem().toString();
        if(YesNoDialog.display("Do you want to load " + title + "?")) {
            String fileName = OtherUtils.getJsonFileName(title);
            try {
                String result = OtherUtils.readFile(fileName);
                Match match = (new Gson()).fromJson(result, new TypeToken<Match>() {
                }.getType());
                MatchListMgr.setCurrentMatch(match);
                switch (match.getStatus()) {
                    case CREATED:
                        mainInterface.showAddPlayersScene();
                        break;
                    case DRAWN:
                        mainInterface.showAddPlayersScene();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                txtStatus.setText("Tournament file not found");
            }
        }

    }


    private void resetList(){
        listMatches.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listMatches.itemsProperty().bind(listProperty);
        listProperty.set(FXCollections.observableArrayList(MatchListMgr.getMatchList()));
    }

    private void createNewMatch(Match.MatchType type){
        Match match = MatchMakerDialog.display(type);
        if(match != null){
            try {
                MatchListMgr.getMatches().add(match);
                MatchListMgr.saveMatches();

                MatchListMgr.setCurrentMatch(match);

                if(MatchListMgr.saveCurrentMatch()) {
                    mainInterface.showAddPlayersScene();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setInterface(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
    }



}
